from typing import Optional

from fastapi_jwt import JwtAuthorizationCredentials

from app.auth.securities import access_token_security
from app.main import Account


async def account_from_credentials(
    auth: JwtAuthorizationCredentials,
) -> Optional[Account]:
    """Returns the user associated with auth credentials."""
    return await Account.find_one(Account.username == auth.subject["username"])


async def account_from_token(token: str) -> Optional[Account]:
    """Returns the user associated with a token value without any validations."""
    return await Account.find_one(Account.username == access_token_security._decode(token))
