#!/usr/bin/env python
#coding: UTF-8

import os

MODE_ALL = 0
MODE_ANY = 1

def find_project_module_path(curr_path, properties, mode = MODE_ALL):
    if not curr_path or not curr_path.strip():
        raise "error: current path is empty"
    if not properties or len(properties) == 0:
        print "waring: input properties is null, curr path will be returned"
        return curr_path
    count = 0
    for property in properties:
        property_path = os.path.join(curr_path, property)
        if os.path.exists(property_path):
            count += 1
    if (mode == MODE_ANY and count >= 1) or (mode == MODE_ALL and count == len(properties)):
        return curr_path
    parent_path = os.path.dirname(curr_path)
    if parent_path == '/':
        return None
    else:
        return find_project_module_path(parent_path, properties, mode)
