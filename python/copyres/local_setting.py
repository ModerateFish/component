#!/usr/bin/env python
#coding:UTF-8

import os
import shutil

# 待拷贝资源的所在路径
org_res_path = "/Users/yangli/Development/huyu/walilive/app/src/main/res"

if not os.path.exists(org_res_path):
    print "error: org res path not found, please modify org_res_path in " \
          + os.path.basename(__file__)
    exit()
