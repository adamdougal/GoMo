package com.gomo.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.gomo.R;
import com.gomo.utilities.AlertDialogManager;
import com.gomo.utilities.SharedPreferencesManager;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AvatarSettingsActivity extends GomoSettingsActivity {

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final int INDEX_OF_INITIALS_EDIT_TEXT = 1;
    private static final int INDEX_OF_EMAIL_EDIT_TEXT = 2;
    private static final int MINIMUM_INITIALS_LENGTH = 2;
    private static final String DEFAULT_EMAIL_VALUE = "";
    private static final String DIALOG_MESSAGE_TITLE_ERROR = "Error";
    private static final String INITIALS_LENGTH_VALIDATION_ERROR = "Length of initials must be greater then 1.";
    private static final String DIALOG_MESSAGE_OK_BUTTON = "OK";
    private static final String NOT_VALID_EMAIL = "Not A Valid Email";

    private String avatarKeyPrefix, avatarInitialsKey;

    private LinearLayout listOfAvatarsInputs;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.avatar_settings);

        setupFields();
        setCurrentSharedPreferencesOnInputs();
    }

    @Override
    protected void setupFields() {

        sharedPreferencesManager = new SharedPreferencesManager(this);

        avatarKeyPrefix = getString(R.string.avatar_key_prefix);
        avatarInitialsKey = getString(R.string.avatar_initials_key);

        listOfAvatarsInputs = (LinearLayout) findViewById(R.id.listOfAvatars);
    }

    @Override
    protected void setCurrentSharedPreferencesOnInputs() {

        Set<String> initialsSet = sharedPreferencesManager.loadStringSet(getString(R.string.avatar_initials_key), new LinkedHashSet<String>());

        if (initialsSet.isEmpty()) {

            addAvatarInput();
            return;
        }

        for (String initials : initialsSet) {

            String email = sharedPreferencesManager.loadString(constructAvatarKeyWithInitials(initials), DEFAULT_EMAIL_VALUE);

            LinearLayout avatarInput = addAvatarInput();
            EditText initialsEditText = getInitialsEditTextFromLayout(avatarInput);
            EditText emailEditText = getEmailEditTextFromLayout(avatarInput);

            initialsEditText.setText(initials);
            emailEditText.setText(email);
        }

    }

    public void addAvatarInputClick(View v) {

        addAvatarInput();
    }

    private LinearLayout addAvatarInput() {

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        LinearLayout avatarInputLayout = (LinearLayout) inflater.inflate(R.layout.avatar_input, null);

        listOfAvatarsInputs.addView(avatarInputLayout);

        return avatarInputLayout;
    }

    public void removeAvatarInputClick(View v) {

        LinearLayout avatarLayout = (LinearLayout) v.getParent();

        removeAvatarSettingFromSharedPreferences(avatarLayout);

        listOfAvatarsInputs.removeView(avatarLayout);
    }

    private void removeAvatarSettingFromSharedPreferences(LinearLayout avatarLayout) {

        EditText initialsEditText = getInitialsEditTextFromLayout(avatarLayout);
        String initials = getStringFromEditText(initialsEditText);

        sharedPreferencesManager.remove(constructAvatarKeyWithInitials(initials));
    }

    @Override
    protected boolean saveSharedPreferencesFromInputs() {

        Set<String> avatarInitialsKeys = new LinkedHashSet<String>();

        for (int i = 0; i < listOfAvatarsInputs.getChildCount(); i++) {

            LinearLayout avatarInput = (LinearLayout) listOfAvatarsInputs.getChildAt(i);

            EditText initialsEditText = getInitialsEditTextFromLayout(avatarInput);
            EditText emailEditText = getEmailEditTextFromLayout(avatarInput);

            String initialsValue = getStringFromEditText(initialsEditText).toUpperCase();
            String emailValue = getStringFromEditText(emailEditText);

            if (!saveSharedPreferencesFromInput(initialsValue, emailValue)) return false;

            avatarInitialsKeys.add(initialsValue);
        }

        return sharedPreferencesManager.saveStringSet(avatarInitialsKey, avatarInitialsKeys);
    }

    private String getStringFromEditText(EditText editText) {

        return editText.getText().toString().trim();
    }

    private EditText getInitialsEditTextFromLayout(LinearLayout avatarLayout) {

        return (EditText) avatarLayout.getChildAt(INDEX_OF_INITIALS_EDIT_TEXT);
    }

    private EditText getEmailEditTextFromLayout(LinearLayout avatarLayout) {

        return (EditText) avatarLayout.getChildAt(INDEX_OF_EMAIL_EDIT_TEXT);
    }

    private boolean saveSharedPreferencesFromInput(String initialsValue, String emailValue) {

        if (!validateInputs(initialsValue, emailValue)) return false;

        String initialsKey = constructAvatarKeyWithInitials(initialsValue);

        return sharedPreferencesManager.saveString(initialsKey, emailValue);
    }

    private boolean validateInputs(String initialsValue, String emailValue) {

        if (!validateInitialsLength(initialsValue)) {
            AlertDialogManager.showAlertDialog(this, DIALOG_MESSAGE_TITLE_ERROR, INITIALS_LENGTH_VALIDATION_ERROR, DIALOG_MESSAGE_OK_BUTTON, null);
            return false;
        }

        if (!validateEmail(emailValue)) {
            AlertDialogManager.showAlertDialog(this, DIALOG_MESSAGE_TITLE_ERROR, NOT_VALID_EMAIL, DIALOG_MESSAGE_OK_BUTTON, null);
            return false;
        }

        return true;
    }

    private String constructAvatarKeyWithInitials(String initials) {

        return avatarKeyPrefix + initials;
    }

    private boolean validateInitialsLength(String initialsValue) {

        return initialsValue.length() >= MINIMUM_INITIALS_LENGTH;
    }

    private boolean validateEmail(String emailValue) {

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(emailValue);

        return matcher.matches();
    }
}
