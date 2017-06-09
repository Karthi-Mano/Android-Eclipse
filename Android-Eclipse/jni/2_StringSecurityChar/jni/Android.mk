LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := StringSecurityChar
LOCAL_SRC_FILES := StringSecurityChar.c

include $(BUILD_SHARED_LIBRARY)
