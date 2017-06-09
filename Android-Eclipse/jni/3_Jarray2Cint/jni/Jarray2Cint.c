#include <jni.h>

void Java_com_example_jarray2cint_MainActivity_changeArray(
		JNIEnv* env, jobject obj, jintArray jintarr) {

	//    jsize       (*GetArrayLength)(JNIEnv*, jarray);

	//得到了数组的长度
	int len = (*env)->GetArrayLength(env, jintarr);

	//  jint*       (*GetIntArrayElements)(JNIEnv*, jintArray, jboolean*);

	//获取数组里的元素
	jint* cintarr = (*env)->GetIntArrayElements(env, jintarr, 0);

	int i;
	for (i = 0; i < len; i++) {
		(*(cintarr + i)) += 10;

	}
}
