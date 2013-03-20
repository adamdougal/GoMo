package com.gomo.models;

public class JenkinsJob {

    private String name;
    private JenkinsBuild lastBuild;

    public JenkinsJob() {

    }

    public JenkinsJob(String name, JenkinsBuild lastBuild) {
        this.name = name;
        this.lastBuild = lastBuild;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastBuild(JenkinsBuild lastBuild) {
        this.lastBuild = lastBuild;
    }

    public JenkinsBuild getLastBuild() {
        return lastBuild;
    }

}
