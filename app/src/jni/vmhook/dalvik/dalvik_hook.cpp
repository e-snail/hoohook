
#include <time.h>
#include <stdlib.h>
#include <stddef.h>
#include <assert.h>

#include <stdbool.h>
#include <fcntl.h>
#include <dlfcn.h>

#include <stdio.h>
#include <stdint.h>
#include <sys/stat.h>
#include <dirent.h>
#include <unistd.h>
#include <ctype.h>
#include <errno.h>
#include <utime.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <jni.h>

extern jboolean __attribute__ ((visibility ("hidden"))) dalvik_setup(JNIEnv* env, int apilevel) {

	return JNI_FALSE;
}

