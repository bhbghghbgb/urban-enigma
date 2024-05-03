from pydantic_settings import BaseSettings


class Settings(BaseSettings):
    database_connection_url: str = ""


APP_SETTINGS = Settings()
