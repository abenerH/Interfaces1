package primero.angie.andrew.blabla.interfaces;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.tumblr.remember.Remember;

public class ProjectApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        Remember.init(getApplicationContext(), "primero.angie.andrew.blabla.interfaces");
    }
}
