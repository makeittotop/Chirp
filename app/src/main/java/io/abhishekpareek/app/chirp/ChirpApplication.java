package io.abhishekpareek.app.chirp;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by apareek on 1/26/16.
 */
public class ChirpApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Parse
        Parse.enableLocalDatastore(this);
        Parse.initialize(this);
    }
}
