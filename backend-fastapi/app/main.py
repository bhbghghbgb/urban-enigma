from fastapi import FastAPI
from fastapi.concurrency import asynccontextmanager
from fastapi.responses import RedirectResponse

from app.database import init_database
from app.test.routers import test_router


@asynccontextmanager
async def lifespan(app: FastAPI):
    # on startup
    await init_database()
    yield
    # on shutdown
    pass


app = FastAPI(lifespan=lifespan)
app.include_router(test_router)


@app.get("/")
async def root_to_docs():
    return RedirectResponse("/docs")
