from fastapi.security import OAuth2PasswordBearer
from fastapi_jwt import JwtAccessBearer, JwtRefreshBearer

from app.auth.settings import (
    AUTH_KEY,
    AUTH_KEY_ALGO,
    AUTH_REFRESH_KEY,
    AUTH_REFRESH_TOKEN_EXPIRED,
    AUTH_TOKEN_EXPIRED,
)

access_token_security = JwtAccessBearer(
    AUTH_KEY,
    algorithm=AUTH_KEY_ALGO,
    access_expires_delta=AUTH_TOKEN_EXPIRED,
    refresh_expires_delta=AUTH_REFRESH_TOKEN_EXPIRED,
    auto_error=True,
)
refresh_token_security = JwtRefreshBearer(
    AUTH_REFRESH_KEY,
    algorithm=AUTH_KEY_ALGO,
    access_expires_delta=AUTH_TOKEN_EXPIRED,
    refresh_expires_delta=AUTH_REFRESH_TOKEN_EXPIRED,
    auto_error=True,
)
oauth2_scheme = OAuth2PasswordBearer("token", auto_error=True)
