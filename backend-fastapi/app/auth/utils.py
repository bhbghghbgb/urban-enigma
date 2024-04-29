from datetime import datetime, timezone

from jose import jwt
from passlib.hash import bcrypt

from app.auth.models import AccessToken, TokenData
from app.auth.settings import AUTH_SETTINGS


def create_access_token(subject: TokenData):
    """Returns an access token associated with a TokenData. Expiration is defined in module's settings."""
    return AccessToken(
        access_token=jwt.encode(
            {
                "sub": subject.username,
                "exp": datetime.now(timezone.utc) + AUTH_SETTINGS.auth_access_expire,
            },
            AUTH_SETTINGS.auth_access_key,
        )
    )


def hash_password(password: str):
    """Hash password."""
    return bcrypt.hash(password)


def verify_password(password: str, hashed: str):
    """Verify password with a hash."""
    return bcrypt.verify(password, hashed)
