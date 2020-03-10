package tech.ankainn.edanapplication;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import tech.ankainn.edanapplication.repositories.UserRepository;
import tech.ankainn.edanapplication.retrofit.ApiService;
import tech.ankainn.edanapplication.retrofit.RetrofitUtil;
import tech.ankainn.edanapplication.util.CrashReportingTree;
import timber.log.Timber;

public class BaseApp extends Application {

    private AppExecutors appExecutors;

    private ApiService apiService;

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(BuildConfig.DEBUG ? new Timber.DebugTree() : new CrashReportingTree());

        appExecutors = new AppExecutors();

        apiService = RetrofitUtil.createApiService();

        registerActivities();
    }

    private void registerActivities() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {

            }
        });
    }

    // public methods
    public UserRepository getUserRepository() {
        return UserRepository.getInstance(appExecutors, apiService);
    }
}