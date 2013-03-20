package com.gomo.activities;

import android.os.Bundle;
import android.widget.EditText;
import com.gomo.R;
import com.gomo.utilities.AlertDialogManager;
import com.gomo.utilities.SharedPreferencesManager;

public class RefreshRateSettingsActivity extends GomoSettingsActivity {

    private String monitorRefreshRateKey;

    private EditText refreshRateEditText;

    private static final int MINIMUM_REFRESH_RATE = 5;
    private static final int DEFAULT_REFRESH_RATE_MILLISECONDS = 60000;
    private static final int ONE_SECOND_IN_MILLISECONDS = 1000;
    private static final String DIALOG_MESSAGE_TITLE_ERROR = "Error";
    private static final String DIALOG_MESSAGE_REFRESH_RATE_MUST_BE_A_NUMBER = "Refresh rate must be a number";
    private static final String DIALOG_MESSAGE_BUTTON_OK = "OK";
    private static final String DIALOG_MESSAGE_REFRESH_RATE_CANNOT_BE_EMPTY = "Refresh rate cannot be empty";
    private static final String DIALOG_MESSAGE_REFRESH_RATE_CANNOT_BE_LOWER_THAN_5_SECONDS = "Refresh rate cannot be lower than 5 seconds";

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.refresh_rate_settings);

        setupFields();
        setCurrentSharedPreferencesOnInputs();
    }

    @Override
    protected void setupFields() {

        sharedPreferencesManager = new SharedPreferencesManager(this);

        monitorRefreshRateKey = getString(R.string.monitor_refresh_rate_key);

        refreshRateEditText = (EditText) findViewById(R.id.refreshRate);
    }

    @Override
    protected void setCurrentSharedPreferencesOnInputs() {

        int refreshRateInMilliseconds = sharedPreferencesManager.loadInt(monitorRefreshRateKey, DEFAULT_REFRESH_RATE_MILLISECONDS);
        int refreshRate = refreshRateInMilliseconds / ONE_SECOND_IN_MILLISECONDS;
        refreshRateEditText.setText(String.valueOf(refreshRate));
    }

    @Override
    protected boolean saveSharedPreferencesFromInputs() {

        String refreshRateInput = refreshRateEditText.getText().toString().trim();
        boolean savedSuccessfully = validateAndSaveRefreshRate(refreshRateInput);

        return savedSuccessfully;
    }

    private boolean validateAndSaveRefreshRate(String refreshRateInput) {

        if (!validateRefreshRateNotEmpty(refreshRateInput))
            return false;

        int refreshRate = 0;

        try {

            refreshRate = Integer.parseInt(refreshRateInput);

        } catch (NumberFormatException e) {

            AlertDialogManager.showAlertDialog(this, DIALOG_MESSAGE_TITLE_ERROR, DIALOG_MESSAGE_REFRESH_RATE_MUST_BE_A_NUMBER, DIALOG_MESSAGE_BUTTON_OK, null);
            return false;
        }

        if (!validateRefreshRateMeetsMinimum(refreshRate))
            return false;

        return sharedPreferencesManager.saveInt(monitorRefreshRateKey, refreshRate * ONE_SECOND_IN_MILLISECONDS);
    }

    private boolean validateRefreshRateNotEmpty(String refreshRateInput) {

        if (refreshRateInput.isEmpty()) {

            AlertDialogManager.showAlertDialog(this, DIALOG_MESSAGE_TITLE_ERROR, DIALOG_MESSAGE_REFRESH_RATE_CANNOT_BE_EMPTY, DIALOG_MESSAGE_BUTTON_OK, null);
            return false;
        }

        return true;
    }

    private boolean validateRefreshRateMeetsMinimum(int refreshRate) {

        if (refreshRate < MINIMUM_REFRESH_RATE) {

            AlertDialogManager.showAlertDialog(this, DIALOG_MESSAGE_TITLE_ERROR, DIALOG_MESSAGE_REFRESH_RATE_CANNOT_BE_LOWER_THAN_5_SECONDS, DIALOG_MESSAGE_BUTTON_OK, null);
            return false;
        }

        return true;
    }
}