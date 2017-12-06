#!/usr/bin/env python
#coding: UTF-8

from create_controller_view import usage_helper as usage_helper1

def usage_helper():
    """Guard for using python scripts for component module
    """

    print usage_helper.__doc__
    usage_helper1()

if __name__ == "__main__":
    usage_helper()