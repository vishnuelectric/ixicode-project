package com.vishnu.solotraveller.injection.component;

import com.vishnu.solotraveller.injection.PerActivity;
import com.vishnu.solotraveller.injection.module.ActivityModule;
import com.vishnu.solotraveller.ui.main.MainActivity;

import dagger.Subcomponent;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

}
