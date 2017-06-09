#include <jni.h>

jstring Java_com_example_hellojni_MainActivity_helloFromC
(JNIEnv* env,jobject obj) {
	char* arr = "hello from jni!";
	return (*env)->NewStringUTF(env,arr);
}

