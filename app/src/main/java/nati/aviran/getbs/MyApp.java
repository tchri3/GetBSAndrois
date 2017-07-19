package nati.aviran.getbs;

import android.app.Application;
import android.content.Context;

/**
 * Created by אבירן on 16/07/2017.
 */

public class MyApp extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }


    public static Context getMyContext() {
        return context;
    }
}
