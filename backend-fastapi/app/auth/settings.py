from datetime import timedelta
from pydantic_settings import BaseSettings


class Settings(BaseSettings):
    auth_access_key: str = (
        "dd6a6faa0ffd4c9c1ac47feb4e9d2284d9fe39959b590f70a92ca7b4ef4c6701"
    )
    auth_access_expire: timedelta = timedelta(days=1)
    auth_refresh_key: str = (
        "220ef76c6f609a5c6101457b8d62a5934bb2dff6159454673790afcd7ebbbe36"
    )
    auth_refresh_expire: timedelta = timedelta(days=7)


AUTH_SETTINGS = Settings()
