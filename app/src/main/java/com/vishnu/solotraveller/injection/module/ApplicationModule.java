package com.vishnu.solotraveller.injection.module;

import android.app.Application;
import android.content.Context;

import com.vishnu.solotraveller.data.remote.IxigoService;
import com.vishnu.solotraveller.injection.ApplicationContext;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Provide application-level dependencies.
 */
@Module
public class ApplicationModule {
    protected final Application mApplication;

    public ApplicationModule(Application application) {
        mApplication = application;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    @Singleton
    IxigoService provideRibotsService() {
        return IxigoService.Creator.newRibotsService();
    }

}
