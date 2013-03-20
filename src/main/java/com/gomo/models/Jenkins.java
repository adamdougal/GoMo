package com.gomo.models;

import java.util.List;

public class Jenkins {

    private List<JenkinsJob> jobs;

    public Jenkins() {
    }

    ;

    public Jenkins(List<JenkinsJob> jobs) {

        this.jobs = jobs;
    }

    public void setJobs(List<JenkinsJob> jobs) {
        this.jobs = jobs;
    }

    public List<JenkinsJob> getJobs() {
        return jobs;
    }
}
