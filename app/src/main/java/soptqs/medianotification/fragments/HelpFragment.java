package soptqs.medianotification.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

import moe.feng.alipay.zerosdk.AlipayZeroSdk;
import soptqs.medianotification.MediaNotification;
import soptqs.medianotification.R;
import soptqs.medianotification.utils.MarkdownUtils;
import ru.noties.markwon.Markwon;

public class HelpFragment extends BaseFragment {

    private ProgressBar progressBar;
    private TextView textView;
    private TextView textNoInternet;
    private ImageView internetError;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help, container, false);
        progressBar = view.findViewById(R.id.progressBar);
        textView = view.findViewById(R.id.textView);
        internetError = view.findViewById(R.id.internet_error);
        textNoInternet = view.findViewById(R.id.textNoInternet);

        internetError.setVisibility(View.INVISIBLE);
        textNoInternet.setVisibility(View.INVISIBLE);

        new ReadmeThread(this).start();

        return view;
    }

    @Override
    public String getTitle(Context context) {
        return context.getString(R.string.title_help);
    }

    private static class ReadmeThread extends Thread {

        private WeakReference<HelpFragment> fragmentReference;

        public ReadmeThread(HelpFragment fragment) {
            fragmentReference = new WeakReference<>(fragment);
        }

        @Override
        public void run() {
            final String readmeurl = MediaNotification.getContext().getResources().getString(R.string.readme);
            final String text;
            final String urlen = readmeurl;
            try {
                URL url = new URL(urlen);
                HttpURLConnection request = (HttpURLConnection) url.openConnection();
                request.connect();

                BufferedReader r = new BufferedReader(new InputStreamReader((InputStream) request.getContent()));
                StringBuilder total = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    total.append(line).append('\n');
                }

                text = MarkdownUtils.toHtml(total.toString());
            } catch (final Exception e) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        final HelpFragment fragment = fragmentReference.get();
                        if (fragment != null && fragment.textView != null && fragment.progressBar != null) {
                            fragment.textView.setVisibility(View.GONE);
                            fragment.progressBar.setVisibility(View.GONE);
                            fragment.internetError.setVisibility(View.VISIBLE);
                            fragment.textNoInternet.setVisibility(View.VISIBLE);
                        }
                    }
                });
                return;
            }

            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    final HelpFragment fragment = fragmentReference.get();
                    if (fragment != null && fragment.textView != null && fragment.progressBar != null) {
                        Markwon.setMarkdown(fragment.textView, text);
                        fragment.progressBar.setVisibility(View.GONE);
                        fragment.internetError.setVisibility(View.GONE);
                        fragment.textNoInternet.setVisibility(View.GONE);
                    }
                }
            });
        }
    }



}
