# Copyright (C) 2009 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

#build sogouime lib
LOCAL_PATH:= $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE_TAGS := eng

#LOCAL_CFLAGS
LOCAL_CFLAGS += -Wall -Wextra -Wno-non-virtual-dtor -DNDEBUG -DOS_LINUX -std=gnu++11 -fpermissive
ifeq ($(WITH_SYMBOL_TABLE),true)
	LOCAL_CFLAGS += -O0 -ggdb3 -fno-inline -g
else 
	LOCAL_CFLAGS += -O2
endif

#source file
LOCAL_SRC_FILES:= \
	$(LOCAL_PATH)/vmhook/hook.cpp \
	$(LOCAL_PATH)/vmhook/dalvik/dalvik_hook.cpp \
	$(LOCAL_PATH)/vmhook/art/art_hook.cpp \
	$(LOCAL_PATH)/vmhook/art/art_method_replace_6_0.cpp

#include path
LOCAL_C_INCLUDES += \
	$(JNI_H_INCLUDE) \
	$(LOCAL_PATH)/vmhook/hook.h \
	$(LOCAL_PATH)/vmhook/art/art_6_0.hi \
	$(LOCAL_PATH)/vmhook/art/art.h 


#LOCAL_SHARED_LIBRARIES
LOCAL_SHARED_LIBRARIES := \
	libandroid_runtime \
	libnativehelper \
	libcutils \
	libutils  \
	libdl

#ld parameters
LOCAL_LDFLAGS += -shared 

LOCAL_LDLIBS  += -llog

# disable prelink
LOCAL_PRELINK_MODULE := false

LOCAL_MODULE:= hoohook 

include $(BUILD_SHARED_LIBRARY)
#----------------------------sogouime--------------------------------------
