package james.medianotification.data;

import android.app.PendingIntent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import java.util.Locale;

import james.medianotification.utils.PreferenceUtils;

public class PlayerData {

    public String name;
    public String packageName;
    public PendingIntent previousIntent;
    public PendingIntent playPauseIntent;
    public PendingIntent nextIntent;
    public String[] actions;
    public int persistence = -1;
    public boolean clearData;
    public boolean reversePlayPause;

    public PlayerData(String name, String packageName, String... actions) {
        this.name = name;
        this.packageName = packageName;
        this.actions = actions;
    }

    public PlayerData(String name, String packageName, PendingIntent previousIntent, PendingIntent playPauseIntent, PendingIntent nextIntent, String... actions) {
        this.name = name;
        this.packageName = packageName;
        this.previousIntent = previousIntent;
        this.playPauseIntent = playPauseIntent;
        this.nextIntent = nextIntent;
        this.actions = actions;
    }

    public PlayerData setPersistence(int persistence) {
        this.persistence = persistence;
        return this;
    }

    public PlayerData setClearData(boolean clearData) {
        this.clearData = clearData;
        return this;
    }

    public PlayerData setReversePlayPause(boolean reversePlayPause) {
        this.reversePlayPause = reversePlayPause;
        return this;
    }

    public boolean hasAction(String action) {
        for (String string : actions) {
            if (string.equals(action))
                return true;
        }

        return false;
    }

    public boolean isInstalled(PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packageName, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }

        return true;
    }

    public boolean isEnabled(SharedPreferences prefs) {
        return prefs.getBoolean(String.format(Locale.getDefault(), PreferenceUtils.PREF_PLAYER_ENABLED, packageName), true);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof PlayerData && ((PlayerData) obj).packageName != null && packageName != null && ((PlayerData) obj).packageName.equals(packageName);
    }
}
