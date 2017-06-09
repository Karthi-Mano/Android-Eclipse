LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_LDLIBS += -llog#添加log的函数库支持
LOCAL_MODULE    := ShowDialog
LOCAL_SRC_FILES := ShowDialog.c

include $(BUILD_SHARED_LIBRARY)
