package com.thornbirds.repodemo;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;

public class MyClass {
    public static void main(String args[]) throws IOException {
        MyClass instance = new MyClass();
//        instance.generate();
    }

//    private void generate() throws IOException {
//        // 定义方法
//        MethodSpec main = MethodSpec.methodBuilder("main")
//                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)//修饰符
//                .returns(void.class)//返回值
//                .addParameter(String[].class, "args")//参数合参数类型
//                .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")//使用format
//                .build();
//
//        //生成类
//        TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")//类名字
//                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)//类修饰符
//                .addMethod(main)//添加方法
//                .build();
//        //生成一个顶级的java文件描述对象
//        JavaFile javaFile = JavaFile.builder("com.example.helloworld", helloWorld)
//                .build();
//        File outputFile = new File("javapoettest/src/main/java");
//        //输出文件
//        javaFile.writeTo(outputFile);
//    }
}