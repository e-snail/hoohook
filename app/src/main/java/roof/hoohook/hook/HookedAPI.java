package roof.hoohook.hook;

import android.widget.Toast;

import roof.hoohook.hook.annotation.Hook;

/**
 * Created by wuyongbo on 16-7-13.
 */
public class HookedAPI {

    @Hook("android.widget.Toast::show")
    public static void Toast_show(Toast toast) {
        //HookManager.getDefault().callSuper(toast);
    }
}
