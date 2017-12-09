from Builder import Builder
from FileGenerator import FileGenerator

if __name__ == '__main__':
    print "run generate_presenter"

    fileGenerator = FileGenerator('./out.java')

    fileGenerator\
        .add_component(Builder().add_state("package com.java.foo;"))\
        .add_component(Builder().add_state("public class a {}"))

    fileGenerator.generate()