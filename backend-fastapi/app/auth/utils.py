from datetime import datetime, timedelta, timezone
from typing import Optional

from jwt import decode, encode

from app.auth.settings import (
    AUTH_KEY,
    AUTH_KEY_ALGO,
    AUTH_REFRESH_TOKEN_EXPIRED,
    AUTH_TOKEN_EXPIRED,
)


def _create_token(
    issuer: str,
    subject: dict,
    expires_delta: timedelta,
    key: Optional[str] = None,
):
    issued_at = datetime.now(timezone.utc)
    return encode(
        payload={
            "subject": subject,
            "exp": issued_at + expires_delta,
            "iss": f"app:auth:{issuer}",
            "iat": issued_at,
        },
        key=key or AUTH_KEY,
        algorithm=AUTH_KEY_ALGO,
    )


def create_access_token(subject: dict):
    return _create_token("access_token", subject, AUTH_TOKEN_EXPIRED)


def create_refresh_token(subject: dict):
    return _create_token("refresh_token", subject, AUTH_REFRESH_TOKEN_EXPIRED)


def create_account_access_token(username: str):
    return create_access_token({"username": username})


def read_token(token: str, key: str) -> dict:
    return decode(token, key, algorithms=[AUTH_KEY_ALGO])


if __name__ == "__main__":
    from secrets import token_hex

    from rich.pretty import pprint
    from rich.prompt import Prompt

    print("Test auth token generation")
    username = Prompt.ask("Enter username")
    key = Prompt.ask(f"Enter key", default=token_hex(32), show_default=True)
    token = _create_token("test", {"testUsername": username}, timedelta(seconds=5), key)
    pprint(token)
    pprint(read_token(token, key))
