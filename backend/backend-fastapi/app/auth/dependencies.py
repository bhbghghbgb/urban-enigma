from typing import Annotated

from fastapi import Depends, HTTPException, status
from fastapi.security import OAuth2PasswordRequestForm
from jose import JWTError, jwt

from app.accounts.models import Account
from app.auth.securities import oauth2_userpass_security
from app.auth.settings import AUTH_SETTINGS
from app.auth.utils import verify_password


async def account_from_access_token(
    token: Annotated[str, Depends(oauth2_userpass_security)]
) -> Account:
    """Returns the Account associated with an access token."""
    try:
        account = await Account.find_one(
            Account.username == jwt.decode(token, AUTH_SETTINGS.auth_access_key)["sub"]
        )
        if account is not None:
            return account
        raise HTTPException(
            status.HTTP_404_NOT_FOUND,
            "Account associated with credentials doesn't exist.",
        )
    except (JWTError, KeyError) as e:
        raise HTTPException(
            status.HTTP_401_UNAUTHORIZED, e, headers={"WWW-Authenticate": "Bearer"}
        )


async def authenticate_userpass_login(
    form_data: Annotated[OAuth2PasswordRequestForm, Depends()]
) -> Account:
    """Returns the Account associated with this username and password."""
    account = await Account.find_one(Account.username == form_data.username)
    if account is None:
        raise HTTPException(
            status.HTTP_404_NOT_FOUND, "Account associated with username doesn't exist."
        )
    if not verify_password(form_data.password, account.password):
        raise HTTPException(
            status.HTTP_401_UNAUTHORIZED,
            "Found an Account associated with this username, but password is mismatch.",
        )
    return account
