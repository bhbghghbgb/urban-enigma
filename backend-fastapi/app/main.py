from contextlib import asynccontextmanager
from typing import Union
from passlib.hash import bcrypt
from beanie import init_beanie
from fastapi import FastAPI, HTTPException, Security
from motor.motor_asyncio import AsyncIOMotorClient
from starlette.status import (
    HTTP_401_UNAUTHORIZED,
    HTTP_403_FORBIDDEN,
    HTTP_404_NOT_FOUND,
)
from app.accounts.models import Account, AccountAuth
from app.auth.models import AccountSessionToken
from app.settings import DATABASE_CONNECTION_URL
from app.auth.securities import (
    access_token_security,
    refresh_token_security,
    oauth2_scheme,
)
from app.auth.settings import AUTH_TOKEN_EXPIRED, AUTH_REFRESH_TOKEN_EXPIRED


@asynccontextmanager
async def lifespan(app: FastAPI):
    # on startup
    await init_beanie(database=database, document_models=[Account])
    yield
    # on shutdown
    pass


app = FastAPI(lifespan=lifespan)
client = AsyncIOMotorClient(DATABASE_CONNECTION_URL)
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


@app.post("/legacy-auth", tags=["Auth"])
async def legacy_authentication(auth: AccountAuth) -> AccountSessionToken:
    """Authenticate and returns the user's JWT."""
    account = await Account.find_one(Account.username == auth.username)
    if account is None:
        raise HTTPException(HTTP_404_NOT_FOUND, "Username does not exist.")
    elif account.email_confirmed_at is None:
        raise HTTPException(HTTP_403_FORBIDDEN, "Account unverified.")
    elif not bcrypt.verify(auth.password, account.password):
        raise HTTPException(HTTP_401_UNAUTHORIZED, "Password mismatch.")
    return AccountSessionToken(
        access_token=access_token_security.create_access_token(account.jwt_subject),
        refresh_token=refresh_token_security.create_refresh_token(account.jwt_subject),
        access_token_expire=AUTH_TOKEN_EXPIRED,
        refresh_token_expire=AUTH_REFRESH_TOKEN_EXPIRED,
    )


@app.post("/auth", tags=["Auth"])
async def authentication(token: str = Security(oauth2_scheme)):
    return {"token": token}
