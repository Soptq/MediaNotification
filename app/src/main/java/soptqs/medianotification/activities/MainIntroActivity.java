package soptqs.medianotification.activities;

import android.os.Bundle;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;
import com.tencent.bugly.crashreport.CrashReport;

import soptqs.medianotification.R;

/**
 * Created by S0ptq on 2017/12/14.
 */

public class MainIntroActivity extends IntroActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrashReport.initCrashReport(getApplicationContext(), "55bd533027", true);
        setButtonBackVisible(true);
        setButtonNextVisible(true);

        addSlide(new SimpleSlide.Builder()
                .title(soptqs.medianotification.R.string.app_name)
                .description(soptqs.medianotification.R.string.desc_tutorial)
                .image(soptqs.medianotification.R.mipmap.ic_launcher)
                .background(soptqs.medianotification.R.color.colorBackground)
                .backgroundDark(soptqs.medianotification.R.color.colorBackground)
                .scrollable(false)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title(soptqs.medianotification.R.string.top_2)
                .description(soptqs.medianotification.R.string.noti_decp)
                .image(soptqs.medianotification.R.drawable.ic_introtwo)
                .background(soptqs.medianotification.R.color.colorPrimary)
                .backgroundDark(soptqs.medianotification.R.color.colorPrimary)
                .scrollable(false)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title(soptqs.medianotification.R.string.top_4)
                .description(soptqs.medianotification.R.string.noti_decp_2)
                .image(R.drawable.autofetch)
                .background(R.color.intro1)
                .backgroundDark(R.color.intro1)
                .scrollable(false)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title(soptqs.medianotification.R.string.top_5)
                .description(soptqs.medianotification.R.string.noti_decp_3)
                .image(R.drawable.ic_warning_black_24dp)
                .background(R.color.intro2)
                .backgroundDark(R.color.intro2)
                .scrollable(false)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title(soptqs.medianotification.R.string.top_3)
                .description(soptqs.medianotification.R.string.fin_decp)
                .image(R.drawable.ic_music_note_black_24dp)
                .background(soptqs.medianotification.R.color.colorBackground)
                .backgroundDark(soptqs.medianotification.R.color.colorBackground)
                .scrollable(false)
                .build());
    }
}
