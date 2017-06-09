LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := bacplatform
LOCAL_SRC_FILES := bacplatform.c

include $(BUILD_SHARED_LIBRARY)
