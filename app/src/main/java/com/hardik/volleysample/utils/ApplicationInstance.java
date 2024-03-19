package com.hardik.volleysample.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ApplicationInstance extends Application implements Application.ActivityLifecycleCallbacks {
    private static String TAG = ApplicationInstance.class.getSimpleName();
    private static ApplicationInstance instance;
    private static Context context;

//    private static MySharedPreferences mySharedPreferences;
//    private static volatile AppDatabase database;

    // Singleton method to access the application context
    public static synchronized ApplicationInstance getInstance() {
        Log.e(TAG, "getInstance: ");
        return instance;
    }
    public static Context getAppContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        instance = this;
        context = getApplicationContext();
//        database = getDatabase();
        registerActivityLifecycleCallbacks(this);
        // Perform any initialization tasks here.
        // This method is called when your application starts.
//        mySharedPreferences = MySharedPreferences.getInstance(this);
//        mySharedPreferences.setLastVisitTimestamp(0);
    }

    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle bundle) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {

    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {

    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
//        mySharedPreferences = MySharedPreferences.getInstance(this);
//        mySharedPreferences.setLastVisitTimestamp(System.currentTimeMillis());
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle bundle) {

    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
    }

    /// Singleton method to access the database
/*    public static AppDatabase getDatabase() {
        Log.d(TAG, "getDatabase: ");
        if (database == null) {
            synchronized (MyApplication.class) {
                if (database == null) {
     /// Create a Room database instance if it doesn't exist
                    database = Room.databaseBuilder(instance.getApplicationContext(), AppDatabase.class, "ENGLISH_LEARN_DB")
                            .build();
                }
            }
        }
        return database;
    }*/
}
