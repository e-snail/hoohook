
#include <time.h>
#include <stdlib.h>
#include <stddef.h>
#include <assert.h>

#include <stdbool.h>
#include <fcntl.h>
#include <dlfcn.h>

#include <sys/stat.h>
#include <dirent.h>
#include <unistd.h>
#include <ctype.h>
#include <errno.h>
#include <utime.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <android/log.h>
#include <jni.h>

#include "art.h"
#include "../common.h"

static int apilevel;

extern jboolean __attribute__ ((visibility ("hidden"))) art_setup(JNIEnv* env, int level) {
	apilevel = level;
	return JNI_TRUE;
}

extern void __attribute__ ((visibility ("hidden"))) art_setFieldFlag(JNIEnv* env, jobject field) {
	if (apilevel > 22) {
		setFieldFlag_6_0(env, field);
	} else if (apilevel > 21) {
		//setFieldFlag_5_1(env, field);
	} else {
		//setFieldFlag_5_0(env, field);
	}
}
