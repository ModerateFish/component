class Builder:
    message = ''

    def __init__(self):
        pass

    def add_state(self, message):
        self.message = '\n' + message
        return self

    def build(self):
        return self.message

