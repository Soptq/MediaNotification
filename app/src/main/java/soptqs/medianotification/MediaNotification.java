package soptqs.medianotification;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import james.colorpickerdialog.ColorPicker;
import soptqs.medianotification.fragments.SettingsFragment;
import soptqs.medianotification.utils.PreferenceUtils;

public class MediaNotification extends ColorPicker {

    private List<TutorialListener> listeners;
    private static Context context;


    @Override
    public void onCreate() {
        super.onCreate();
        listeners = new ArrayList<>();
        context = getApplicationContext();
    }


    public void showTutorial() {
        PreferenceManager.getDefaultSharedPreferences(this)
                .edit()
                .putBoolean(PreferenceUtils.PREF_TUTORIAL, true)
                .putBoolean(PreferenceUtils.PREF_TUTORIAL_PLAYERS, true)
                .putBoolean(PreferenceUtils.PREF_SHELL, true)
                .apply();


        for (TutorialListener listener : listeners) {
            listener.onTutorial();
        }

    }

    public void addListener(TutorialListener listener) {
        listeners.add(listener);
    }

    public void removeListener(TutorialListener listener) {
        listeners.remove(listener);
    }

    public interface TutorialListener {
        void onTutorial();
    }

    public static Context getContext() {
        return context;
    }

}
