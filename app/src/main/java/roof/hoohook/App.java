package roof.hoohook;

import android.app.Application;
import android.content.Context;

import roof.hoohook.hook.HookManager;
import roof.hoohook.hook.HookedAPI;

/**
 * Created by wuyongbo on 16-7-13.
 */
public class App extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        HookManager.getDefault().applyHooks(HookedAPI.class);
    }
}
