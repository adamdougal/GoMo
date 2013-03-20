package com.gomo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.gomo.R;
import com.gomo.utilities.SharedPreferencesManager;

import java.util.LinkedHashSet;
import java.util.Set;

public class JenkinsSettingsActivity extends GomoSettingsActivity {

    private String monitorJenkinsUrlKey, jenkinsUrlKey;
    private LinearLayout jenkinsUrlInputsLayout;
    private Set<String> DEFAULT_JENKINS_URLS = new LinkedHashSet<String>();

    private static final int INDEX_OF_JENKINS_URL_TEXT_VIEW = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.jenkins_settings);

        setupFields();
        setCurrentSharedPreferencesOnInputs();
    }

    @Override
    protected void setupFields() {

        sharedPreferencesManager = new SharedPreferencesManager(this);

        monitorJenkinsUrlKey = getString(R.string.monitor_jenkins_url_key);
        jenkinsUrlKey = getString(R.string.jenkins_url_intent_key);

        jenkinsUrlInputsLayout = (LinearLayout) findViewById(R.id.jenkinsUrlInputsLayout);
    }

    @Override
    protected void setCurrentSharedPreferencesOnInputs() {

        Set<String> jenkinsUrlStrings = sharedPreferencesManager.loadStringSet(monitorJenkinsUrlKey, DEFAULT_JENKINS_URLS);

        for (String jenkinsUrl : jenkinsUrlStrings) {

            addJenkinsUrlInputWithString(jenkinsUrl);
        }
    }

    private void addJenkinsUrlInputWithString(String jenkinsUrl) {

        LinearLayout addedJenkinsUrlInput = addJenkinsUrlInput();

        TextView lastJenkinsUrlTextView = (TextView) addedJenkinsUrlInput.getChildAt(INDEX_OF_JENKINS_URL_TEXT_VIEW);

        lastJenkinsUrlTextView.setText(jenkinsUrl);
    }

    private LinearLayout addJenkinsUrlInput() {

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        LinearLayout jenkinsUrlInputLayout = (LinearLayout) inflater.inflate(R.layout.jenkins_url_input, null);

        jenkinsUrlInputsLayout.addView(jenkinsUrlInputLayout);

        return jenkinsUrlInputLayout;
    }

    @Override
    protected boolean saveSharedPreferencesFromInputs() {

        Set<String> jenkinsUrlInputs = getJenkinsUrlStringsFromInputs();
        saveJenkinsUrlInputs(jenkinsUrlInputs);

        return true;
    }

    private Set<String> getJenkinsUrlStringsFromInputs() {

        Set<String> jenkinsUrlInputs = new LinkedHashSet<String>();

        for (int i = 0; i < jenkinsUrlInputsLayout.getChildCount(); i++) {

            String jenkinsUrl = getJenkinsUrlInputStringFromInputAtIndex(i);
            jenkinsUrlInputs.add(jenkinsUrl);
        }

        return jenkinsUrlInputs;
    }

    private String getJenkinsUrlInputStringFromInputAtIndex(int index) {

        LinearLayout jenkinsUrlInput = (LinearLayout) jenkinsUrlInputsLayout.getChildAt(index);
        TextView jenkinsUrlTextView = (TextView) jenkinsUrlInput.getChildAt(INDEX_OF_JENKINS_URL_TEXT_VIEW);

        return jenkinsUrlTextView.getText().toString().trim();
    }

    private void saveJenkinsUrlInputs(Set<String> jenkinsUrlInputs) {

        sharedPreferencesManager.saveStringSet(monitorJenkinsUrlKey, jenkinsUrlInputs);
    }

    public void addJenkinsUrlInputClick(View v) {

        navigateToJenkinsSettingsWithExtra(null, null);
    }

    private void navigateToJenkinsSettingsWithExtra(String key, String string) {

        Intent jenkinsSettingIntent = new Intent(this, JenkinsJobsSettingsActivity.class);

        if ((key != null && !key.isEmpty()) && (string != null && !string.isEmpty()))
            jenkinsSettingIntent.putExtra(key, string);

        startActivity(jenkinsSettingIntent);
        finish();
    }

    public void removeJenkinsUrlInput(View v) {

        Button button = (Button) v;
        LinearLayout layout = (LinearLayout) button.getParent();
        jenkinsUrlInputsLayout.removeView(layout);
    }

    public void editJenkinsUrlClick(View v) {

        LinearLayout jenkinsUrlInputLayout = (LinearLayout) v.getParent();
        TextView jenkinsUrlTextView = (TextView) jenkinsUrlInputLayout.getChildAt(INDEX_OF_JENKINS_URL_TEXT_VIEW);

        String jenkinsUrl = jenkinsUrlTextView.getText().toString();

        navigateToJenkinsSettingsWithExtra(jenkinsUrlKey, jenkinsUrl);
    }
}