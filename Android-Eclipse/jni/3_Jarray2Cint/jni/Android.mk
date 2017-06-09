LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := Jarray2Cint
LOCAL_SRC_FILES := Jarray2Cint.c

include $(BUILD_SHARED_LIBRARY)
