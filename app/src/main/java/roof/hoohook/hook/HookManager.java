package roof.hoohook.hook;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import roof.hoohook.hook.annotation.Hook;
import roof.hoohook.jni.HookInterface;

/**
 * Created by wuyongbo on 16-7-12.
 */
public class HookManager {

    final static String TAG = HookManager.class.getSimpleName();

    /** Singleton */
    private static final HookManager sDefault = new HookManager();

    HookInterface hookInterface = new HookInterface();
    Context context;

    /**
     * [Key] = MethodName
     * [Value] = BackupMethods
     */
    private final Map<String, Map<String,List<Method>>> classToBackupMethodsMapping = new ConcurrentHashMap<String, Map<String, List<Method>>>();

    public static HookManager getDefault() {
        return sDefault;
    }

    public void applyHooks(Class<?> hookClass) {

        HookInterface.setup();

        for (Method hookMethod : hookClass.getDeclaredMethods()) {
            Hook hook = hookMethod.getAnnotation(Hook.class);
            if (hook != null) {
                /** Parse Hooked API statement */
                String statement = hook.value();
                String[] splitValues = statement.split("::");
                if (splitValues.length == 2) {
                    String className = splitValues[0];
                    String[] methodNameWithSignature = splitValues[1].split("@");
                    if (methodNameWithSignature.length <= 2) {
                        String methodName = methodNameWithSignature[0];
                        String signature = methodNameWithSignature.length == 2 ? methodNameWithSignature[1] : "";
                        String[] paramList = signature.split("#");
                        if (paramList[0].equals("")) {
                            paramList = new String[0];
                        }
                        /** find the right API to be hooked */
                        try {
                            Class<?> clazz = Class.forName(className);
                            for (Method method : clazz.getDeclaredMethods()) {
                                if (method.getName().equals(methodName)) {
                                    Class<?>[] types = method.getParameterTypes();
                                    if (paramList.length == types.length) {
                                        boolean isMatch = true;
                                        for (int N = 0; N < types.length; N++) {
                                            if (!types[N].getName().equals(paramList[N])) {
                                                isMatch = false;
                                                break;
                                            }
                                        }
                                        if (isMatch) {
                                            boolean success = hookMethod(method, hookMethod);
                                            if (success) {
                                                Log.d(TAG, "[+++] " + method.getName() + " be hooked successfully.");
                                            } else {
                                                Log.e(TAG, "[---] Cannot resolve Method " + Arrays.toString(methodNameWithSignature));
                                            }
                                            break;
                                        }
                                    }
                                }
                            }
                        } catch (Throwable e) {
                            Log.e(TAG, "[---] Error to Load Hook Method " + hookMethod.getName() + " in class " + className);
                            e.printStackTrace();
                        }
                    }else {
                        Log.e(TAG, "[---] Can't split method and signature " + Arrays.toString(methodNameWithSignature));
                    }
                }else {
                    Log.e(TAG, "[---] Can't understand your statement " + statement);
                }
            }
        }
    }

    private boolean hookMethod(Method src, Method dest) {

        final String vmVersion = System.getProperty("java.vm.version");
        boolean isArt = vmVersion != null && vmVersion.startsWith("2");

        if (isArt) {
            hookMethodArt(src, dest);
        } else {
            hookMethodDalvik(src, dest);
        }

        return false;
    }

    //FIXME
    //use method name and class name
    private boolean hookMethodArt(Method src, Method dest) {

        return hookInterface.hookArtMethod(src, dest);
    }

    private boolean hookMethodDalvik(Method src, Method dest) {

        return hookInterface.hookDalvikMethod(src, dest);
    }
}
