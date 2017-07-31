#!/usr/bin/env python
#coding: UTF-8

from base.command_parser import *
from base.package_parser import *

curr_exec_path = os.path.abspath('.')

command_info = parse_command_info(sys.argv)
if not command_info:
    print 'waring: no command info found.'
    exit()
elif not command_info.name:
    print 'error: parse command info failed, component name should be specified.'
    exit()
else:
    print 'info: command info is: ' + str(command_info)

package_info = parse_package_info(
    curr_exec_path, command_info.module, command_info.source, command_info.language, command_info.package)
if not package_info:
    print 'error: parse package info failed, please run this script under an android studio project.'
    exit()
else:
    print 'info: package info is: ' + str(package_info)




