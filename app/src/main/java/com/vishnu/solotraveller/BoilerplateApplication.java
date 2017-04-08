package com.vishnu.solotraveller;

import android.app.Application;

import com.vishnu.solotraveller.injection.component.ApplicationComponent;
import com.vishnu.solotraveller.injection.component.DaggerApplicationComponent;
import com.vishnu.solotraveller.injection.module.ApplicationModule;

import timber.log.Timber;

public class BoilerplateApplication extends Application  {

    ApplicationComponent mApplicationComponent;

    public static BoilerplateApplication boilerplateApplication;

    @Override
    public void onCreate() {
        super.onCreate();
boilerplateApplication =this;
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
            //Fabric.with(this, new Crashlytics());
        }
    }

    public static BoilerplateApplication get() {
        return boilerplateApplication;
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
