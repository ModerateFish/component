from Generator import Generator

class FileGenerator(Generator):
    path = ''
    _package = None
    _imports = None

    def __init__(self, path = '.'):
        self.path = path
        pass

    def add_component(self, component):
        self.components += component.build()
        return self

    def generate(self):
        fd = open(self.path, 'w')
        try:
            fd.write(self.components)
        finally:
            fd.close()
        pass
