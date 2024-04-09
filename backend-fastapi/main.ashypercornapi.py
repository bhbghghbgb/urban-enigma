from typing import Union

from fastapi import FastAPI
from hypercorn.config import Config
from hypercorn.trio import serve
from trio import run

app = FastAPI()


@app.get("/")
def read_root():
    return {"Hello": "World"}


@app.get("/items/{item_id}")
def read_item(item_id: int, q: Union[str, None] = None):
    return {"item_id": item_id, "q": q}


config = Config()
config.bind = ["localhost:3001"]


run(serve, app, config)
