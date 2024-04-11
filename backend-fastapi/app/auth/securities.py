from fastapi.security import OAuth2PasswordBearer

oauth2_userpass_security = OAuth2PasswordBearer(tokenUrl="/test/login")