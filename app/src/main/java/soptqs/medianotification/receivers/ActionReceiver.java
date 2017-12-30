package soptqs.medianotification.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import java.io.OutputStream;

import soptqs.medianotification.utils.PreferenceUtils;

public class  ActionReceiver extends BroadcastReceiver {

    private OutputStream os;

    public static final String EXTRA_KEYCODE = "james.medianotification.EXTRA_KEYCODE";
    public static final String EXTRA_PACKAGE = "james.medianotification.EXTRA_PACKAGE";

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        int keycode = intent.getIntExtra(EXTRA_KEYCODE, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE);
        String packageName = intent.getStringExtra(EXTRA_PACKAGE);
        Log.e("keycode", "onReceive: "+keycode);

        switch (prefs.getInt(PreferenceUtils.PREF_MEDIA_CONTROLS_METHOD, PreferenceUtils.CONTROLS_METHOD_NONE)) {
            case PreferenceUtils.CONTROLS_METHOD_AUDIO_MANAGER:
                sendKeyPressAudioManager(context, keycode);
                break;
            case PreferenceUtils.CONTROLS_METHOD_REFLECTION:
                sendKeyPressReflection(context, keycode);
                break;
            case PreferenceUtils.CONTROLS_METHOD_BROADCAST:
                sendKeyPressBroadcast(context, keycode, packageName);
                break;
            case PreferenceUtils.CONTROLS_METHOD_BROADCAST_STRING:
                sendKeyPressBroadcastString(context, keycode, packageName);
                break;
            case PreferenceUtils.CONTROLS_METHOD_BROADCAST_PARCELABLE:
                sendKeyPressBroadcastParcelable(context, keycode, packageName);
                break;
            case PreferenceUtils.CONTROLS_METHOD_SHELL_ROOT:
                sendKeyPressShellCommand(keycode);
                break;
        }
    }

    public void sendKeyPressAudioManager(Context context, int keycode) {
        sendKeyPressAudioManager(context, KeyEvent.ACTION_DOWN, keycode);
        sendKeyPressAudioManager(context, KeyEvent.ACTION_UP, keycode);
    }

    public void sendKeyPressAudioManager(Context context, int action, int keycode) {
        long uptime = SystemClock.uptimeMillis();

        AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        audioManager.dispatchMediaKeyEvent(new KeyEvent(uptime, uptime, action, keycode, 0));
    }

    public void sendKeyPressReflection(Context context, int keycode) {
        try {
            sendKeyPressReflection(KeyEvent.ACTION_DOWN, keycode);
            sendKeyPressReflection(KeyEvent.ACTION_UP, keycode);
        } catch (Exception e) {
            Toast.makeText(context, String.format(context.getString(soptqs.medianotification.R.string.msg_reflection_error), e.getMessage()), Toast.LENGTH_SHORT).show();
        }
    }

    public void sendKeyPressReflection(int action, int keycode) throws Exception {
        long uptime = SystemClock.uptimeMillis();

        IBinder iBinder = (IBinder) Class.forName("android.os.ServiceManager")
                .getDeclaredMethod("checkService", String.class)
                .invoke(null, Context.AUDIO_SERVICE);

        Object audioService = Class.forName("android.media.IAudioService$Stub")
                .getDeclaredMethod("asInterface", IBinder.class)
                .invoke(null, iBinder);

        Class.forName("android.media.IAudioService")
                .getDeclaredMethod("dispatchMediaKeyEvent", KeyEvent.class)
                .invoke(audioService, new KeyEvent(uptime, uptime, action, keycode, 0));
    }

    public void sendKeyPressBroadcast(Context context, int keycode, String packageName) {
        Intent intent;
        switch (keycode) {
            case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
                intent = new Intent("com.android.music.musicservicecommand.previous");
                break;
            case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
                intent = new Intent("com.android.music.musicservicecommand.togglepause");
                break;
            case KeyEvent.KEYCODE_MEDIA_PAUSE:
                intent = new Intent("com.android.music.musicservicecommand.pause");
                break;
            case KeyEvent.KEYCODE_MEDIA_PLAY:
                intent = new Intent("com.android.music.musicservicecommand.play");
                break;
            case KeyEvent.KEYCODE_MEDIA_NEXT:
                intent = new Intent("com.android.music.musicservicecommand.next");
                break;
            default:
                return;
        }

        if (packageName != null)
            intent.setPackage(packageName);

        context.sendOrderedBroadcast(intent, null);
    }

    public void sendKeyPressBroadcastString(Context context, int keycode, String packageName) {
        Intent intent = new Intent("com.android.music.musicservicecommand");
        switch (keycode) {
            case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
                intent.putExtra("command", "previous");
                break;
            case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
                intent.putExtra("command", "togglepause");
                break;
            case KeyEvent.KEYCODE_MEDIA_PAUSE:
                intent.putExtra("command", "pause");
                break;
            case KeyEvent.KEYCODE_MEDIA_PLAY:
                intent.putExtra("command", "play");
                break;
            case KeyEvent.KEYCODE_MEDIA_NEXT:
                intent.putExtra("command", "next");
                break;
            case KeyEvent.KEYCODE_MEDIA_STOP:
                intent.putExtra("command", "stop");
                break;
            default:
                return;
        }

        if (packageName != null)
            intent.setPackage(packageName);

        context.sendOrderedBroadcast(intent, null);
    }

    public void sendKeyPressBroadcastParcelable(Context context, int keycode, String packageName) {
        sendKeyPressBroadcastParcelable(context, KeyEvent.ACTION_DOWN, keycode, packageName);
        sendKeyPressBroadcastParcelable(context, KeyEvent.ACTION_UP, keycode, packageName);
    }

    public void sendKeyPressBroadcastParcelable(Context context, int action, int keycode, String packageName) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_BUTTON);
        intent.putExtra(Intent.EXTRA_KEY_EVENT, new KeyEvent(0, 0, action, keycode, 0));
        if (packageName != null)
            intent.setPackage(packageName);

        context.sendOrderedBroadcast(intent, null);
    }

    public void sendKeyPressShellCommand(int keyCode){
        exec("input keyevent " + keyCode + "\n");
    }

    public final void exec(String cmd) {
        try {
            if (os == null) {
                os = Runtime.getRuntime().exec("su").getOutputStream();
            }
            os.write(cmd.getBytes());
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
