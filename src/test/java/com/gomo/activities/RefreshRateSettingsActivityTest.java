package com.gomo.activities;

import android.widget.Button;
import android.widget.EditText;
import com.gomo.R;
import com.gomo.utilities.SharedPreferencesManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class RefreshRateSettingsActivityTest {

    private RefreshRateSettingsActivity refreshRateSettingsActivity;
    private SharedPreferencesManager sharedPreferencesManager;
    private String refreshRateKey;

    @Before
    public void setUp() throws Exception {

        refreshRateSettingsActivity = new RefreshRateSettingsActivity();

        sharedPreferencesManager = new SharedPreferencesManager(refreshRateSettingsActivity);

        refreshRateKey = refreshRateSettingsActivity.getString(R.string.monitor_refresh_rate_key);

        refreshRateSettingsActivity.onCreate(null);
    }

    private EditText getRefreshRateEditText() {

        return (EditText) refreshRateSettingsActivity.findViewById(R.id.refreshRate);
    }

    private Button getSaveButton() {

        return (Button) refreshRateSettingsActivity.findViewById(R.id.saveBtn);
    }

    @Test
    public void givenRefreshRateIsStored_ShouldDisplayItInEditText() throws Exception {

        int initialStoredValue = 5000;

        sharedPreferencesManager.saveInt(refreshRateKey, initialStoredValue);

        refreshRateSettingsActivity.onCreate(null);

        String outputtedValue = getRefreshRateEditText().getText().toString();

        assertEquals(initialStoredValue / 1000, Integer.parseInt(outputtedValue));
    }

    @Test
    public void givenAValidStringInput_ShouldSaveIntoMonitorRefreshRateAsInt() throws Exception {

        String validRefreshRateInput = "5";

        getRefreshRateEditText().setText(validRefreshRateInput);

        getSaveButton().performClick();

        int savedInt = sharedPreferencesManager.loadInt(refreshRateKey, 0);

        assertEquals(Integer.parseInt(validRefreshRateInput), savedInt / 1000);
    }

    @Test
    public void givenAValidStringLessThanFive_ShouldNotSaveIntoMonitorJenkinsRefreshRateKey() throws Exception {

        int initialValue = 5000;

        sharedPreferencesManager.saveInt(refreshRateKey, initialValue);

        getRefreshRateEditText().setText("4");

        getSaveButton().performClick();

        int savedInt = sharedPreferencesManager.loadInt(refreshRateKey, 0);

        assertEquals(initialValue, savedInt);
    }

    @Test
    public void givenAnEmptyString_ShouldNotSaveIntoMonitorJenkinsRefreshRateKey() throws Exception {

        int initialValue = 5000;

        sharedPreferencesManager.saveInt(refreshRateKey, initialValue);

        getRefreshRateEditText().setText("");

        getSaveButton().performClick();

        int savedInt = sharedPreferencesManager.loadInt(refreshRateKey, 0);

        assertEquals(initialValue, savedInt);
    }

    @Test
    public void givenANonNumericString_ShouldNotSaveIntoMonitorJenkinsRefreshRateKey() throws Exception {

        int initialValue = 5000;

        sharedPreferencesManager.saveInt(refreshRateKey, initialValue);

        getRefreshRateEditText().setText("a");

        getSaveButton().performClick();

        int savedInt = sharedPreferencesManager.loadInt(refreshRateKey, 0);

        assertEquals(initialValue, savedInt);
    }
}
