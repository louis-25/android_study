package me.onulhalin.sample;

import android.app.Application;

import me.onulhalin.fragmentation.Fragmentation;
import me.onulhalin.fragmentation.helper.ExceptionHandler;

/**
 *
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Fragmentation.builder()

                .stackViewMode(Fragmentation.BUBBLE)
                .debug(false) // 实际场景建议.debug(BuildConfig.DEBUG)

                .handleException(new ExceptionHandler() {
                    @Override
                    public void onException(Exception e) {

                    }
                })
                .install();
    }
}
