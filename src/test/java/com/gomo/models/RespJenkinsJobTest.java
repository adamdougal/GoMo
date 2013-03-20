package com.gomo.models;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class RespJenkinsJobTest {

    private static final String SAMPLE_LAST_BUILD_URL = "url";
    private static final String SAMPLE_BUILD_NAME = "name";
    private static final String SAMPLE_SET_BUILD_NAME = "name";
    private RespJenkinsJob job;

    @Before
    public void setUp() throws Exception {

        job = new RespJenkinsJob();


    }

    @Test
    public void shouldSetLastBuildOnRespJenkinsObj() {

        RespJenkinsJob.LastBuild lastBuild = new RespJenkinsJob.LastBuild();

        job.setLastBuild(lastBuild);

        assertEquals(lastBuild, job.getLastBuild());
    }

    @Test
    public void shouldSetLastBuildUrlOnRespJenkinsObj() {

        RespJenkinsJob.LastBuild lastBuild = new RespJenkinsJob.LastBuild();
        lastBuild.setUrl(SAMPLE_LAST_BUILD_URL);

        job.setLastBuild(lastBuild);

        assertEquals(SAMPLE_LAST_BUILD_URL, job.getLastBuild().getUrl());
    }

    @Test
    public void shouldSetDisplayNameOnRespJenkinsObj() throws Exception {

        job.setDisplayName(SAMPLE_SET_BUILD_NAME);

        assertEquals(SAMPLE_BUILD_NAME, job.getDisplayName());
    }

}
