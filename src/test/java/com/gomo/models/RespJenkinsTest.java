package com.gomo.models;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class RespJenkinsTest {

    private static final String SAMPLE_JOB_URL = "jobUrl";
    private static final String SAMPLE_JOB_NAME = "jobName";
    private static final int GET_JOB_POSITION_ZERO = 0;
    private RespJenkins.Jobs job;
    private RespJenkins jenkins;

    @Before
    public void setUp() throws Exception {

        job = new RespJenkins.Jobs();
        jenkins = new RespJenkins();

    }

    @Test
    public void shouldSetJobsOnRespJenkinsObj() throws Exception {

        List<RespJenkins.Jobs> jenkinsJobs = new ArrayList<RespJenkins.Jobs>();
        jenkinsJobs.add(job);

        jenkins.setJobs(jenkinsJobs);

        assertEquals(jenkinsJobs, jenkins.getJobs());

    }

    @Test
    public void shouldSetJobsUrlOnRespJenkinsObj() throws Exception {

        job.setUrl(SAMPLE_JOB_URL);
        List<RespJenkins.Jobs> jenkinsJobs = new ArrayList<RespJenkins.Jobs>();
        jenkinsJobs.add(job);

        RespJenkins jenkins = new RespJenkins();
        jenkins.setJobs(jenkinsJobs);

        assertEquals(SAMPLE_JOB_URL, jenkins.getJobs().get(GET_JOB_POSITION_ZERO).getUrl());

    }

    @Test
    public void shouldSetJobsNameOnRespJenkinsObj() throws Exception {

        job.setName(SAMPLE_JOB_NAME);
        List<RespJenkins.Jobs> jenkinsJobs = new ArrayList<RespJenkins.Jobs>();
        jenkinsJobs.add(job);

        RespJenkins jenkins = new RespJenkins();
        jenkins.setJobs(jenkinsJobs);

        assertEquals(SAMPLE_JOB_NAME, jenkins.getJobs().get(GET_JOB_POSITION_ZERO).getName());

    }
}
