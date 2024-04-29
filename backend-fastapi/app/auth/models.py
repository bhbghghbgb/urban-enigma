from pydantic import BaseModel


class TokenData(BaseModel):
    """Identification data present in a token."""

    username: str


class AccessToken(BaseModel):
    """Access token model returned to user for successful login."""

    access_token: str


class RefreshToken(BaseModel):
    """Refresh token model returned to user for successful login."""

    refresh_token: str


class AccountSessionToken(AccessToken, RefreshToken):
    """Final model containing access and refresh token returned to user for successful login."""

    pass
