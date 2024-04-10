from decouple import config
from isodate import parse_duration

from app.utils.print import pprint_constants

AUTH_KEY = config("AUTH_KEY")
AUTH_REFRESH_KEY = config("AUTH_REFRESH_KEY")
AUTH_KEY_ALGO = config("AUTH_KEY_ALGO")
AUTH_TOKEN_EXPIRED = parse_duration(config("AUTH_TOKEN_EXPIRED"))
AUTH_REFRESH_TOKEN_EXPIRED = parse_duration(config("AUTH_REFRESH_TOKEN_EXPIRED"))


if __name__ == "__main__":
    print("Print defined settings")
    pprint_constants(globals())
