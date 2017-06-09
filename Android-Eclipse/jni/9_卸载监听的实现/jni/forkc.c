#include <jni.h>
#include <stdlib.h>
#include <stdio.h>
#include <android/log.h>
#include <string.h>
#define LOG_TAG "System.out"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

void Java_com_example_xiezaijianting_MainActivity_callC(JNIEnv * env,
		jobject obj) {
	//fork();  获取进程 拆分进程
	int flag = 1;
	pid_t pid = fork(); //拆分进程
	if (pid == 0) { //0拆分成功
		while (flag) {
			//监视文件夹是否存在。
			//FILE *fopen(char *filename, char *type);
			FILE* f = fopen("/data/data/com.example.xiezaijianting", "rw");
			if (f == NULL) {
				//文件不存在
				LOGI("not exist,bei xiele");
				//调用c代码执行一个外部命令，开启界面
				//4.2以上的系统由于用户权限管理更严格，需要加上 --user 0
				execlp("am", "am", "start", "-a", "android.intent.action.VIEW",
						"-d", "http://www.baidu.com", (char *) NULL);
				flag = 0;
			} else {
				//文件存在
				LOGI("exist");
			}
			sleep(1);
		}
	}
}
