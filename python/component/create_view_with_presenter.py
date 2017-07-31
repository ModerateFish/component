#!/usr/bin/env python
#coding: UTF-8

import os

from base.creator_base import *
from base.utils import *
from base.template_info import *

# 生成View
class_package = root_package + '.view'
class_import = 'import %s.presenter.%sPresenter;' %(root_package, name)
view = TemplateInfo(class_package, class_import, name, command, user_name, user_date)
out_file = os.path.join(root_path, 'view', name + 'View.java')
check_path(os.path.join(root_path, 'view'))
view.write_to_file(os.path.join(template_path, 'TemplateView.java'), out_file)

# 生成Presenter
class_package = root_package + '.presenter'
class_import = 'import %s.view.%sView;' %(root_package, name)
view = TemplateInfo(class_package, class_import, name, command, user_name, user_date)
out_file = os.path.join(root_path, 'presenter', name + 'Presenter.java')
check_path(os.path.join(root_path, 'presenter'))
view.write_to_file(os.path.join(template_path, 'TemplatePresenter.java'), out_file)