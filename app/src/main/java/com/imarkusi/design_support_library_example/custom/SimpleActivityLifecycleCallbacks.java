package com.imarkusi.design_support_library_example.custom;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * Created on 11/10/15.
 *
 * @author markusi
 */
public abstract class SimpleActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
