package com.imarkusi.design_support_library_example;

import android.app.Application;

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
    }
}
