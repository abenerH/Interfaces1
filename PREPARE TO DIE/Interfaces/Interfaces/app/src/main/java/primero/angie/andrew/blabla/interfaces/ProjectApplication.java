package primero.angie.andrew.blabla.interfaces;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

public class ProjectApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}