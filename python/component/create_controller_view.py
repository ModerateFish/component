#!/usr/bin/env python
#coding: UTF-8

import os

def usage_helper():
    """Create a Presenter, which has no View, and no need to refresh UI.
    Command:
        python [-m] create_controller_view -n component_name
    For more usages, try following command:
        python [-m] create_controller_view -h
    """
    print "Usage Guard for " + __name__ + '.py'
    print usage_helper.__doc__

if __name__ == "__main__":
    from base.creator_base import *
    from base.utils import *
    from base.template_info import *

    # 生成View
    name1 = name + 'View'
    name2 = name + 'Controller'
    class_package = root_package
    # class_import = 'import %s.%s;' %(root_package, name2)
    class_import = ''
    template_view = TemplateInfo(class_package, class_import, name1, name2, command, user_name, user_date)
    out_file = os.path.join(root_path, name1 + '.java')
    check_path(os.path.dirname(out_file))
    template_view.write_to_file(os.path.join(template_path, 'ControllerView.java'), out_file)