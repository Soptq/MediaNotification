package soptqs.medianotification.utils;

import android.util.JsonReader;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import soptqs.medianotification.MediaNotification;
import soptqs.medianotification.R;

/**
 * Created by S0ptq on 2017/12/24.
 */

public class ChecksModule {

    private OutputStream os;

    public static class checksMod extends Thread{

        boolean checks = false;


        @Override
        public void run() {

            String modname = "MediaNotification4Magisk" + MediaNotification.getContext().getResources().getString(R.string.app_version) + ".zip";
            try {

                HttpURLConnection request = (HttpURLConnection) new URL("https://api.github.com/repos/Soptq/MediaNotification/git/trees/Coolapk").openConnection();
                request.connect();

                JsonReader reader = new JsonReader(new InputStreamReader((InputStream) request.getContent()));
                reader.setLenient(true);
                reader.beginArray();
                reader.skipValue();
                while (reader.hasNext()) {
                    reader.beginObject();
                    String name;
                    while (reader.hasNext()) {
                        switch (reader.nextName()) {
                            case "path":
                                name = reader.nextString();
                                if (name == modname) {
                                    checks = true;
                                    Log.d("checksmodule", "run: "+checks);
                                }
                                break;
                            default:
                                reader.skipValue();
                        }
                    }
                    reader.endObject();
                }
                reader.endArray();
            } catch (Exception ignored) {
                Log.e("ChecksModule", "ChecksMod: "+ignored );
            }

        }


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

    public static boolean findBinary(String binaryName) {
        boolean found = false;
        if (!found) {
            String[] places = { "/sbin/", "/system/bin/", "/system/xbin/",
                    "/data/local/xbin/", "/data/local/bin/",
                    "/system/sd/xbin/", "/system/bin/failsafe/", "/data/local/" };
            for (String where : places) {
                if (new File(where + binaryName).exists()) {
                    found = true;

                    break;
                }
            }
        }
        return found;
    }

}
