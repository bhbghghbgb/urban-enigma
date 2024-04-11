from datetime import datetime

from pydantic import BaseModel


class AccessToken(BaseModel):
    access_token: str
    access_token_expire: datetime


class RefreshToken(BaseModel):
    refresh_token: str
    refresh_token_expire: datetime


class AccountSessionToken(AccessToken, RefreshToken):
    pass
