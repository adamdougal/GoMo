package com.gomo.activities;

import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.gomo.R;
import com.gomo.utilities.SharedPreferencesManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.LinkedHashSet;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)

public class AvatarSettingsActivityTest {

    private AvatarSettingsActivity avatarSettingsActivity;
    private Button addAvatarButton, removeAvatarButton, saveBtn;
    private LinearLayout listOfAvatarLayouts, avatarInput;
    private EditText initialsEditText, emailEditText;
    private SharedPreferencesManager sharedPreferencesManager;


    @Before
    public void setUp() throws Exception {

        avatarSettingsActivity = new AvatarSettingsActivity();
        avatarSettingsActivity.onCreate(null);

        sharedPreferencesManager = new SharedPreferencesManager(avatarSettingsActivity);

        addAvatarButton = (Button) avatarSettingsActivity.findViewById(R.id.addAvatarButton);
        saveBtn = (Button) avatarSettingsActivity.findViewById(R.id.saveBtn);

        setFieldsForAvatarInputAtIndex(0);
    }

    private void setFieldsForAvatarInputAtIndex(int index) {

        listOfAvatarLayouts = (LinearLayout) avatarSettingsActivity.findViewById(R.id.listOfAvatars);

        avatarInput = (LinearLayout) listOfAvatarLayouts.getChildAt(index);

        removeAvatarButton = (Button) avatarInput.getChildAt(0);
        initialsEditText = (EditText) avatarInput.getChildAt(1);
        emailEditText = (EditText) avatarInput.getChildAt(2);
    }


    private String getAvatarKeyForInitials(String initials) {
        return "Avatar_" + initials;
    }

    @Test
    public void givenAvatarSettingsActivity_ShouldBeInstanceOfGomoSettingsActivity() throws Exception {

        assertTrue(avatarSettingsActivity instanceof GomoSettingsActivity);

    }

    @Test
    public void givenAddAvatarInputIsPressed_ShouldAddAvatarInputFields() throws Exception {

        int initialChildCount = listOfAvatarLayouts.getChildCount();

        addAvatarButton.performClick();

        int finalChildCount = listOfAvatarLayouts.getChildCount();

        assertEquals(initialChildCount + 1, finalChildCount);

    }

    @Test
    public void givenRemoveAvatarInputIsPressed_ShouldRemoveInputFields() throws Exception {

        int initialChildCount = listOfAvatarLayouts.getChildCount();

        avatarSettingsActivity.removeAvatarInputClick(removeAvatarButton);

        int finalChildCount = listOfAvatarLayouts.getChildCount();

        assertEquals(initialChildCount - 1, finalChildCount);

    }

    @Test
    public void givenRemoveButtonIsPressedForAStoredInputAndSaveButtonPressed_ShouldRemoveFromSharedPreferences() throws Exception {

        String initials = "GO";
        String email = "gomo@umv.com";

        Set<String> initialsSet = new LinkedHashSet<String>();
        initialsSet.add(initials);

        sharedPreferencesManager.saveStringSet(avatarSettingsActivity.getString(R.string.avatar_initials_key), initialsSet);

        sharedPreferencesManager.saveString(getAvatarKeyForInitials(initials), email);

        avatarSettingsActivity.onCreate(null);

        setFieldsForAvatarInputAtIndex(0);

        avatarSettingsActivity.removeAvatarInputClick(removeAvatarButton);

        saveBtn.performClick();

        String defaultValue = "";
        String storedValue = sharedPreferencesManager.loadString(getAvatarKeyForInitials(initials), defaultValue);

        assertEquals(defaultValue, storedValue);
    }

    @Test
    public void givenOneCorrectInputAndSaveButtonPressed_ShouldSave() throws Exception {

        String initials = "GO";
        String email = "go@umv.com";

        initialsEditText.setText(initials);
        emailEditText.setText(email);

        saveBtn.performClick();

        String avatarKey = getAvatarKeyForInitials(initials);

        String savedEmail = sharedPreferencesManager.loadString(avatarKey, "");

        assertEquals(email, savedEmail);
    }

    @Test
    public void givenTwoCorrectInputAndSaveButtonPressed_ShouldSave() throws Exception {

        addAvatarButton.performClick();

        String initials = "GO";
        String email = "go@umv.com";

        setFieldsForAvatarInputAtIndex(0);

        initialsEditText.setText(initials);
        emailEditText.setText(email);

        setFieldsForAvatarInputAtIndex(1);

        initialsEditText.setText(initials);
        emailEditText.setText(email);

        saveBtn.performClick();

        String avatarKey = getAvatarKeyForInitials(initials);

        String savedEmail = sharedPreferencesManager.loadString(avatarKey, "");

        assertEquals(email, savedEmail);
    }

    @Test
    public void givenInvalidInitialsAndSaveButtonPressed_ShouldNotSave() throws Exception {

        String initials = "A";
        String email = "go@umv.com";

        initialsEditText.setText(initials);
        emailEditText.setText(email);

        saveBtn.performClick();

        String avatarKey = getAvatarKeyForInitials(initials);

        String defaultValue = "";
        String savedEmail = sharedPreferencesManager.loadString(avatarKey, defaultValue);

        assertEquals(defaultValue, savedEmail);
    }

    @Test
    public void givenInvalidEmailAndSaveButtonIsPressed_ShouldNotSave() throws Exception {

        String initials = "GO";
        String email = "@Gomo@umv.com";

        initialsEditText.setText(initials);
        emailEditText.setText(email);

        saveBtn.performClick();

        String avatarKey = getAvatarKeyForInitials(initials);

        String defaultValue = "";

        String savedEmail = sharedPreferencesManager.loadString(avatarKey, defaultValue);

        assertEquals(defaultValue, savedEmail);
    }

    @Test
    public void givenAvatarsSettingsSaved_ShouldLoadStoredInitialsOnAvatarInputs() throws Exception {

        String initials = "GO";
        String email = "gomo@umv.com";

        Set<String> initialsSet = new LinkedHashSet<String>();
        initialsSet.add(initials);

        sharedPreferencesManager.saveStringSet(avatarSettingsActivity.getString(R.string.avatar_initials_key), initialsSet);

        sharedPreferencesManager.saveString(getAvatarKeyForInitials(initials), email);

        avatarSettingsActivity.onCreate(null);

        setFieldsForAvatarInputAtIndex(0);

        String displayedInitials = initialsEditText.getText().toString();

        assertEquals(initials, displayedInitials);
    }

    @Test
    public void givenAvatarsSettingsSaved_ShouldLoadStoredEmailOnAvatarInputs() throws Exception {

        String initials = "GO";
        String email = "gomo@umv.com";

        Set<String> initialsSet = new LinkedHashSet<String>();
        initialsSet.add(initials);

        sharedPreferencesManager.saveStringSet(avatarSettingsActivity.getString(R.string.avatar_initials_key), initialsSet);

        sharedPreferencesManager.saveString(getAvatarKeyForInitials(initials), email);

        avatarSettingsActivity.onCreate(null);

        setFieldsForAvatarInputAtIndex(0);

        String displayedEmail = emailEditText.getText().toString();

        assertEquals(email, displayedEmail);
    }
}