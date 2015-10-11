package com.imarkusi.design_support_library_example;

import com.imarkusi.design_support_library_example.custom.SimpleActivityLifecycleCallbacks;

import android.app.Activity;
import android.app.Application;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

/**
 * Created on 09/10/15.
 *
 * @author markusi
 */
public class App extends Application {

    private static App instance;

    public static App getInstance() {
        return instance;
    }

    public static void setInstance(App instance) {
        App.instance = instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setInstance(this);
        lockInPortraitMode();
    }

    private void lockInPortraitMode() {
        registerActivityLifecycleCallbacks(new SimpleActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
            }
        });
    }
}
