package soptqs.medianotification.activities;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import soptqs.medianotification.MediaNotification;
import soptqs.medianotification.adapters.SimplePagerAdapter;
import soptqs.medianotification.dialogs.AboutDialog;
import soptqs.medianotification.fragments.AppsFragment;
import soptqs.medianotification.fragments.HelpFragment;
import soptqs.medianotification.fragments.SettingsFragment;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private boolean FirstStart = false;
    private static final String START_KEY = "isFirst";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(soptqs.medianotification.R.layout.activity_main);
        toolbar = findViewById(soptqs.medianotification.R.id.toolbar);
        tabLayout = findViewById(soptqs.medianotification.R.id.tabLayout);
        viewPager = findViewById(soptqs.medianotification.R.id.viewPager);

        setSupportActionBar(toolbar);

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(new SimplePagerAdapter(this, viewPager, getSupportFragmentManager(), new SettingsFragment(), new AppsFragment(), new HelpFragment()));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                findViewById(soptqs.medianotification.R.id.icon).setVisibility(View.GONE);
                intro();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ValueAnimator animator = ValueAnimator.ofObject(new ArgbEvaluator(), getWindow().getStatusBarColor(), ContextCompat.getColor(MainActivity.this, soptqs.medianotification.R.color.colorPrimaryDark));
                    animator.setInterpolator(new AccelerateInterpolator());
                    animator.setDuration(500);
                    animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator valueAnimator) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                                getWindow().setStatusBarColor((int) valueAnimator.getAnimatedValue());
                        }
                    });
                    animator.start();
                }
            }
        }, 3000);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(soptqs.medianotification.R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case soptqs.medianotification.R.id.action_github:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Soptq/MediaNotification/tree/Coolapk")));
                break;
            case soptqs.medianotification.R.id.action_tutorial:
                ((MediaNotification) getApplicationContext()).showTutorial();
                break;
            case soptqs.medianotification.R.id.action_changelog:
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(soptqs.medianotification.R.string.changelog);
                builder.setMessage(soptqs.medianotification.R.string.changelogcontent);
                builder.create().show();
                break;
            case soptqs.medianotification.R.id.action_about:
                new AboutDialog(this).show();
                break;
            case soptqs.medianotification.R.id.action_intro:
                startActivity(new Intent(MainActivity.this,MainIntroActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void intro() {
        SharedPreferences preferences = getPreferences(MODE_PRIVATE);
        FirstStart = preferences.getBoolean(START_KEY, true);
        if (FirstStart == true) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(START_KEY, false);
            editor.commit();
            startActivity(new Intent(MainActivity.this,MainIntroActivity.class));
        }
    }
}