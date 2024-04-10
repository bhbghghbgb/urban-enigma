from fastapi.security import HTTPBearer


class JWTBearer(HTTPBearer):
    