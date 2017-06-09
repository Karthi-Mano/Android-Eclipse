LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_LDLIBS += -llog#添加log的函数库支持
LOCAL_MODULE    := showlog
LOCAL_SRC_FILES := showlog.c

include $(BUILD_SHARED_LIBRARY)
