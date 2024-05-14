from contextlib import asynccontextmanager
import json
from datetime import datetime
from typing import List, Literal, Optional

from httpx import AsyncClient
import pytz
from beanie import Document, Link, init_beanie, BeanieObjectId
from fastapi import FastAPI, HTTPException
from motor.motor_asyncio import AsyncIOMotorClient
from pydantic import Field

with open("./config.json") as f:
    config = json.load(f)


@asynccontextmanager
async def lifespan(app: FastAPI):
    """Initialize application services."""
    client = AsyncIOMotorClient(config["DATABASE_CONNECTION_URL"]).get_database(
        config["DATABASE_NAME"]
    )
    await init_beanie(client, document_models=[Payment, OrderInfo, Item, Transaction])
    print("Startup complete")
    yield
    print("Shutdown complete")


app = FastAPI(lifespan=lifespan)


class Item(Document):
    item_id: str
    item_name: str
    item_price: int
    item_quantity: int


class OrderInfo(Document):
    order_id: str


class Payment(Document):
    app_id: Literal[1]  # Identifier for the application registered with ZaloPay
    app_user: str = Field(
        ..., max_length=50
    )  # User identifier: id/username/name/phone/email
    app_trans_id: str = Field(
        ..., max_length=40, pattern=r"\d{6}_.*"
    )  # Transaction ID, must start with yymmdd_
    app_time: int  # Order creation time (unix timestamp in milliseconds)
    items: List[Item]  # Order items, defined by the application
    description: str = Field(
        ..., max_length=256
    )  # Description of the service being paid
    embed_data: OrderInfo  # Custom order data, will be called back to AppServer upon successful payment
    callback_url: Optional[
        str
    ]  # ZaloPay will notify the payment status upon completion
    title: Optional[str] = Field(None, max_length=256)  # Order title
    currency: Optional[str] = Field(
        "VND", min_length=3, max_length=3
    )  # Currency unit, default is VND
    phone: Optional[str] = Field(None, max_length=50)  # User's phone number
    email: Optional[str] = Field(None, max_length=100)  # User's email
    address: Optional[str] = Field(None, max_length=1024)  # User's address


class Transaction(Document):
    payment: Link[Payment]
    status: Literal["pending", "completed", "rejected"]


class UpdateTransaction(Document):
    transaction: BeanieObjectId
    status: Literal["completed", "rejected"]


@app.post("/gialo-money/payment")
async def create_payment(payment: Payment):
    # Validate the transaction ID format with the current date in Vietnam timezone
    vn_timezone = pytz.timezone("Asia/Ho_Chi_Minh")
    vn_time = datetime.now(vn_timezone)
    current_date_str = vn_time.strftime("%y%m%d")
    if not payment.app_trans_id.startswith(current_date_str):
        raise HTTPException(
            status_code=400,
            detail=f"Invalid app_trans_id format. It must start with yymmdd. Must start with today {current_date_str}",
        )

    # Additional validations can be added here

    # If all validations pass, proceed with order creation logic
    # ...

    await payment.save()
    transaction = Transaction(payment=payment, status="pending")
    transaction.save()
    return {"message": "Order created successfully", "transaction": transaction}


@app.get("/gialo-money/payment", response_model=list[Payment])
async def get_transactions():
    return await Payment.find().to_list()


@app.post("/gialo-money/transaction")
async def confirm_transaction(update_transaction: UpdateTransaction):
    transaction = await Transaction.find_one(
        Transaction.id == UpdateTransaction.transaction
    )
    if not transaction:
        raise HTTPException(
            status_code=404,
            detail="No such transaction.",
        )
    if update_transaction.status == "rejected":
        transaction.status = "rejected"
        return
    if update_transaction.status != "completed":
        raise HTTPException(
            status_code=400,
            detail="No such status.",
        )
    transaction.status = "completed"
    payment = await Payment.find_one(Payment.id == transaction.payment)
    if not payment:
        raise HTTPException(status_code=404, detail="No such payment.")
    async with AsyncClient() as client:
        await client.post(payment.callback_url, content=json.dumps(payment.embed_data))
    return

@app.get("/gialo-money/transaction")
async def get_transactions():
    return await Transaction.find().to_list()

# Run the application
if __name__ == "__main__":
    import uvicorn

    uvicorn.run(app, host="localhost", port=8000)
