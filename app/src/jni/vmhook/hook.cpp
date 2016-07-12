#include <jni.h>
#include <assert.h>
#include <android/log.h>

#include <stdio.h>
#include <assert.h>
#include <stdint.h>
#include <unistd.h>
#include <sys/inotify.h>
#include <fcntl.h>

#include "hook.h"
#include "common.h"

#ifndef NELEM
#define NELEM(x)  ((int)(sizeof(x)/sizeof((x)[0])))
#endif

/** for Dalvik */
extern jboolean dalvik_setup(JNIEnv* env, int apilevel);

/** for ART */
extern jboolean art_setup(JNIEnv* env, int apilevel);

static bool isArt;

static jboolean setup(JNIEnv* env, jclass clazz, jboolean vm_art, jint apilevel) {

	isArt = vm_art;

	LOGD("vm is: %s , apilevel is: %i", (isArt ? "art" : "dalvik"), (int)apilevel);

	if (isArt) {
		return art_setup(env, (int) apilevel);
	} else {
		return dalvik_setup(env, (int) apilevel);
	}
}

static jint addNative(JNIEnv *env, jobject thiz, jint add1, jint add2) {
	return add1 + add2;
}

static jint subNative(JNIEnv *env, jobject thiz, jint sub1, jint sub2) {
	return sub1 - sub2;
}

static JNINativeMethod gMethods[] = {
	{"setupNative", "(ZI)Z", (void*) setup }, 
	{"addNative", 	"(II)I", (void*) addNative },
	{"subNative", 	"(II)I", (void*) subNative }
};

static int register_jni(JNIEnv *env) {

	char const *const kClassPathName = "roof/hoohook/jni/HookInterface";

	jclass clazz = env->FindClass(kClassPathName);

    if (env->RegisterNatives(clazz, gMethods, NELEM(gMethods)) < 0) {
        return JNI_FALSE;
    }

	return JNI_TRUE;
}

jint JNI_OnLoad(JavaVM* vm, void* reserved) {

	(void) reserved;

	JNIEnv * env = NULL;
	jint result = -1;

	if (vm->GetEnv((void**) &env, JNI_VERSION_1_4) != JNI_OK) {
		goto bail;
	}
	assert(env != NULL);

	if (register_jni(env) < 0) {
		LOGE("ERROR: register_jni failed!\n");
		goto bail;
	}

	result = JNI_VERSION_1_4;

	bail:
		return result;
}
