package org.projects.shoppinglist;

import android.app.Application;
import android.util.Log;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Alice on 24/11/2016.
 */

public class MyApplication extends Application {
    public void onCreate() {
        super.onCreate();
        if (!FirebaseApp.getApps(this).isEmpty()) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
            Log.d("firebase","persistance enabled");
        }
        else
            Log.d("firebase","persistance not enabled");
    }

}
