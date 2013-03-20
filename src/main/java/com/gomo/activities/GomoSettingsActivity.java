package com.gomo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.gomo.utilities.SharedPreferencesManager;

public abstract class GomoSettingsActivity extends GomoActivity {

    protected SharedPreferencesManager sharedPreferencesManager;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    public void backBtnClick(View v) {

        navigateToMonitorActivity();
    }

    public void saveAndNavigateBackToMonitor(View v) {

        if (saveSharedPreferencesFromInputs())
            navigateToMonitorActivity();
    }

    protected void navigateToMonitorActivity() {

        Intent homeActivity = new Intent(this, MonitorActivity.class);
        startActivity(homeActivity);
        finish();
    }

    abstract protected void setupFields();

    abstract protected void setCurrentSharedPreferencesOnInputs();

    abstract protected boolean saveSharedPreferencesFromInputs();
}