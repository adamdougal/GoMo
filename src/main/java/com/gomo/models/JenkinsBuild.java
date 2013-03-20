package com.gomo.models;

public class JenkinsBuild {

    private String fullDisplayName;
    private String buildStatus;
    private int buildNumber;
    private String commitMessage;
    private String commitUser;
    private boolean building;

    public JenkinsBuild() {
    }

    public JenkinsBuild(String fullDisplayName, boolean building, String buildStatus, int buildNumber) {
        this.fullDisplayName = fullDisplayName;
        this.building = building;
        this.buildStatus = buildStatus;
        this.buildNumber = buildNumber;
    }


    public JenkinsBuild(String fullDisplayName, boolean building, String buildStatus, int buildNumber, String commitMessage, String commitUser) {
        this.fullDisplayName = fullDisplayName;
        this.building = building;
        this.buildStatus = buildStatus;
        this.buildNumber = buildNumber;
        this.commitMessage = commitMessage;
        this.commitUser = commitUser;
    }

    public String getFullDisplayName() {
        return fullDisplayName;
    }

    public void setFullDisplayName(String fullDisplayName) {
        this.fullDisplayName = fullDisplayName;
    }

    public void setBuildStatus(String buildStatus) {
        this.buildStatus = buildStatus;
    }

    public String getBuildStatus() {
        return buildStatus;
    }

    public void setBuildNumber(int buildNumber) {
        this.buildNumber = buildNumber;
    }

    public int getBuildNumber() {
        return buildNumber;
    }

    public void setCommitMessage(String commitMessage) {
        this.commitMessage = commitMessage;
    }

    public String getCommitMessage() {
        return commitMessage;
    }

    public void setCommitUser(String commitUser) {
        this.commitUser = commitUser;
    }

    public String getCommitUser() {
        return commitUser;
    }

    public void setBuilding(boolean building) {
        this.building = building;
    }

    public boolean getBuilding() {
        return building;
    }
}
