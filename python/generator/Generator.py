import abc

class Generator:

    @abc.abstractmethod
    def add_component(self): return self

    @abc.abstractmethod
    def generate(self): pass
