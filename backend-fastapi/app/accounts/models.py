"""Account models."""

from datetime import datetime
from typing import Annotated, Literal, Optional

from beanie import Document
from pydantic import (
    BaseModel,
    ConfigDict,
    EmailStr,
    PastDate,
    SecretStr,
    StringConstraints,
)

from app.accounts.settings import (
    PASSWORD_PATTERN,
    USERNAME_PATTERN,
    VIETNAMESE_PHONE_PATTERN,
)


class AccountAuth(BaseModel):
    """Account signup and login auth."""

    username: Annotated[
        str,
        StringConstraints(
            strip_whitespace=True,
            to_lower=True,
            min_length=6,
            max_length=32,
            pattern=USERNAME_PATTERN,
        ),
    ]
    password: Annotated[
        str,
        StringConstraints(
            min_length=8,
            max_length=128,
            pattern=PASSWORD_PATTERN,
        ),
    ]
    role: Literal["customer", "shipper"]

    # password pattern uses lookahead
    model_config = ConfigDict(regex_engine="python-re")


class AccountUpdatable(BaseModel):
    """Updatable account fields."""

    # Account information
    email: Optional[Annotated[EmailStr, StringConstraints(max_length=128)]] = None
    vietnamese_phone: Optional[
        Annotated[
            str,
            StringConstraints(
                strip_whitespace=True,
                min_length=10,
                max_length=12,
                pattern=VIETNAMESE_PHONE_PATTERN,
            ),
        ]
    ] = None
    # User information
    full_name: Optional[
        Annotated[
            str, StringConstraints(strip_whitespace=True, min_length=1, max_length=128)
        ]
    ] = None
    date_of_birth: Optional[PastDate] = None


class Account(Document, AccountAuth, AccountUpdatable):
    """Account DB representation."""

    email_confirmed_at: Optional[datetime] = None

    class Settings:
        name = "accounts"

    class Config:
        schema_extras = {
            "example": {
                "username": "memaybeo",
                "password": "mOt ha1 b@",
                "email": "bamaymup@microgle.com",
                "vietnamese_phone": "84366777888",
                "full_name": "Ta La Vuong Lao Gia",
                "date_of_birth": 978307200000,
            }
        }

    def __hash__(self) -> int:
        return hash(self.id)

    def __eq__(self, value: object) -> bool:
        return isinstance(value, Account) and self.id == value.id

    @property
    def created_at(self) -> Optional[datetime]:
        """The time the user is created and inserted into the db. Returns mongodb ObjectId generation time."""
        return self.id.generation_time if self.id else None

    @property
    def jwt_subject(self) -> dict[str, str]:
        """JWT subject fields. Use the returned value for JWT authorization purposes."""
        return {"username": self.username}

    @classmethod
    async def find_by_email(cls, email: str):
        """Find an Account instance by email. Case sensitive."""
        return await cls.find_one(cls.email == email)

    @classmethod
    async def find_by_vietnamese_phone(cls, vietnamese_phone: str):
        """Find an Account instance by vietnamese phone."""
        return await cls.find_one(cls.vietnamese_phone == vietnamese_phone)

    @classmethod
    async def update_email(cls, new_email: str):
        raise NotImplementedError(f"Change email WIP {new_email}")

    @classmethod
    async def update_vietnamese_phone(cls, new_vietnamese_phone: str):
        raise NotImplementedError(f"Change vietnamese_phone WIP {new_vietnamese_phone}")
