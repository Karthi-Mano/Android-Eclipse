#include <jni.h>

jstring Java_com_example_level3encrypt_util_LanguageTransform_getDataFromC(JNIEnv* env,
		jobject obj) {
	//JNIEnv* env  jni环境的上下文对象
	//jobject obj  调用该方法的java对象

	//返回一个java字符串,即秘钥
	char* cstr = "myKey0510YUY1234";

	//将c字符串 转换成 java字符串
	//	   jstring     (*NewStringUTF)(JNIEnv*, const char*);
	jstring jstr = (**env).NewStringUTF(env, cstr);
	return jstr;
}
