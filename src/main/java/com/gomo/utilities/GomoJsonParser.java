package com.gomo.utilities;

import android.app.Activity;
import com.gomo.models.*;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GomoJsonParser {

    private static final String API_JSON_SUFFIX = "api/json";
    public static final boolean DEFAULT_SHOULD_GET_JOB = false;
    private SharedPreferencesManager sharedPreferencesManager;

    public GomoJsonParser(Activity activity) {

        this.sharedPreferencesManager = new SharedPreferencesManager(activity);
    }

    public Jenkins getJenkinsFromPath(String path) throws IOException, JSONException {

        RespJenkins jenkinsData = getRespJenkinsFromPath(path);

        List<JenkinsJob> jenkinsJobs = getJenkinsJobsFromJenkinsData(jenkinsData);

        Jenkins jenkins = new Jenkins(jenkinsJobs);

        return jenkins;
    }

    public RespJenkins getRespJenkinsFromPath(String path) throws IOException, JSONException {

        File jsonFile = getFileFromUrl(path);

        RespJenkins jenkinsData = (RespJenkins) parseJsonFromFile(jsonFile, RespJenkins.class);

        jsonFile.delete();

        return jenkinsData;
    }

    private List<JenkinsJob> getJenkinsJobsFromJenkinsData(RespJenkins jenkinsData) throws IOException, JSONException {

        List<JenkinsJob> jenkinsJobs = new ArrayList<JenkinsJob>();
        for (RespJenkins.Jobs job : jenkinsData.getJobs()) {

            String jenkinsJobUrl = job.getUrl();

            if (shouldGetJob(jenkinsJobUrl)) {

                JenkinsJob jenkinsJob = getJenkinsJobFromPath(jenkinsJobUrl);
                jenkinsJobs.add(jenkinsJob);
            }
        }

        return jenkinsJobs;
    }

    public boolean shouldGetJob(String jenkinsJobUrl) {

        return sharedPreferencesManager.loadBoolean(jenkinsJobUrl, DEFAULT_SHOULD_GET_JOB);
    }

    public JenkinsJob getJenkinsJobFromPath(String path) throws IOException, JSONException {

        File jsonFile = getFileFromUrl(path);

        RespJenkinsJob jobData = (RespJenkinsJob) parseJsonFromFile(jsonFile, RespJenkinsJob.class);

        jsonFile.delete();

        JenkinsBuild lastBuild = null;
        if (jobData.getLastBuild() != null) {
            String lastBuildUrl = jobData.getLastBuild().getUrl();
            lastBuild = getJenkinsBuildFromPath(lastBuildUrl);
        }

        JenkinsJob jenkinsJob = new JenkinsJob(jobData.getDisplayName(), lastBuild);

        return jenkinsJob;
    }

    public JenkinsBuild getJenkinsBuildFromPath(String path) throws IOException, JSONException {

        File jsonFile = getFileFromUrl(path);

        RespJenkinsBuild buildData = (RespJenkinsBuild) parseJsonFromFile(jsonFile, RespJenkinsBuild.class);

        jsonFile.delete();

        String fullDisplayName = buildData.getFullDisplayName();
        String buildStatus = buildData.getResult();
        boolean building = buildData.getBuilding();
        int buildNumber = buildData.getNumber();

        boolean hasItems = buildData.getChangeSet().getItems().size() > 0;

        JenkinsBuild jenkinsBuild = createJenkinsBuildWithData(buildData, fullDisplayName, buildStatus, building, buildNumber, hasItems);

        return jenkinsBuild;
    }

    private JenkinsBuild createJenkinsBuildWithData(RespJenkinsBuild buildData, String fullDisplayName, String buildStatus, boolean building, int buildNumber, boolean hasItems) {

        JenkinsBuild jenkinsBuild;
        if (hasItems) {

            String commitMessage = buildData.getChangeSet().getItems().get(0).getMsg();
            String commitUser = buildData.getChangeSet().getItems().get(0).getUser();

            jenkinsBuild = new JenkinsBuild(fullDisplayName, building, buildStatus, buildNumber, commitMessage, commitUser);

        } else {

            jenkinsBuild = new JenkinsBuild(fullDisplayName, building, buildStatus, buildNumber);
        }

        return jenkinsBuild;
    }

    private File getFileFromUrl(String path) throws IOException, JSONException {

        return GomoHttpClient.getFileFromUrl(getJenkinsApiPathFromURL(path));
    }

    private Object parseJsonFromFile(File file, Class objectClass) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        return mapper.readValue(file, objectClass);
    }

    public String getJenkinsApiPathFromURL(String jenkinsBuildFilePath) {

        return jenkinsBuildFilePath + API_JSON_SUFFIX;
    }
}
