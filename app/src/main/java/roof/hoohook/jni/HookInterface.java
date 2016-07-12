package roof.hoohook.jni;

/**
 * Created by wuyongbo on 16-7-12.
 */
public class HookInterface {

    static {
        System.loadLibrary("hoohook");
    }

    public native int addNative(int add1, int add2);
    public native int subNative(int sub1, int sub2);
}
