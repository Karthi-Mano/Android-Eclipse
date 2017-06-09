LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := candcpp
LOCAL_SRC_FILES := candcpp.cpp

include $(BUILD_SHARED_LIBRARY)
