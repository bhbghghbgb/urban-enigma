from contextlib import asynccontextmanager
from typing import Union

from beanie import Document, init_beanie
from fastapi import FastAPI
from motor.motor_asyncio import AsyncIOMotorClient


class Account(Document):
    username: str
    password: str

    class Settings:
        name = "accounts"

    class Config:
        schema_extras = {"example": {"username": "memaybeo", "password": "123"}}


@asynccontextmanager
async def lifespan(app: FastAPI):
    # on startup
    await init_beanie(database=database, document_models=[Account])
    yield
    # on shutdown


app = FastAPI(lifespan=lifespan)
client = AsyncIOMotorClient(
    "mongodb+srv://new-user:KkuqGxB8oST1y7PB@cluster0.0tglxoe.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"
)
database = client.get_database("test")


@app.get("/")
def read_root():
    return {"Hello": "World"}


@app.get("/items/{item_id}")
def read_item(item_id: int, q: Union[str, None] = None):
    return {"item_id": item_id, "q": q}


@app.get("/accounts")
async def read_accounts() -> list[Account]:
    return await Account.find_all().to_list()
