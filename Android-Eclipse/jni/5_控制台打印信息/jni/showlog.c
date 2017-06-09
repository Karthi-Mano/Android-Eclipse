#include <jni.h>
#include <string.h>
#include <android/log.h>
#define LOG_TAG "System.out"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

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
	jbyteArray barr = (jbyteArray)(*env)->CallObjectMethod(env, jstr, mid,
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
 * 安全支付操作
 */

jint Java_com_example_printlog_MainActivity_safePay(JNIEnv* env, jobject obj,
		jstring jusername, jstring jpassword, jfloat jmoney) {

	//把日志打印到控制台 printf();
	char* cusername = Jstring2CStr(env, jusername);
	char* cpassword = Jstring2CStr(env, jpassword);

	LOGI("cusername=%s", cusername);
	LOGI("cpassword=%s", cpassword);
	LOGI("jia mi cusername"); //调用java代码 实现界面.
	LOGI("jia mi cpassword");

	LOGI("jiancha anquanzhifu huanjing");
	LOGI("lianjie aliyun fuwuqi");
	LOGI("fuwuqi jiachayonghum mima shifouzhengque");
	//假设正确的用户名 abc 密码是123
	//支付限额是5000块
	int ptr1;
	ptr1 = strcmp(cusername, "abc");
	int ptr2;
	ptr2 = strcmp(cpassword, "123");
	if (ptr1 == 0 && ptr2 == 0) {
		if (jmoney > 5000) {
			return 250;
		} else {
			return 200;
		}
	} else {
		return 404;
	}

}
