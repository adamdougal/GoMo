package com.gomo.utilities;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuItem;
import com.gomo.R;
import com.gomo.activities.AvatarSettingsActivity;
import com.gomo.activities.CommitInfoSettingsActivity;
import com.gomo.activities.JenkinsSettingsActivity;
import com.gomo.activities.RefreshRateSettingsActivity;

public class MenuHandler {

    public static boolean onOptionsItemSelected(MenuItem item, Activity activity) {

        switch (item.getItemId()) {

            case R.id.jenkinsMenuItem:

                navigateToJenkinsSettings(activity);
                return true;

            case R.id.refreshRateMenuItem:

                navigateToRefreshRateSettings(activity);
                return true;

            case R.id.commitInfoMenuItem:

                navigateToCommitInfoSettings(activity);
                return true;

            case R.id.avatarMenuItem:

                navigateToAvatarSettings(activity);
                return true;

        }

        return false;
    }

    public static void navigateToAvatarSettings(Activity activity) {

        Intent avatarSettingsIntent = new Intent(activity, AvatarSettingsActivity.class);
        activity.startActivity(avatarSettingsIntent);
    }

    public static void navigateToJenkinsSettings(Activity activity) {

        Intent jenkinsSettingsIntent = new Intent(activity, JenkinsSettingsActivity.class);
        activity.startActivity(jenkinsSettingsIntent);
    }

    public static void navigateToRefreshRateSettings(Activity activity) {

        Intent refreshRateSettingsIntent = new Intent(activity, RefreshRateSettingsActivity.class);
        activity.startActivity(refreshRateSettingsIntent);
    }

    public static void navigateToCommitInfoSettings(Activity activity) {

        Intent commitInfoSettingsIntent = new Intent(activity, CommitInfoSettingsActivity.class);
        activity.startActivity(commitInfoSettingsIntent);
    }
}
