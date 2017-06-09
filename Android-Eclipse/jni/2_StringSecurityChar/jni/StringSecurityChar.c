#include <jni.h>
#include <string.h>

/**
 * 把java的string转化成c语言的字符串数组
 * env 虚拟机的环境
 * jstr 要转化的java的字符串
 */
char* Jstring2CStr(JNIEnv* env, jstring jstr) {
	char* rtn = NULL;
	jclass clsstring = (*env)->FindClass(env, "java/lang/String");
	jstring strencode = (*env)->NewStringUTF(env, "GB2312");
	jmethodID mid = (*env)->GetMethodID(env, clsstring, "getBytes",
			"(Ljava/lang/String;)[B");
	jbyteArray barr = (jbyteArray) (*env)->CallObjectMethod(env, jstr, mid,
			strencode); // String .getByte("GB2312");
	jsize alen = (*env)->GetArrayLength(env, barr);
	jbyte* ba = (*env)->GetByteArrayElements(env, barr, JNI_FALSE);
	if (alen > 0) {
		rtn = (char*) malloc(alen + 1); //"\0"
		memcpy(rtn, ba, alen);
		rtn[alen] = 0;
	}
	(*env)->ReleaseByteArrayElements(env, barr, ba, 0); //
	return rtn;
}

/**
 * c 语言加密字符串
 *
 */
jstring Java_com_example_stringsecuritychar_MainActivity_encodeStr(JNIEnv* env,
		jobject obj, jstring jstr, jint len) {

	//c语言中的字符串和java语言中的字符串不通用.
	char* cstr = Jstring2CStr(env, jstr); //把java的字符串转化成了 c语言的字符串数组
	// abc ---> bcd
	int i;
	for (i = 0; i < len; i++) {
		*(cstr + i) += 1;
	}
	return (*env)->NewStringUTF(env, cstr);
}

/**
 * c 语言解密字符串
 *
 */
jstring Java_com_example_stringsecuritychar_MainActivity_decodeStr(JNIEnv * env,
		jobject obj, jstring jstr, jint len) {
	//c语言中的字符串和java语言中的字符串不通用.
	char* cstr = Jstring2CStr(env, jstr); //把java的字符串转化成了 c语言的字符串数组
	// abc ---> bcd
	int i;
	for (i = 0; i < len; i++) {
		*(cstr + i) -= 1;
	}
	return (*env)->NewStringUTF(env, cstr);
}
