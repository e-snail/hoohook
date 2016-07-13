package roof.hoohook.jni;

import android.os.Build;
import android.util.Log;

/**
 * Created by wuyongbo on 16-7-12.
 */
public class HookInterface {

    final static String TAG = HookInterface.class.getSimpleName();

    static {
        try {
            System.loadLibrary("hoohook");
        } catch (Throwable e) {
            Log.e(TAG, "loadLibrary", e);
        }
    }

    public static boolean setup() {
        try {
            final String vmVersion = System.getProperty("java.vm.version");
            boolean isArt = vmVersion != null && vmVersion.startsWith("2");
            int apiLevel = Build.VERSION.SDK_INT;
            return setupNative(isArt, apiLevel);
        } catch (Exception e) {
            Log.e(TAG, "setup", e);
            return false;
        }
    }

    private static native boolean setupNative(boolean isArt, int apiLevel);
    public native int addNative(int add1, int add2);
    public native int subNative(int sub1, int sub2);

    public native boolean hookArtMethod(Object src, Object target);
    public native boolean hookDalvikMethod(Object src, Object target);
}
