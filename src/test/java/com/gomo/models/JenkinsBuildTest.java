package com.gomo.models;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class JenkinsBuildTest {

    private static final String SAMPLE_BUILD_NAME = "app1";
    private static final String SAMPLE_BUILD_STATUS = "fail";
    private static final int SAMPLE_BUILD_NUMBER = 28;
    private static final String SAMPLE_COMMIT_MESSAGE = "ABC";
    private static final String SAMPLE_COMMIT_USER = "GomoBot#1";
    private static final boolean SAMPLE_BUILDING_STATE = false;
    private JenkinsBuild build;

    @Before
    public void setUp() throws Exception {

        build = new JenkinsBuild();

    }

    @Test
    public void shouldSetFullDisplayNameOnJenkinsBuildObj() {

        build.setFullDisplayName(SAMPLE_BUILD_NAME);

        assertEquals(SAMPLE_BUILD_NAME, build.getFullDisplayName());
    }

    @Test
    public void shouldSetBuildStatusOnJenkinsBuildObj() {

        build.setBuildStatus(SAMPLE_BUILD_STATUS);

        assertEquals(SAMPLE_BUILD_STATUS, build.getBuildStatus());
    }

    @Test
    public void shouldSetNumberOnJenkinsBuildObj() {

        build.setBuildNumber(SAMPLE_BUILD_NUMBER);

        assertEquals(SAMPLE_BUILD_NUMBER, build.getBuildNumber());
    }

    @Test
    public void shouldSetCommitMessage() throws Exception {

        build.setCommitMessage(SAMPLE_COMMIT_MESSAGE);

        assertEquals(SAMPLE_COMMIT_MESSAGE, build.getCommitMessage());
    }


    @Test
    public void shouldSetCommitUser() throws Exception {

        build.setCommitUser(SAMPLE_COMMIT_USER);

        assertEquals(SAMPLE_COMMIT_USER, build.getCommitUser());
    }

    @Test
    public void shouldSetBuilding() throws Exception {


        build.setBuilding(SAMPLE_BUILDING_STATE);

        assertEquals(SAMPLE_BUILDING_STATE, build.getBuilding());

    }

}
