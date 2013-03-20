package com.gomo.activities;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.gomo.R;
import com.gomo.models.Jenkins;
import com.gomo.tasks.DisplayJenkinsTask;
import com.gomo.utilities.MenuHandler;
import com.gomo.utilities.SharedPreferencesManager;
import com.gomo.views.MonitorView;

import java.util.List;
import java.util.Set;

public class MonitorActivity extends GomoActivity {

    private static final int DEFAULT_REFRESH_RATE_MILLISECONDS = 60000;

    private String jenkinsUrlKey, refreshRateKey;

    private FrameLayout layoutWrapper;
    private RelativeLayout progressBarLayout;

    private Handler handler;
    private SharedPreferencesManager sharedPreferencesManager;
    private Set<String> jenkinsUrls;
    private int refreshRateMillis;
    private DisplayJenkinsTask displayJenkinsTask;
    private Runnable buildMonitorRefresh = new Runnable() {

        @Override
        public void run() {

            if (displayJenkinsTask.getStatus() != AsyncTask.Status.RUNNING)
                displayBuildMonitor();

            handler.postDelayed(buildMonitorRefresh, refreshRateMillis);
        }
    };

    public Set<String> getJenkinsUrls() {

        return jenkinsUrls;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.monitor);

        setupFields();

        final boolean hasNoJenkins = setupMonitorPreferences();
        if (hasNoJenkins) return;
        startRepeatingBuildingMonitorRefresh();
    }

    private void setupFields() {

        handler = new Handler();
        displayJenkinsTask = new DisplayJenkinsTask();

        jenkinsUrlKey = getString(R.string.monitor_jenkins_url_key);
        refreshRateKey = getString(R.string.monitor_refresh_rate_key);

        layoutWrapper = (FrameLayout) findViewById(R.id.layoutWrapper);
        progressBarLayout = (RelativeLayout) findViewById(R.id.progressBarLayout);
    }

    private boolean setupMonitorPreferences() {

        sharedPreferencesManager = new SharedPreferencesManager(this);

        jenkinsUrls = sharedPreferencesManager.loadStringSet(jenkinsUrlKey, null);

       if (jenkinsUrls == null || jenkinsUrls.isEmpty()) {

           MenuHandler.navigateToJenkinsSettings(this);
           return true;
       }

        refreshRateMillis = sharedPreferencesManager.loadInt(refreshRateKey, 0);

        setupDefaultRefreshRate();

        return false;
    }

    private void setupDefaultRefreshRate() {

        if (refreshRateMillis == 0) {

            sharedPreferencesManager.saveInt(refreshRateKey, DEFAULT_REFRESH_RATE_MILLISECONDS);
            refreshRateMillis = DEFAULT_REFRESH_RATE_MILLISECONDS;

        }
    }

    private void startRepeatingBuildingMonitorRefresh() {

        buildMonitorRefresh.run();
    }

    private void displayBuildMonitor() {

        if (displayJenkinsTask.getStatus() == AsyncTask.Status.FINISHED)
            displayJenkinsTask = new DisplayJenkinsTask();

        displayJenkinsTask.execute(this);
    }

    public void displayBuildMonitor(List<Jenkins> jenkinsList) {

        if (jenkinsList != null) {

            MonitorView view = new MonitorView(this, jenkinsList);
            view.displayMonitor();
        }
    }

    public void hideProgressBar() {

        layoutWrapper.removeView(progressBarLayout);
    }
}