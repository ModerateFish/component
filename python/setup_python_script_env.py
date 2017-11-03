#!/usr/bin/env python
#coding: UTF-8

import os

print "################################description##################################"
print "# Setup environment for all python modules under current path, by appending path item for the"
print "# subdirectory to environment variable called PYTHONPATH in ~/.bash_profile."
print "#"
print "# [Note] After this script run successfully, you can execute python file under subdirectory"
print "# from any path on this computer, by using the following command:"
print "# \"python -m python_file [params]\"."
print "#"
print "# [Example] Generate both view and presenter for a component called Test under module"
print "# watchclient from project livesdk, as follows:"
print "# \"cd ~/Development/huyu/livesdk/watchclient\""
print "# \"python -m create_view_with_component Test\""
print "#"
print "# You can also import python file under subdirectory by using the following statement:"
print "# \"import python_file\" or \"from python_file import *\"."
print "#############################################################################"

print "\n###################################setup#####################################"

def getSystemEnviron(environKey):
    environValue = ''
    try:
        environValue = os.environ[environKey]
        print "environment %s is: %s" % (environKey, environValue)
    except KeyError:
        print "environment %s is not exist" % environKey
    return environValue

modulePath = os.path.abspath('.')

baseProfile = '~/.bash_profile'
os.system('source ' + baseProfile)

pythonPath = getSystemEnviron('PYTHONPATH')

# modules to be added
modules = ['component', 'copyres']
print "modules to be added is: " + ", ".join(modules)

newPath = ''
for elem in modules:
    currPath = os.path.join(modulePath, elem)
    if pythonPath.find(currPath) != -1:
        print "waring: PYTHONPATH already contains " + currPath
    else:
        newPath = '%s\$TEMP_PATH/%s:' % (newPath, elem)

if newPath:
    print "add path:\"%s\" to PYTHONPATH in %s" % (newPath, baseProfile)
    os.system('echo "\nTEMP_PATH=\\\"%s\\\"" >>%s' % (modulePath, baseProfile))
    os.system('echo "export PYTHONPATH=%s\$PYTHONPATH" >>%s' % (newPath, baseProfile))
    os.system('source ' + baseProfile)
    getSystemEnviron('PYTHONPATH')
else:
    print "waring: no path need to be added to PYTHONPATH"

print "####################################done#####################################"

os.system('cat ' + baseProfile)

