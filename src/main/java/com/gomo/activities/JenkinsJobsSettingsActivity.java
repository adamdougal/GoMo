package com.gomo.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.gomo.R;
import com.gomo.models.RespJenkins;
import com.gomo.tasks.GetJenkinsRespTask;
import com.gomo.utilities.AlertDialogManager;
import com.gomo.utilities.MenuHandler;
import com.gomo.utilities.SharedPreferencesManager;

import java.util.LinkedHashSet;
import java.util.Set;

public class JenkinsJobsSettingsActivity extends GomoActivity {

    private static final String JENKINS_URL_SUFFIX = "/";
    private static final String EXPECTED_JENKINS_URL_PREFIX = "http";
    private static final String JENKINS_URL_PREFIX = "http://";

    private ProgressBar progressBar;
    private LinearLayout jenkinsJobList, buttonsLayout;
    private EditText jenkinsUrlInput;
    private Button fetchBtn, topBtn;
    private ScrollView scrollView;

    private String monitorJenkinsUrlKey, jenkinsUrlIntentkey, jenkinsUrl;
    private SharedPreferencesManager sharedPreferencesManager;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.jenkins_job_settings);

        setupFields();
        fetchJenkinsUrlFromIntent();
        setupSharedPreferences();
    }

    private void setupFields() {

        monitorJenkinsUrlKey = getString(R.string.monitor_jenkins_url_key);
        jenkinsUrlIntentkey = getString(R.string.jenkins_url_intent_key);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        jenkinsJobList = (LinearLayout) findViewById(R.id.jenkinsJobList);
        jenkinsUrlInput = (EditText) findViewById(R.id.jenkinsUrl);
        fetchBtn = (Button) findViewById(R.id.fetchBtn);
        buttonsLayout = (LinearLayout) findViewById(R.id.buttonLayout);
        topBtn = (Button) findViewById(R.id.topBtn);
        scrollView = (ScrollView) findViewById(R.id.scrollViewLayout);
    }

    private void fetchJenkinsUrlFromIntent() {

        Intent intent = getIntent();
        jenkinsUrl = intent.getStringExtra(jenkinsUrlIntentkey);

        if (jenkinsUrl != null && !jenkinsUrl.isEmpty()) {

            jenkinsUrlInput.setText(jenkinsUrl);
            fetchBtn.performClick();
        }
    }

    private void setupSharedPreferences() {

        sharedPreferencesManager = new SharedPreferencesManager(this);
    }

    public void fetchClick(View v) {

        jenkinsUrl = jenkinsUrlInput.getText().toString();

        if (jenkinsUrl.isEmpty())
            return;

        setFetchButtonEnabled(false);

        fetchJenkinsJobs();
    }

    public void setFetchButtonEnabled(boolean setEnabled) {

        fetchBtn.setEnabled(setEnabled);
    }

    private void fetchJenkinsJobs() {

        validateJenkinsUrl();
        jenkinsUrlInput.setText(jenkinsUrl);

        jenkinsJobList.removeAllViews();
        new GetJenkinsRespTask(this, jenkinsUrl).execute();
    }

    private void validateJenkinsUrl() {

        validateJenkinsUrlPrefix();
        validateJenkinsUrlSuffix();
    }

    private void validateJenkinsUrlPrefix() {

        if (!jenkinsUrl.startsWith(EXPECTED_JENKINS_URL_PREFIX))
            jenkinsUrl = JENKINS_URL_PREFIX + jenkinsUrl;
    }

    private void validateJenkinsUrlSuffix() {

        if (!jenkinsUrl.endsWith(JENKINS_URL_SUFFIX))
            jenkinsUrl += JENKINS_URL_SUFFIX;
    }

    public void showProgressBar(boolean showProgressBar) {

        if (showProgressBar && !progressBar.isShown()) {

            progressBar.setVisibility(View.VISIBLE);

        } else if (!showProgressBar && progressBar.isShown()) {

            progressBar.setVisibility(View.GONE);
        }
    }

    public void showButtons(boolean showButtons) {

        if (showButtons && !buttonsLayout.isShown()) {

            buttonsLayout.setVisibility(View.VISIBLE);
            topBtn.setVisibility(View.VISIBLE);

        } else if (!showButtons && buttonsLayout.isShown()) {

            buttonsLayout.setVisibility(View.GONE);
            topBtn.setVisibility(View.GONE);
        }
    }

    public void displayJenkinsJobs(RespJenkins respJenkins) {

        setFetchButtonEnabled(true);

        for (RespJenkins.Jobs job : respJenkins.getJobs()) {

            CheckBox jobCheckBox = setupCheckboxForJob(job);
            jenkinsJobList.addView(jobCheckBox);
        }
    }

    private CheckBox setupCheckboxForJob(RespJenkins.Jobs job) {

        CheckBox jobCheckBox = new CheckBox(this);
        jobCheckBox.setText(job.getName());

        String checkboxTag = job.getUrl();
        jobCheckBox.setTag(checkboxTag);

        boolean setChecked = sharedPreferencesManager.loadBoolean(checkboxTag, false);
        jobCheckBox.setChecked(setChecked);

        return jobCheckBox;
    }

    public void selectAllJobs(View v) {

        setAllJobCheckboxesChecked(true);
    }

    public void deselectAllJobs(View v) {

        setAllJobCheckboxesChecked(false);
    }

    private void setAllJobCheckboxesChecked(boolean setChecked) {

        for (int i = 0; i < jenkinsJobList.getChildCount(); i++) {

            CheckBox checkBox = (CheckBox) jenkinsJobList.getChildAt(i);
            checkBox.setChecked(setChecked);
        }
    }

    public void saveClick(View v) {

        saveJenkinUrl();
        saveJenkinsJobs();

        navigateToSettingsActivity();
    }

    private void saveJenkinsJobs() {

        for (int i = 0; i < jenkinsJobList.getChildCount(); i++) {

            CheckBox checkBox = (CheckBox) jenkinsJobList.getChildAt(i);

            String jobUrl = (String) checkBox.getTag();
            boolean showJob = checkBox.isChecked();

            sharedPreferencesManager.saveBoolean(jobUrl, showJob);
        }
    }

    private void saveJenkinUrl() {

        Set<String> jenkinsUrls = sharedPreferencesManager.loadStringSet(monitorJenkinsUrlKey, new LinkedHashSet<String>());

        Set<String> jenkinsUrlsToBeSaved = new LinkedHashSet<String>();

        for (String jenkinsUrl : jenkinsUrls) {

            jenkinsUrlsToBeSaved.add(jenkinsUrl);
        }

        jenkinsUrlsToBeSaved.add(jenkinsUrl);

        sharedPreferencesManager.saveStringSet(monitorJenkinsUrlKey, jenkinsUrlsToBeSaved);
    }

    public void showAlertForException(Exception exception) {

        AlertDialog.OnClickListener onClickListener = new AlertDialog.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                jenkinsUrlInput.requestFocus();
            }
        };

        AlertDialogManager.showAlertDialogForException(exception, this, onClickListener);
    }

    public void backButtonClick(View v) {

        navigateToSettingsActivity();
    }

    public void topClick(View v) {

        scrollView.fullScroll(ScrollView.FOCUS_UP);
    }

    private void navigateToSettingsActivity() {

        Intent jenkinsSettingsIntent = new Intent(this, JenkinsSettingsActivity.class);
        startActivity(jenkinsSettingsIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return MenuHandler.onOptionsItemSelected(item, this);
    }
}