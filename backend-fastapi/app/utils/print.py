"""Print a globals dict containing only UPPERCASE_KEYs (constants)"""

from rich.pretty import pprint


def pprint_constants(globals: dict):
    pprint({k: v for k, v in globals.items() if k.isupper()})
