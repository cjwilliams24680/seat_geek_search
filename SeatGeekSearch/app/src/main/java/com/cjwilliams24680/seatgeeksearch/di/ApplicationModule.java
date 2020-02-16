package com.cjwilliams24680.seatgeeksearch.di;

import android.app.Application;
import android.content.Context;

import com.cjwilliams24680.seatgeeksearch.data.UserPreferences;
import com.cjwilliams24680.seatgeeksearch.network.NetworkManager;
import com.cjwilliams24680.seatgeeksearch.network.SeatGeekApi;

import java.lang.ref.WeakReference;

import javax.inject.Singleton;

import androidx.lifecycle.ViewModelProvider;
import dagger.Module;
import dagger.Provides;

/**
 * Created by chris on 4/12/18.
 *
 * Injects items which live at the application level
 */

@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    Context providesContext() {
        return application;
    }

    @Provides
    SeatGeekApi providesSeatGeekApi(NetworkManager networkManager) {
        return networkManager.getSeatGeekApi();
    }

    @Provides @Singleton
    UserPreferences providesUserPreferences() {
        return new UserPreferences(application);
    }

}
