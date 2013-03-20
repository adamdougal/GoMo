package com.gomo.views;


import android.app.Activity;
import android.graphics.Color;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.gomo.R;
import com.gomo.models.Jenkins;
import com.gomo.models.JenkinsBuild;
import com.gomo.models.JenkinsJob;
import com.gomo.utilities.GomoGravatar;
import com.gomo.utilities.SharedPreferencesManager;
import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MonitorView {

    private static final int MIN_JOBS_FOR_MULIPLE_COLUMNS = 4;
    private static final int AVATAR_IMAGE_VIEW_MARGIN = 2;
    private static final int TWO_COLUMNS = AVATAR_IMAGE_VIEW_MARGIN;
    private static final int ONE_COLUMN = 1;
    private static final float COLUMN_WEIGHT = 1f;
    private static final int COLUMN_MARGIN_LEFT = 3;
    private static final int COLUMN_MARGIN_TOP = 0;
    private static final int COLUMN_MARGIN_RIGHT = 0;
    private static final int COLUMN_MARGIN_BOTTOM = 0;
    private static final int COLUMN_WIDTH = 0;
    private static final String BUILD_NUMBER_PREFIX = " #";
    private static final boolean DEFAULT_SHOW_COMMIT_INFO_VALUE = true;
    private static final int DEFAULT_COLOUR = Color.GRAY;
    private static final int JOB_LAYOUT_LEFT_MARGIN = 0;
    private static final int JOB_LAYOUT_TOP_MARGIN = 0;
    private static final int JOB_LAYOUT_RIGHT_MARGIN = 0;
    private static final int JOB_LAYOUT_BOTTOM_MARGIN = 3;
    private static final int JOB_LAYOUT_ORIENTATION = LinearLayout.VERTICAL;
    private static final float JOB_NAME_TEXT_SIZE = 14f;
    private static final float JOB_COMMIT_INFO_TEXT_SIZE = 10f;
    private static final String BUILDING_COLOUR = "#00CC00";
    private static final String SUCCESS_BUILD_STATUS = "SUCCESS";
    private static final String FAILURE_BUILD_STATE = "FAILURE";
    private static final String SUCCESS_COLOUR = "#007000";
    private static final String FAILURE_COLOUR = "#FF0000";
    private static final String COMMIT_MESSAGE_EXTRACTION_PATTERN = "(?<=\\[)(.*?)(?=\\])";
    private static final int COMMIT_MESSAGE_COMPONENT_INDEX = 1;
    private static final String COMMIT_MESSAGE_COMPONENT_DELIMETER = "\\|";
    private static final String INITIALS_DELIMETER = "-";
    private static final String GRAVATAR_EMAIL_DEFAULT_VALUE = "";
    private static final int AVATAR_IMAGE_SIZE = 30;
    private static final int AVATAR_IMAGE_CACHE_DURATION_MS = 300000;

    private Activity activity;
    private LinearLayout jenkinsMonitorLayout;
    private boolean showCommitUser, showCommitMessage;
    private List<Jenkins> jenkinsList;
    private SharedPreferencesManager sharedPreferencesManager;

    public MonitorView(Activity activity, List<Jenkins> jenkinsList) {

        this.activity = activity;
        this.jenkinsList = jenkinsList;

        setupWidgetsFromLayout();
        setupSharedPreferencesOptions();
    }

    private void setupWidgetsFromLayout() {

        jenkinsMonitorLayout = (LinearLayout) activity.findViewById(R.id.layout);
    }

    private void setupSharedPreferencesOptions() {
        sharedPreferencesManager = new SharedPreferencesManager(activity);

        showCommitUser = sharedPreferencesManager.loadBoolean(activity.getString(R.string.monitor_show_commit_user_key), DEFAULT_SHOW_COMMIT_INFO_VALUE);
        showCommitMessage = sharedPreferencesManager.loadBoolean(activity.getString(R.string.monitor_show_commit_message_key), DEFAULT_SHOW_COMMIT_INFO_VALUE);
    }

    public void displayMonitor() {

        removeAllViewsFromGridLayout();
        addJenkinsJobsToJenkinsMonitorLayout();
    }

    public void removeAllViewsFromGridLayout() {

        jenkinsMonitorLayout.removeAllViews();
    }

    private void addJenkinsJobsToJenkinsMonitorLayout() {

        int noOfColumns = noOfJobsInJenkinsList() >= MIN_JOBS_FOR_MULIPLE_COLUMNS ? TWO_COLUMNS : ONE_COLUMN;

        jenkinsMonitorLayout.setWeightSum(noOfColumns);

        List<LinearLayout> monitorColumns = new ArrayList<LinearLayout>();

        setupMonitorColumnsAndAddToMonitor(noOfColumns, monitorColumns);

        int indexOfLastUsedColumn = -1;
        for (Jenkins jenkins : jenkinsList) {

            for (JenkinsJob job : jenkins.getJobs()) {

                int indexOfColumnToUse = getIndexOfColumnToUse(monitorColumns, indexOfLastUsedColumn);

                LinearLayout jobLayout = setupJenkinsJobLayout(job);
                addJenkinsJobToJenkinsMonitorLayout(monitorColumns, jobLayout, indexOfColumnToUse);

                indexOfLastUsedColumn = indexOfColumnToUse;
            }
        }
    }

    private int noOfJobsInJenkinsList() {

        int noOfJobs = 0;
        for (Jenkins jenkins : jenkinsList)
            noOfJobs += jenkins.getJobs().size();

        return noOfJobs;
    }

    private void setupMonitorColumnsAndAddToMonitor(int noOfColumns, List<LinearLayout> monitorColumns) {

        for (int i = 0; i < noOfColumns; i++) {

            LinearLayout monitorColumn = new LinearLayout(activity);

            boolean isFirstColumn = i == 0;
            setupMonitorColumn(monitorColumn, !isFirstColumn);

            jenkinsMonitorLayout.addView(monitorColumn);

            monitorColumns.add(monitorColumn);
        }
    }

    private void setupMonitorColumn(LinearLayout monitorColumn, boolean addMargin) {

        monitorColumn.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams columnLayoutParams = new LinearLayout.LayoutParams(COLUMN_WIDTH, ViewGroup.LayoutParams.WRAP_CONTENT, COLUMN_WEIGHT);

        if (addMargin)
            columnLayoutParams.setMargins(COLUMN_MARGIN_LEFT, COLUMN_MARGIN_TOP, COLUMN_MARGIN_RIGHT, COLUMN_MARGIN_BOTTOM);

        monitorColumn.setLayoutParams(columnLayoutParams);
    }

    private int getIndexOfColumnToUse(List<LinearLayout> monitorColumns, int indexOfLastUsedColumn) {

        int indexOfLastColumn = monitorColumns.size() - 1;
        int indexOfNextColumn = indexOfLastUsedColumn + 1;
        int indexOfFirstColumn = 0;

        boolean isThisTheLastColumn = indexOfLastUsedColumn == indexOfLastColumn;

        return isThisTheLastColumn ? indexOfFirstColumn : indexOfNextColumn;
    }

    private LinearLayout setupJenkinsJobLayout(JenkinsJob job) {

        LinearLayout jobLayout = setupJobLayout(job);

        setupJobTextViewsOntoLayout(job, jobLayout);
        setupAvatarsOntoLayout(job, jobLayout);
        setupBuildingStateOntoLayout(job, jobLayout);

        return jobLayout;
    }

    private LinearLayout setupJobLayout(JenkinsJob job) {

        LinearLayout linearLayout = new LinearLayout(activity);
        linearLayout.setOrientation(JOB_LAYOUT_ORIENTATION);
        linearLayout.setLayoutParams(getJobLayoutParams());
        linearLayout.setBackgroundColor(getColourStatusOfJenkinJob(job));

        return linearLayout;
    }

    private LinearLayout.LayoutParams getJobLayoutParams() {

        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearLayoutParams.setMargins(JOB_LAYOUT_LEFT_MARGIN, JOB_LAYOUT_TOP_MARGIN, JOB_LAYOUT_RIGHT_MARGIN, JOB_LAYOUT_BOTTOM_MARGIN);

        return linearLayoutParams;
    }

    private int getColourStatusOfJenkinJob(JenkinsJob job) {

        int buildColour = DEFAULT_COLOUR;

        boolean hasLastBuild = job.getLastBuild() != null;

        if (hasLastBuild)
            buildColour = getColourStatusOfJenkinsBuild(job.getLastBuild());

        return buildColour;
    }

    private int getColourStatusOfJenkinsBuild(JenkinsBuild jenkinsBuild) {

        int buildColour = DEFAULT_COLOUR;

        boolean hasBuildStatus = jenkinsBuild.getBuildStatus() != null;

        if (jenkinsBuild.getBuilding()) {

            buildColour = Color.parseColor(BUILDING_COLOUR);

        } else if (hasBuildStatus) {

            boolean buildSucceeded = jenkinsBuild.getBuildStatus().equals(SUCCESS_BUILD_STATUS);
            boolean buildFailed = jenkinsBuild.getBuildStatus().equals(FAILURE_BUILD_STATE);

            if (buildSucceeded) {

                buildColour = Color.parseColor(SUCCESS_COLOUR);

            } else if (buildFailed) {

                buildColour = Color.parseColor(FAILURE_COLOUR);
            }
        }

        return buildColour;
    }

    private void setupJobTextViewsOntoLayout(JenkinsJob job, LinearLayout jobLayout) {

        String jobName = job.getName();
        String commitUser = null;
        String commitMessage = null;

        if (job.getLastBuild() != null) {

            int buildNumber = job.getLastBuild().getBuildNumber();
            if (showCommitUser) commitUser = job.getLastBuild().getCommitUser();
            if (showCommitMessage) commitMessage = job.getLastBuild().getCommitMessage();
            if (buildNumber > 0) jobName += BUILD_NUMBER_PREFIX + job.getLastBuild().getBuildNumber();
        }

        addTextViewToLayoutForStringIfNotNull(jobName, jobLayout, JOB_NAME_TEXT_SIZE);
        addTextViewToLayoutForStringIfNotNull(commitUser, jobLayout, JOB_COMMIT_INFO_TEXT_SIZE);
        addTextViewToLayoutForStringIfNotNull(commitMessage, jobLayout, JOB_COMMIT_INFO_TEXT_SIZE);
    }

    private void addTextViewToLayoutForStringIfNotNull(String string, LinearLayout jobLayout, float textSize) {

        if (string != null) createTextViewForJobInfoAndAddToLayout(string, jobLayout, textSize);
    }

    private void createTextViewForJobInfoAndAddToLayout(String text, LinearLayout jobLayout, float textSize) {

        TextView jobInfoTextView = new TextView(activity);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        jobInfoTextView.setLayoutParams(layoutParams);
        jobInfoTextView.setGravity(Gravity.CENTER);
        jobInfoTextView.setText(text);
        jobInfoTextView.setTextSize(textSize);

        jobInfoTextView.setTextColor(Color.WHITE);

        jobLayout.addView(jobInfoTextView);
    }

    private void setupAvatarsOntoLayout(JenkinsJob job, LinearLayout jobLayout) {

        boolean hasLastBuild = job.getLastBuild() != null;
        if (hasLastBuild) {

            String commitMessage = job.getLastBuild().getCommitMessage();

            boolean hasCommitMessage = commitMessage != null;
            if (hasCommitMessage) {

                String commitInfoComponents = getCommitInfoFromCommitMessage(commitMessage);

                String[] initialsArray = getInitialsFromCommitInfo(commitInfoComponents);

                int minimumInitialsRequired = 1;
                if (initialsArray.length >= minimumInitialsRequired) {

                    setupAvatarImageViewsOnLayout(jobLayout, initialsArray);
                }
            }
        }
    }

    private String getCommitInfoFromCommitMessage(String commitMessage) {

        Pattern pattern = Pattern.compile(COMMIT_MESSAGE_EXTRACTION_PATTERN);
        Matcher matcher = pattern.matcher(commitMessage);

        String commitMessageComponents = "";

        if (matcher.find()) {

            commitMessageComponents = matcher.group(COMMIT_MESSAGE_COMPONENT_INDEX);
        }

        return commitMessageComponents;
    }

    private String[] getInitialsFromCommitInfo(String commitInfoComponents) {

        String[] arrayOfCommitMessageComponents = commitInfoComponents.split(COMMIT_MESSAGE_COMPONENT_DELIMETER);

        int indexOfInitialsComponent = arrayOfCommitMessageComponents.length - 1;
        String initialsComponents = arrayOfCommitMessageComponents[indexOfInitialsComponent];

        return initialsComponents.split(INITIALS_DELIMETER);
    }

    private void setupAvatarImageViewsOnLayout(LinearLayout jobLayout, String[] initialsArray) {

        List<ImageView> avatarImageViews = new ArrayList<ImageView>();

        createSmartImageViewsForInitials(initialsArray, avatarImageViews);

        addAvatarImageViewsToLayout(avatarImageViews, jobLayout);
    }

    private void createSmartImageViewsForInitials(String[] initialsArray, List<ImageView> avatarImageViews) {

        for (String initials : initialsArray) {

            String email = getGravatarEmailFromInitials(initials);

            if (!email.equals(GRAVATAR_EMAIL_DEFAULT_VALUE)) {

                try {

                    String gravatarImageURL = GomoGravatar.getGravatarImageURLWithSize(email, AVATAR_IMAGE_SIZE);

                    ImageView avatarImageView = createImageViewForUrl(gravatarImageURL);

                    avatarImageViews.add(avatarImageView);

                } catch (Exception e) {
                }
            }
        }
    }

    private String getGravatarEmailFromInitials(String initials) {

        String avatarKey = activity.getString(R.string.avatar_key_prefix) + initials;
        return sharedPreferencesManager.loadString(avatarKey, GRAVATAR_EMAIL_DEFAULT_VALUE);
    }

    private ImageView createImageViewForUrl(String url) {

        ImageView avatarImageView = new ImageView(activity);

        UrlImageViewHelper.setUrlDrawable(avatarImageView, url, null, AVATAR_IMAGE_CACHE_DURATION_MS);

        LinearLayout.LayoutParams imageViewLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageViewLayoutParams.setMargins(AVATAR_IMAGE_VIEW_MARGIN, AVATAR_IMAGE_VIEW_MARGIN, AVATAR_IMAGE_VIEW_MARGIN, AVATAR_IMAGE_VIEW_MARGIN);

        avatarImageView.setLayoutParams(imageViewLayoutParams);

        return avatarImageView;
    }

    private void addAvatarImageViewsToLayout(List<ImageView> avatarImageViews, LinearLayout jobLayout) {

        int minimumImageViewsRequired = 1;
        if (avatarImageViews.size() >= minimumImageViewsRequired) {

            LinearLayout avatarLayout = new LinearLayout(activity);
            avatarLayout.setOrientation(LinearLayout.HORIZONTAL);
            avatarLayout.setGravity(Gravity.CENTER);

            for (ImageView avatarImageView : avatarImageViews) {

                avatarLayout.addView(avatarImageView);
            }

            jobLayout.addView(avatarLayout);
        }
    }

    private void setupBuildingStateOntoLayout(JenkinsJob job, LinearLayout jobLayout) {

        boolean hasLastBuild = job.getLastBuild() != null;

        if (hasLastBuild && job.getLastBuild().getBuilding()) {

            ProgressBar progressBar = new ProgressBar(activity);

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(15, 15);
            layoutParams.gravity = Gravity.CENTER;
            progressBar.setLayoutParams(layoutParams);

            jobLayout.addView(progressBar);
        }
    }

    private void addJenkinsJobToJenkinsMonitorLayout(List<LinearLayout> monitorColumns, LinearLayout jobLayout, int indexOfColumnToUse) {

        LinearLayout monitorColumnToUse = monitorColumns.get(indexOfColumnToUse);
        monitorColumnToUse.addView(jobLayout);
    }
}