package pl.tamides.mi9secpuconfigurator;

import android.app.Application;
import android.content.Context;

public class App extends Application {

    private static Context appContext;
    private static BaseActivity currentActivity;

    public static Context getAppContext() {
        return appContext;
    }

    public static void setAppContext(Context appContext) {
        App.appContext = appContext;
    }

    public static BaseActivity getCurrentActivity() {
        return currentActivity;
    }

    public static void setCurrentActivity(BaseActivity activity) {
        currentActivity = activity;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
    }
}
