#include <jni.h>
#include <stdlib.h>
#include <stdio.h>
int flag;

//模拟获取锅炉的压力.
int getPressure(){
    //获取硬件.
    //读取管脚信息
    //里面的代码可能有几十行甚至上百行,或者是一个方法又依赖了几十个 几百个方法.
    return rand() % 100;
}

/**
 * 开始监视
 */
void Java_com_example_wulianwang_MainActivity_startMonitor
  (JNIEnv* env, jobject obj){
	//看门狗逻辑,一直不停的循环监视压力的情况.
	flag = 1;
	while(flag){
		int pressure = getPressure();
		jclass  clazz = (*env)->FindClass(env,"com/example/wulianwang/MainActivity");
		jmethodID  methodid = (*env)->GetMethodID(env,clazz,"setPb","(I)V");
		(*env)->CallVoidMethod(env,obj,methodid,pressure);
		if(pressure>98){
			//通知管理员进行进一步的操作
		}
		sleep(1);
	}
}

/**
 * 停止监视
 */
void Java_com_example_wulianwang_MainActivity_stopMonitor
  (JNIEnv* env, jobject obj){
	flag = 0;
}
