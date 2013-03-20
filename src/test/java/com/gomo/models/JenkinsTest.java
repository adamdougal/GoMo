package com.gomo.models;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class JenkinsTest {

    @Test
    public void shouldSetJobsOnJenkinsObj() {
        JenkinsJob job = new JenkinsJob();
        List<JenkinsJob> jobs = new ArrayList<JenkinsJob>();
        jobs.add(job);

        Jenkins jenkins = new Jenkins();
        jenkins.setJobs(jobs);

        assertEquals(jobs, jenkins.getJobs());
    }


}
