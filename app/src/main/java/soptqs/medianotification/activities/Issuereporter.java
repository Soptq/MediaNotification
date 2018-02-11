package soptqs.medianotification.activities;

import android.os.Bundle;

import com.heinrichreimersoftware.androidissuereporter.IssueReporterActivity;
import com.heinrichreimersoftware.androidissuereporter.model.github.ExtraInfo;
import com.heinrichreimersoftware.androidissuereporter.model.github.GithubTarget;
import com.tencent.bugly.crashreport.CrashReport;

import soptqs.medianotification.R;

/**
 * Created by S0ptq on 2017/12/17.
 */

public class Issuereporter extends IssueReporterActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrashReport.initCrashReport(getApplicationContext(), "55bd533027", true);
        setContentView(R.layout.air_activity_issue_reporter);
        setMinimumDescriptionLength(10);
        setGuestToken("d2bee92043ecb15c55abae5432db8ed955858d09");
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

