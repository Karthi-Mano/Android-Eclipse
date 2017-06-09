LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_LDLIBS += -llog
LOCAL_MODULE    := forkc
LOCAL_SRC_FILES := forkc.c

include $(BUILD_SHARED_LIBRARY)
