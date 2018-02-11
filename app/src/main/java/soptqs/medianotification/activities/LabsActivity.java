package soptqs.medianotification.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.widget.CompoundButton;

import com.tencent.bugly.crashreport.CrashReport;

import soptqs.medianotification.R;
import soptqs.medianotification.services.NotificationService;
import soptqs.medianotification.utils.PreferenceUtils;

import static soptqs.medianotification.MediaNotification.getContext;

/**
 * Created by S0ptq on 2018/1/6.
 */

public class LabsActivity extends AppCompatActivity {

    private SwitchCompat notificationStyle2;
    private SwitchCompat enableBlur;
    private SwitchCompat enableRenderScript;

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labs);

        CrashReport.initCrashReport(getApplicationContext(), "55bd533027", true);

        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());

        notificationStyle2 = (SwitchCompat)findViewById(R.id.notificationStyle2);
        enableBlur = (SwitchCompat)findViewById(R.id.enable_blur);
        enableRenderScript = (SwitchCompat)findViewById(R.id.renderScript_Blur);

        notificationStyle2.setChecked(prefs.getBoolean(PreferenceUtils.PREF_NOTIFICATION_STYLE2, false));
        notificationStyle2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                prefs.edit().putBoolean(PreferenceUtils.PREF_NOTIFICATION_STYLE2, isChecked).apply();
                if (!isChecked && enableBlur.isChecked()) enableBlur.setChecked(false);
                updateNotification();
            }
        });

        enableBlur.setChecked(prefs.getBoolean(PreferenceUtils.PREF_ENABLE_BLUR, false));
        enableBlur.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                prefs.edit().putBoolean(PreferenceUtils.PREF_ENABLE_BLUR, isChecked).apply();
                updateNotification();
            }
        });

        enableRenderScript.setChecked(prefs.getBoolean(PreferenceUtils.PREF_ENABLE_RENDERSCRIPT, false));
        enableRenderScript.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                prefs.edit().putBoolean(PreferenceUtils.PREF_ENABLE_RENDERSCRIPT, isChecked).apply();
                updateNotification();
            }
        });
    }

    private void updateNotification() {
        if (NotificationService.isRunning(getContext())) {
            Intent intent = new Intent(getContext(), NotificationService.class);
            intent.setAction(NotificationService.ACTION_UPDATE);
            getContext().startService(intent);
        }
    }
}
