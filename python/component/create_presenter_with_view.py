#!/usr/bin/env python
#coding: UTF-8

from base.package_parser import *

curr_exec_path = os.path.abspath('.')

module_path = find_project_module_path(curr_exec_path, ['build.gradle'], MODE_ALL)
print "module path is " + str(module_path)

project_path = find_project_module_path(curr_exec_path, ['build.gradle', 'settings.gradle'], MODE_ALL)
print "project path is " + str(project_path)
