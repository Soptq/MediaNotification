package soptqs.medianotification.activities;

import android.os.Bundle;

import com.heinrichreimersoftware.androidissuereporter.IssueReporterActivity;
import com.heinrichreimersoftware.androidissuereporter.model.github.ExtraInfo;
import com.heinrichreimersoftware.androidissuereporter.model.github.GithubTarget;

import soptqs.medianotification.R;

/**
 * Created by S0ptq on 2017/12/17.
 */

public class Issuereporter extends IssueReporterActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setMinimumDescriptionLength(10);
        setGuestToken("28f479f73db97d912611b27579aad7a76ad2baf5");
    }

    @Override
    public GithubTarget getTarget() {
        return new GithubTarget("Soptq", "Medianotification_bug");
    }

    @Override
    public void onSaveExtraInfo(ExtraInfo extraInfo) {
        extraInfo.put("Info 1", "logcat");
        extraInfo.put("Info 2", true);
    }

}

