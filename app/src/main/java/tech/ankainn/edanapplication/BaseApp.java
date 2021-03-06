package tech.ankainn.edanapplication;

import android.app.Application;

import tech.ankainn.edanapplication.db.EdanDatabase;
import tech.ankainn.edanapplication.util.CrashReportingTree;
import timber.log.Timber;

public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(BuildConfig.DEBUG ? new Timber.DebugTree() : new CrashReportingTree());

        EdanDatabase.getInstance(this);
    }
}