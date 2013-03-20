package com.gomo.tasks;

import android.os.AsyncTask;
import com.gomo.activities.JenkinsJobsSettingsActivity;
import com.gomo.models.RespJenkins;
import com.gomo.utilities.GomoJsonParser;

public class GetJenkinsRespTask extends AsyncTask<Void, Integer, Void> {

    private final JenkinsJobsSettingsActivity activity;
    private final String jenkinsURL;
    private RespJenkins respJenkins;
    private Exception exception;

    public GetJenkinsRespTask(JenkinsJobsSettingsActivity activity, String jenkinsURL) {

        super();
        this.activity = activity;
        this.jenkinsURL = jenkinsURL;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();

        activity.showProgressBar(true);
        activity.showButtons(false);
    }

    @Override
    protected Void doInBackground(Void... params) {

        getRespJenkins();

        return null;
    }

    private void getRespJenkins() {

        GomoJsonParser gomoJsonParser = new GomoJsonParser(activity);

        try {

            respJenkins = gomoJsonParser.getRespJenkinsFromPath(jenkinsURL);

        } catch (Exception e) {

            exception = e;
        }
    }

    @Override
    protected void onPostExecute(Void aVoid) {

        super.onPostExecute(aVoid);

        activity.showProgressBar(false);
        activity.setFetchButtonEnabled(true);

        if (exception == null) {

            activity.showButtons(true);
            activity.displayJenkinsJobs(respJenkins);

        } else {

            activity.showAlertForException(exception);
        }
    }
}
