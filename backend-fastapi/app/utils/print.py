from typing import Any
from rich.pretty import pprint


def pprint_constants(globals: dict[str, Any]):
    """Print a globals dict containing only UPPERCASE_KEYs (constants)"""
    pprint({k: v for k, v in globals.items() if k.isupper()})
