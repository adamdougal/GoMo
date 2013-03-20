package com.gomo.models;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class JenkinsJobTest {

    private static final String SAMPLE_JOB_NAME = "name";

    private JenkinsJob job;

    @Before
    public void setUp() throws Exception {

        job = new JenkinsJob();
    }

    @Test
    public void shouldSetLastBuildOnJenkinsObj() {

        JenkinsBuild lastBuild = new JenkinsBuild();

        job.setLastBuild(lastBuild);

        assertEquals(lastBuild, job.getLastBuild());
    }

    @Test
    public void shouldSetNameOnJenkinsObj() throws Exception {

        job.setName(SAMPLE_JOB_NAME);

        assertEquals(SAMPLE_JOB_NAME, job.getName());
    }
}
