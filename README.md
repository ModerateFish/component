===在AndroidStudio中使用component库
(1) 在工程build.gradle文件中加入如下语句：
 allprojects {
     repositories {
         ...
         maven {
             url 'https://raw.githubusercontent.com/ModerateFish/mvn-repo/master/'
         }
     }
 }

(2) 在模块的build.gradle文件中加入语句引入依赖：
 dependencies {
     ...
     compile 'com.thornbirds:component:1.0.1'
     compile 'com.thornbirds:componentAndroid:1.0.1'
 }

===自动生成代码的python脚本使用

源代码地址：https://github.com/ModerateFish/component/tree/dev

将python目录拷贝出来，运行setup_python.sh即可
