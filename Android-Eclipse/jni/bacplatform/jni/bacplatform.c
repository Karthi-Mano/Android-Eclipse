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
jstring Java_com_bac_bacplatform_SubSplash_BeforeLoginActivity_encodeStr(JNIEnv* env,
		jobject obj, jstring jstr, jint len) {


	char* cstr = Jstring2CStr(env, jstr);

	int i;

	for (i = 0; i < len; i++) {
		*(cstr + i) -=i;
	}
	return (*env)->NewStringUTF(env, cstr);
}
