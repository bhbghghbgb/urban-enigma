from decouple import AutoConfig

config = AutoConfig()

USERNAME_PATTERN = config("USERNAME_PATTERN", default=".")
PASSWORD_PATTERN = config("PASSWORD_PATTERN", default=".")
VIETNAMESE_PHONE_PATTERN = config("VIETNAMESE_PHONE_PATTERN", default=".")
FULL_NAME_PATTERN = config("FULL_NAME_PATTERN", default=".")
