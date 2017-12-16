package soptqs.medianotification.activities;

import android.os.Bundle;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

/**
 * Created by S0ptq on 2017/12/14.
 */

public class MainIntroActivity extends IntroActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                .title(soptqs.medianotification.R.string.top_3)
                .description(soptqs.medianotification.R.string.fin_decp)
                .image(soptqs.medianotification.R.drawable.ic_music)
                .background(soptqs.medianotification.R.color.colorBackground)
                .backgroundDark(soptqs.medianotification.R.color.colorBackground)
                .scrollable(false)
                .build());
    }
}
