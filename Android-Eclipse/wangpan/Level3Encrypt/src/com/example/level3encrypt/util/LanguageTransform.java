package com.example.level3encrypt.util;

/**
 * 从C代码中获取数据
 * 
 * @author YUY
 * 
 * NDK开发流程： 
	1、创建Android工程
	2、完成简单的页面处理(页面布局文件)
	3、在java代码中 声明本地方法(native)
	(3-4之间)
	   通过javah 工具 完成jni样式的头文件(。h文件)的生成	
	4、在工程中 创建jni目录，在jni目录中编写 与java本地方法对应的C方法
		jstring Java_包名_类名_方法名(){...}
	5、编写Android.mk文件，来指定如何编译C代码
	6、通过ndk-build工具来完成交叉编译 ，生成Anroid 中可以调用的2进制文件（链接库文件）
	7、将生成的  链接库文件 在 声明本地方法的java文件中 加载   System.loadLibrary("hello-jni");
	8、在java代码中 使用声明的本地方法即可
 */
public class LanguageTransform {
	
	//---------------------单例--------------------------
	private static LanguageTransform instance = null; 
	private LanguageTransform(){}

	public static LanguageTransform getInstance(){
		if(instance==null){
			synchronized (LanguageTransform.class) {
				if(instance==null){
					instance = new LanguageTransform();
				}
			}
		}
		return instance;
	}
	//---------------------单例--------------------------
	
	
	static {
		// 加载链接库文件
		System.loadLibrary("YeKatad");
	}

	// 本地方法 c实现
	public native String getDataFromC();

	//从c中获取秘钥
	public static String getKey(){
		return getInstance().getDataFromC();
	}
}
