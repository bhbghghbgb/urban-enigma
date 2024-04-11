from fastapi import HTTPException, Security
from fastapi_jwt import JwtAuthorizationCredentials
from starlette.status import HTTP_404_NOT_FOUND

from app.auth.securities import access_token_security
from app.auth.utils import account_from_credentials
from app.main import Account


async def account_from_current_session(
    auth: JwtAuthorizationCredentials = Security(access_token_security),
) -> Account:
    """Returns an Account instance from the current session, i.e. authorized account."""
    account = await account_from_credentials(auth)
    if account is None:
        raise HTTPException(
            HTTP_404_NOT_FOUND, "Account associated with credentials doesn't exist."
        )
    return account
