from beanie import init_beanie
from motor.motor_asyncio import AsyncIOMotorClient

from app.accounts.models import Account
from app.settings import APP_SETTINGS

client = AsyncIOMotorClient(APP_SETTINGS.database_connection_url)
database = client.get_database("test")


async def init_database():
    await init_beanie(database=database, document_models=[Account])
