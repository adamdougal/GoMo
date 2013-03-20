package com.gomo.activities;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.gomo.R;
import com.gomo.utilities.SharedPreferencesManager;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.LinkedHashSet;
import java.util.Set;

import static junit.framework.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class JenkinsSettingsActivityTest {

    public static final int INDEX_OF_FIRST_JENKINS_URL_LAYOUT = 0;
    public static final int INDEX_OF_JENKINS_URL_TEXT_VIEW = 1;
    public static final int INDEX_OF_DELETE_JENKINS_URL_BUTTON = 0;
    private JenkinsSettingsActivity jenkinsSettingsActivity;
    private SharedPreferencesManager sharedPreferencesManager;
    private String jenkinsUrlsKey;

    @Before
    public void setUp() throws Exception {

        jenkinsSettingsActivity = new JenkinsSettingsActivity();

        sharedPreferencesManager = new SharedPreferencesManager(jenkinsSettingsActivity);

        jenkinsUrlsKey = jenkinsSettingsActivity.getString(R.string.monitor_jenkins_url_key);
    }

    private LinearLayout getJenkinsUrlLayout() {

        return (LinearLayout) jenkinsSettingsActivity.findViewById(R.id.jenkinsUrlInputsLayout);
    }

    private Button getDeleteButtonOfFirstJenkinsUrlLayout() {

        LinearLayout jenkinsUrlsLayout = (LinearLayout) jenkinsSettingsActivity.findViewById(R.id.jenkinsUrlInputsLayout);

        LinearLayout jenkinsUrlLayout = (LinearLayout) jenkinsUrlsLayout.getChildAt(INDEX_OF_FIRST_JENKINS_URL_LAYOUT);

        return (Button) jenkinsUrlLayout.getChildAt(INDEX_OF_DELETE_JENKINS_URL_BUTTON);
    }

    @Test
    public void givenOneUrlIsStored_LayoutShouldHaveOneChild() throws Exception {

        Set<String> jenkinsUrls = new LinkedHashSet<String>();
        jenkinsUrls.add("http://url.com/");

        sharedPreferencesManager.saveStringSet(jenkinsUrlsKey, jenkinsUrls);

        jenkinsSettingsActivity.onCreate(null);

        LinearLayout jenkinsUrlsLayout = getJenkinsUrlLayout();

        int numberOfUrls = jenkinsUrls.size();
        int numberOfLayoutChildren = jenkinsUrlsLayout.getChildCount();

        assertEquals(numberOfUrls, numberOfLayoutChildren);
    }

    @Test
    public void givenTwoUrlsAreStored_LayoutShouldHaveTwoChildren() throws Exception {

        Set<String> jenkinsUrls = new LinkedHashSet<String>();
        jenkinsUrls.add("http://url.com/");
        jenkinsUrls.add("http://anotherurl.com/");

        sharedPreferencesManager.saveStringSet(jenkinsUrlsKey, jenkinsUrls);

        jenkinsSettingsActivity.onCreate(null);

        LinearLayout jenkinsUrlsLayout = getJenkinsUrlLayout();

        int numberOfUrls = jenkinsUrls.size();
        int numberOfLayoutChildren = jenkinsUrlsLayout.getChildCount();

        assertEquals(numberOfUrls, numberOfLayoutChildren);
    }

    @Test
    public void givenOneUrlIsStored_LayoutShouldHaveOneChildWithThatUrl() throws Exception {

        String storedJenkinsUrl = "http://url.com/";

        Set<String> jenkinsUrls = new LinkedHashSet<String>();
        jenkinsUrls.add(storedJenkinsUrl);

        sharedPreferencesManager.saveStringSet(jenkinsUrlsKey, jenkinsUrls);

        jenkinsSettingsActivity.onCreate(null);

        LinearLayout jenkinsUrlsLayout = getJenkinsUrlLayout();

        LinearLayout jenkinsUrlLayout = (LinearLayout) jenkinsUrlsLayout.getChildAt(INDEX_OF_FIRST_JENKINS_URL_LAYOUT);

        TextView jenkinsUrlTextView = (TextView) jenkinsUrlLayout.getChildAt(INDEX_OF_JENKINS_URL_TEXT_VIEW);

        String outputtedJenkinsUrl = jenkinsUrlTextView.getText().toString();

        assertEquals(storedJenkinsUrl, outputtedJenkinsUrl);
    }

    @Test
    public void givenOneUrlIsStoredAndDeleteButtonPressed_LayoutShouldHaveNoChildren() throws Exception {

        Set<String> jenkinsUrls = new LinkedHashSet<String>();
        jenkinsUrls.add("http://url.com/");

        sharedPreferencesManager.saveStringSet(jenkinsUrlsKey, jenkinsUrls);

        jenkinsSettingsActivity.onCreate(null);

        LinearLayout jenkinsUrlsLayout = getJenkinsUrlLayout();

        Button deleteButton = getDeleteButtonOfFirstJenkinsUrlLayout();

        jenkinsSettingsActivity.removeJenkinsUrlInput(deleteButton);

        int noOfJenkinsUrlLayouts = jenkinsUrlsLayout.getChildCount();

        assertEquals(jenkinsUrls.size() - 1, noOfJenkinsUrlLayouts);
    }

    @Test
    public void givenTwoUrlsAreStoredThenOneIsDeletedThenSaveButtonIsPressed_StoredJenkinsUrlsShouldOnlyHaveOneUrl() throws Exception {

        Set<String> jenkinsUrls = new LinkedHashSet<String>();
        jenkinsUrls.add("http://url.com/");
        jenkinsUrls.add("http://anotherurl.com/");

        sharedPreferencesManager.saveStringSet(jenkinsUrlsKey, jenkinsUrls);

        jenkinsSettingsActivity.onCreate(null);

        Button deleteButton = getDeleteButtonOfFirstJenkinsUrlLayout();

        jenkinsSettingsActivity.removeJenkinsUrlInput(deleteButton);

        Button saveButton = (Button) jenkinsSettingsActivity.findViewById(R.id.saveBtn);

        saveButton.performClick();

        int sizeOfStoredSet = sharedPreferencesManager.loadStringSet(jenkinsUrlsKey, null).size();

        assertEquals(jenkinsUrls.size() - 1, sizeOfStoredSet);
    }
}
