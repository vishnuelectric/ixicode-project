package com.vishnu.solotraveller.injection.component;

import android.app.Application;
import android.content.Context;

import com.vishnu.solotraveller.data.DataManager;
import com.vishnu.solotraveller.data.remote.IxigoService;
import com.vishnu.solotraveller.injection.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;



@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {



    @com.vishnu.solotraveller.injection.ApplicationContext
    Context context();
    Application application();
    IxigoService ribotsService();

    DataManager dataManager();
    com.vishnu.solotraveller.util.RxEventBus eventBus();

}
