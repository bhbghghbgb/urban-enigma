from pydantic_settings import BaseSettings


class Settings(BaseSettings):
    # length constraints are hardcoded in model.py
    
    # username can contain character and digits
    username_pattern: str = r"^[A-Za-z0-9_-]*$"
    # password specifications:
    # Should contain at least a capital letter
    # Should contain at least a small letter
    # Should contain at least a number
    # Should contain at least a special character
    password_pattern: str = (
        r"^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).*$"
    )
    # vietnamese phone number spec:
    # Các đầu số 03, 05, 07, 08, 09 (ví dụ: 0981234567)
    # Số có thể bắt đầu với +84 hoặc 84 (ví dụ +84981234567, 84981234567)
    # Các đầu số Vinaphone là: 091, 094, 088, 083, 084, 085, 081, 082
    # Các đầu số Viettel là: 086, 096, 097, 098, 032, 033, 034, 035, 036, 037, 038, 039
    # Các đầu số Mobilephone là: 089, 090, 093, 070, 079, 077, 076, 078
    # Các mạng ảo như iTel bắt đầu bằng: 087
    # Wintel: 055
    # VNSKY: 077
    # Local: 089
    vietnamese_phone_pattern: str = r"^(((\+|)84)|0)(3|5|7|8|9)+([0-9]{8})$"
    # https://stackoverflow.com/questions/3819791/regex-in-vietnamese-characters
    # List of correct Vietnamese characters: àáãạảăắằẳẵặâấầẩẫậèéẹẻẽêềếểễệđìíĩỉịòóõọỏôốồổỗộơớờởỡợùúũụủưứừửữựỳỵỷỹýÀÁÃẠẢĂẮẰẲẴẶÂẤẦẨẪẬÈÉẸẺẼÊỀẾỂỄỆĐÌÍĨỈỊÒÓÕỌỎÔỐỒỔỖỘƠỚỜỞỠỢÙÚŨỤỦƯỨỪỬỮỰỲỴỶỸÝ
    # Also, remember to normalize the string in NFC form (string.normalize('NFC')) before testing it with the regex.
    full_name_pattern: str = (
        r"^[ a-z0-9A-Z_àáãạảăắằẳẵặâấầẩẫậèéẹẻẽêềếểễệđìíĩỉịòóõọỏôốồổỗộơớờởỡợùúũụủưứừửữựỳỵỷỹýÀÁÃẠẢĂẮẰẲẴẶÂẤẦẨẪẬÈÉẸẺẼÊỀẾỂỄỆĐÌÍĨỈỊÒÓÕỌỎÔỐỒỔỖỘƠỚỜỞỠỢÙÚŨỤỦƯỨỪỬỮỰỲỴỶỸÝ]+$"
    )


ACCOUNT_SETTINGS = Settings()
