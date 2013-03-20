package com.gomo.activities;

import android.os.Bundle;
import android.widget.CheckBox;
import com.gomo.R;
import com.gomo.utilities.SharedPreferencesManager;

public class CommitInfoSettingsActivity extends GomoSettingsActivity {

    private CheckBox showCommitUserCheckBox, showCommitMessageCheckBox;

    private String showCommitMessageKey, showCommitUserKey;

    private static final boolean DEFAULT_SHOW_COMMIT_INFO_VALUE = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.commit_info_settings);

        setupFields();
        setCurrentSharedPreferencesOnInputs();
    }

    @Override
    protected void setupFields() {

        sharedPreferencesManager = new SharedPreferencesManager(this);

        showCommitMessageKey = getString(R.string.monitor_show_commit_message_key);
        showCommitUserKey = getString(R.string.monitor_show_commit_user_key);

        showCommitUserCheckBox = (CheckBox) findViewById(R.id.showCommitUser);
        showCommitMessageCheckBox = (CheckBox) findViewById(R.id.showCommitMessage);
    }

    @Override
    protected void setCurrentSharedPreferencesOnInputs() {

        boolean showCommitUser = sharedPreferencesManager.loadBoolean(showCommitUserKey, DEFAULT_SHOW_COMMIT_INFO_VALUE);
        showCommitUserCheckBox.setChecked(showCommitUser);

        boolean showCommitMessage = sharedPreferencesManager.loadBoolean(showCommitMessageKey, DEFAULT_SHOW_COMMIT_INFO_VALUE);
        showCommitMessageCheckBox.setChecked(showCommitMessage);
    }

    @Override
    protected boolean saveSharedPreferencesFromInputs() {

        boolean showCommitUser = showCommitUserCheckBox.isChecked();
        sharedPreferencesManager.saveBoolean(showCommitUserKey, showCommitUser);

        boolean showCommitMessage = showCommitMessageCheckBox.isChecked();
        sharedPreferencesManager.saveBoolean(showCommitMessageKey, showCommitMessage);

        return true;
    }
}