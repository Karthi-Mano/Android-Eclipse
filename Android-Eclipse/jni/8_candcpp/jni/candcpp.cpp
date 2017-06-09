#include <jni.h>
 #include<stdlib.h>
#include "com_example_candcpp_MainActivity.h"
//JNIEXPORT jstring JNICALL Java_com_itheima_chello_MainActivity_helloFromC
//  (JNIEnv * env, jobject obj){
//	return (*env)->NewStringUTF(env,"hello from c");
//}

char* Jstring2CStr(JNIEnv* env, jstring jstr) {
	char* rtn = 0;
	jclass clsstring = (env)->FindClass("java/lang/String");
	jstring strencode = (env)->NewStringUTF("GB2312");
	jmethodID mid = (env)->GetMethodID(clsstring, "getBytes",
			"(Ljava/lang/String;)[B");
	jbyteArray barr = (jbyteArray)(env)->CallObjectMethod(jstr, mid, strencode); // String .getByte("GB2312");
	jsize alen = (env)->GetArrayLength(barr);
	jbyte* ba = (env)->GetByteArrayElements(barr, JNI_FALSE);
	if (alen > 0) {
		rtn = (char*) malloc(alen + 1); //"\0"
		memcpy(rtn, ba, alen);
		rtn[alen] = 0;
	}
	(env)->ReleaseByteArrayElements(barr, ba, 0); //
	return rtn;
}

JNIEXPORT jstring JNICALL Java_com_example_candcpp_MainActivity_helloFromCpp(
		JNIEnv * env, jobject obj) {
	return (env)->NewStringUTF("hello from cpp");
	//return (*env)->NewStringUTF(env,"hello from c");
}
