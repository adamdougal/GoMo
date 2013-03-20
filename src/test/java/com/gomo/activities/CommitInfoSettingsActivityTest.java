package com.gomo.activities;

import android.widget.Button;
import android.widget.CheckBox;
import com.gomo.R;
import com.gomo.utilities.SharedPreferencesManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class CommitInfoSettingsActivityTest {

    private CommitInfoSettingsActivity commitInfoSettingsActivity;
    private SharedPreferencesManager sharedPreferencesManager;
    private String showCommitMessageKey;
    private String showCommitUserKey;

    @Before
    public void setUp() throws Exception {

        commitInfoSettingsActivity = new CommitInfoSettingsActivity();

        sharedPreferencesManager = new SharedPreferencesManager(commitInfoSettingsActivity);

        showCommitMessageKey = commitInfoSettingsActivity.getString(R.string.monitor_show_commit_message_key);
        showCommitUserKey = commitInfoSettingsActivity.getString(R.string.monitor_show_commit_user_key);
    }

    private boolean isCommitMessageCheckboxChecked() {

        commitInfoSettingsActivity.onCreate(null);
        CheckBox commitMessageCheckbox = (CheckBox) commitInfoSettingsActivity.findViewById(R.id.showCommitMessage);
        return commitMessageCheckbox.isChecked();
    }

    private boolean isCommitUserCheckboxChecked() {

        commitInfoSettingsActivity.onCreate(null);
        CheckBox commitUserCheckbox = (CheckBox) commitInfoSettingsActivity.findViewById(R.id.showCommitUser);
        return commitUserCheckbox.isChecked();
    }

    private void setCommitMessageCheckboxChecked(boolean checked) {

        CheckBox commitMessageCheckbox = (CheckBox) commitInfoSettingsActivity.findViewById(R.id.showCommitMessage);
        commitMessageCheckbox.setChecked(checked);
    }

    private void setCommitUserCheckboxChecked(boolean checked) {

        CheckBox commitUserCheckbox = (CheckBox) commitInfoSettingsActivity.findViewById(R.id.showCommitUser);
        commitUserCheckbox.setChecked(checked);
    }

    @Test
    public void givenShowCommitMessageStoredAsTrue_ShouldCheckCommitMessageCheckbox() throws Exception {

        boolean showCommitMessage = true;

        sharedPreferencesManager.saveBoolean(showCommitMessageKey, showCommitMessage);

        boolean isCheckboxChecked = isCommitMessageCheckboxChecked();

        assertEquals(showCommitMessage, isCheckboxChecked);
    }

    @Test
    public void givenShowCommitMessageStoredAsFalse_ShouldNotCheckCommitMessageCheckbox() throws Exception {

        boolean showCommitMessage = false;

        sharedPreferencesManager.saveBoolean(showCommitMessageKey, showCommitMessage);

        boolean isCheckboxChecked = isCommitMessageCheckboxChecked();

        assertEquals(showCommitMessage, isCheckboxChecked);
    }

    @Test
    public void givenShowCommitMessageIsNotStored_ShouldCheckCommitMessageCheckbox() throws Exception {

        sharedPreferencesManager.clearAll();

        boolean isCheckboxChecked = isCommitMessageCheckboxChecked();

        boolean defaultShowCommitMessage = true;

        assertEquals(defaultShowCommitMessage, isCheckboxChecked);
    }

    @Test
    public void givenShowCommitUserStoredAsTrue_ShouldCheckCommitUserCheckbox() throws Exception {

        boolean showCommitUser = true;

        sharedPreferencesManager.saveBoolean(showCommitUserKey, showCommitUser);

        boolean isCheckboxChecked = isCommitUserCheckboxChecked();

        assertEquals(showCommitUser, isCheckboxChecked);
    }

    @Test
    public void givenShowCommitUserStoredAsFalse_ShouldNotCheckCommitUserCheckbox() throws Exception {

        boolean showCommitUser = false;

        sharedPreferencesManager.saveBoolean(showCommitUserKey, showCommitUser);

        boolean isCheckboxChecked = isCommitUserCheckboxChecked();

        assertEquals(showCommitUser, isCheckboxChecked);
    }

    @Test
    public void givenShowCommitUserIsNotStored_ShouldCheckCommitUserCheckbox() throws Exception {

        sharedPreferencesManager.clearAll();

        boolean isCheckboxChecked = isCommitUserCheckboxChecked();

        boolean defaultShowCommitUser = true;

        assertEquals(defaultShowCommitUser, isCheckboxChecked);
    }

    @Test
    public void givenShowCommitMessageChangedAndSaveBtnPressed_ShouldStoreSettings() throws Exception {

        boolean initialShowCommitMessage = false;
        boolean finalShowCommitMessage = true;

        sharedPreferencesManager.saveBoolean(showCommitMessageKey, initialShowCommitMessage);

        commitInfoSettingsActivity.onCreate(null);

        setCommitMessageCheckboxChecked(finalShowCommitMessage);

        Button saveBtn = (Button) commitInfoSettingsActivity.findViewById(R.id.saveBtn);
        saveBtn.performClick();

        boolean showCommitMessage = sharedPreferencesManager.loadBoolean(showCommitMessageKey, initialShowCommitMessage);

        assertEquals(finalShowCommitMessage, showCommitMessage);
    }

    @Test
    public void givenShowCommitUserChangedAndSaveBtnPressed_ShouldStoreSettings() throws Exception {

        boolean initialShowCommitUser = true;
        boolean finalShowCommitUser = false;

        sharedPreferencesManager.saveBoolean(showCommitUserKey, initialShowCommitUser);

        commitInfoSettingsActivity.onCreate(null);

        setCommitUserCheckboxChecked(finalShowCommitUser);

        Button saveBtn = (Button) commitInfoSettingsActivity.findViewById(R.id.saveBtn);
        saveBtn.performClick();

        boolean showCommitUser = sharedPreferencesManager.loadBoolean(showCommitUserKey, initialShowCommitUser);

        assertEquals(finalShowCommitUser, showCommitUser);
    }
}
