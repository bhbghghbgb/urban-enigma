from typing import Annotated

from fastapi import APIRouter, Depends, FastAPI

from app.accounts.models import Account
from app.auth.dependencies import account_from_access_token, authenticate_userpass_login
from app.auth.models import AccessToken
from app.auth.utils import create_access_token

test_router = APIRouter(prefix="/test", tags=["test"])


@test_router.get("/users/me")
async def read_my_account(
    account: Annotated[Account, Depends(account_from_access_token)]
) -> Account:
    return account


@test_router.post("/login")
async def login_for_access_token(
    account: Annotated[Account, Depends(authenticate_userpass_login)]
) -> AccessToken:
    return create_access_token(account.jwt_subject)
