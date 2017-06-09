   LOCAL_PATH := $(call my-dir)

   include $(CLEAR_VARS)

   LOCAL_MODULE    := YeKatad
   LOCAL_SRC_FILES := YeKatad.c

   include $(BUILD_SHARED_LIBRARY)