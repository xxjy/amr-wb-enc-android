LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

T_LOCAL_PATH := $(LOCAL_PATH)

include $(T_LOCAL_PATH)/common/Config.mk
include $(T_LOCAL_PATH)/common/Android.mk
include $(T_LOCAL_PATH)/amrwbenc/Android.mk

include $(CLEAR_VARS)

LOCAL_PATH := $(T_LOCAL_PATH)

LOCAL_MODULE := amr-wb-enc
LOCAL_SRC_FILES := wrapper.c \
				amr_wb_encoder.cpp

LOCAL_C_INCLUDES := $(LOCAL_PATH)/common/include \
                 	$(LOCAL_PATH)/amrwbenc/inc \
                 	$(LOCAL_PATH)/amrwbenc/src

LOCAL_LDLIBS := -L$(SYSROOT)/usr/lib -llog
LOCAL_SHARED_LIBRARIES := libstagefright_enc_common \
						libstagefright_amrwbenc


include $(BUILD_SHARED_LIBRARY)