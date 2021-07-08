package com.example.abc.dailycaloriecounter.core;

import android.app.Application;
import android.os.Environment;
import android.support.v7.app.AppCompatDelegate;
import com.example.abc.dailycaloriecounter.utils.VideoRequestHandler;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import java.io.File;

/**
 * Created by Bashir on 20-Jul-16.
 */

public class DailyCalorieCounterApplication extends Application {
    private static DailyCalorieCounterApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        ActivityLifecycle.init(this);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        instance = this;
        setupPicasso();
//        DINNextLTProLight=Typeface.createFromAsset(getAssets(), "fonts/DINNextLTPro-Light.otf");
//        DINNextLTProMedium= Typeface.createFromAsset(getAssets(), "fonts/DINNextLTPro-Medium.otf");
//        DINNextLTProRegular=Typeface.createFromAsset(getAssets(), "fonts/DINNextLTPro-Regular.otf");
//        DINNextLTProUltraLight=Typeface.createFromAsset(getAssets(), "fonts/DINNextLTPro-UltraLight.otf");
//
//        DINNextRoundedLTProLight=Typeface.createFromAsset(getAssets(), "fonts/DINNextRoundedLTPro-Light.otf");
//        DINNextRoundedLTProMedium=Typeface.createFromAsset(getAssets(), "fonts/DINNextRoundedLTPro-Medium.otf");
//        DINNextRoundedLTProRegular=Typeface.createFromAsset(getAssets(), "fonts/DINNextRoundedLTPro-Regular.otf");
    }

    public static synchronized DailyCalorieCounterApplication getInstance() {
        return instance;
    }


    private void setupPicasso() {
        File customCacheDirectory = new File(Environment.getExternalStorageDirectory().getAbsoluteFile() + "/.StarNova");
        if (!customCacheDirectory.exists()) {
            customCacheDirectory.mkdirs();
        }
        okhttp3.Cache cache = new okhttp3.Cache(/*getCacheDir()*/customCacheDirectory, Integer.MAX_VALUE);
        okhttp3.OkHttpClient okHttpClient = new okhttp3.OkHttpClient.Builder()
                .cache(cache).build();
        OkHttp3Downloader downloader = new OkHttp3Downloader(okHttpClient);
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(downloader);
        builder.addRequestHandler(new VideoRequestHandler());
        Picasso built = builder.build();
    /*    if (BuildConfig.DEBUG) {
            built.setIndicatorsEnabled(true);
            built.setLoggingEnabled(true);
        }*/

        Picasso.setSingletonInstance(built);
    }

    public void clearApplicationData() {
        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    deleteDir(new File(appDir, s));
                }
            }
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }
}
