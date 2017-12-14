package james.medianotification.activities;

import android.os.Bundle;

import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.SimpleSlide;

import james.medianotification.R;

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
                .title(R.string.app_name)
                .description(R.string.desc_tutorial)
                .image(R.mipmap.ic_launcher)
                .background(R.color.colorBackground)
                .backgroundDark(R.color.colorBackground)
                .scrollable(false)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title(R.string.top_2)
                .description(R.string.noti_decp)
                .image(R.drawable.ic_introtwo)
                .background(R.color.colorPrimary)
                .backgroundDark(R.color.colorPrimary)
                .scrollable(false)
                .build());

        addSlide(new SimpleSlide.Builder()
                .title(R.string.top_3)
                .description(R.string.fin_decp)
                .image(R.drawable.ic_music)
                .background(R.color.colorBackground)
                .backgroundDark(R.color.colorBackground)
                .scrollable(false)
                .build());
    }
}
