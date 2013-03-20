package com.gomo.tasks;

import android.app.Activity;
import android.os.AsyncTask;
import com.gomo.activities.MonitorActivity;
import com.gomo.models.Jenkins;
import com.gomo.models.JenkinsBuild;
import com.gomo.models.JenkinsJob;
import com.gomo.utilities.GomoJsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DisplayJenkinsTask extends AsyncTask<Activity, Integer, Void> {

    public static final String ERROR_BUILD_FULL_DISPLAY_NAME = "";
    public static final boolean ERROR_BUILD_BUILDING = false;
    public static final String ERROR_BUILD_STATUS = "FAILURE";
    public static final int ERROR_BUILD_NUMBER = 0;
    public static final String ERROR_JOB_MESSAGE = "Error getting Jenkins - ";
    private MonitorActivity activity;
    private List<Jenkins> jenkinsList;
    private Exception exception;


    @Override
    protected Void doInBackground(Activity... params) {

        activity = (MonitorActivity) params[0];

        jenkinsList = new ArrayList<Jenkins>();

        Set<String> jenkinsUrls = activity.getJenkinsUrls();
        for (String jenkinsUrl : jenkinsUrls) {

            Jenkins jenkins = getJenkinsFromUrl(jenkinsUrl);

            if (jenkins == null && exception != null) {

                jenkins = getErrorJenkinsForUrl(jenkinsUrl);

                exception = null;
            }

            jenkinsList.add(jenkins);
        }

        return null;
    }

    private Jenkins getErrorJenkinsForUrl(String jenkinsUrl) {

        JenkinsBuild errorJenkinsBuild = new JenkinsBuild(ERROR_BUILD_FULL_DISPLAY_NAME, ERROR_BUILD_BUILDING, ERROR_BUILD_STATUS, ERROR_BUILD_NUMBER);

        JenkinsJob errorJenkinsJob = new JenkinsJob(ERROR_JOB_MESSAGE + jenkinsUrl, errorJenkinsBuild);

        List<JenkinsJob> errorJenkinsJobs = new ArrayList<JenkinsJob>();
        errorJenkinsJobs.add(errorJenkinsJob);

        return new Jenkins(errorJenkinsJobs);
    }

    @Override
    protected void onPostExecute(Void result) {

        activity.hideProgressBar();
        activity.displayBuildMonitor(jenkinsList);
    }

    private Jenkins getJenkinsFromUrl(String jenkinsUrl) {

        GomoJsonParser parser = new GomoJsonParser(activity);
        Jenkins jenkins = null;

        try {

            jenkins = parser.getJenkinsFromPath(jenkinsUrl);

        } catch (Exception e) {

            exception = e;
        }

        return jenkins;
    }
}