package com.gomo.utilities;

import com.gomo.TestUtilities;
import com.gomo.activities.MonitorActivity;
import com.gomo.models.Jenkins;
import com.gomo.models.JenkinsBuild;
import com.gomo.models.JenkinsJob;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.IOException;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class GomoJsonParserTest {

    private static final String MONITOR_SHOW_COMMIT_USER_KEY = "MonitorShowCommitUser";
    private static final String MONITOR_SHOW_COMMIT_MESSAGE_KEY = "MonitorShowCommitMessage";
    private static final String SAMPLE_BUILD_NAME_WITH_BUILD_NUMBER = "gomo #28";
    private static final String SAMPLE_JOB_NAME = "gomo";
    private static final String SAMPLE_BUILD_STATUS_RESULT = "SUCCESS";
    private static final String SAMPLE_COMMIT_MESSAGE = "Added target folder to ignore file";
    private static final int SAMPLE_BUILD_NUMBER = 28;
    private static final String SAMPLE_COMMIT_USER = "dougala";
    private static final String JENKINS_URL_SUFFIX = "api/json";
    public GomoJsonParser mockBuildParser;
    public GomoJsonParser mockJobParser;
    public GomoJsonParser mockJenkinsParser;
    public GomoJsonParser parser;
    private SharedPreferencesManager sharedPreferencesManager;

    @Before
    public void initialize() throws Exception {

        mockBuildParser = TestUtilities.getMockBuildGomoJsonParser();
        mockJobParser = TestUtilities.getMockJobGomoJsonParser();
        mockJenkinsParser = TestUtilities.getMockJenkinsGomoJsonParser();

        MonitorActivity monitorActivity = new MonitorActivity();

        parser = new GomoJsonParser(monitorActivity);
        sharedPreferencesManager = new SharedPreferencesManager(monitorActivity);
    }

    /* JENKINS */

    @Test
    public void givenValidJenkinsJsonFile_ShouldReturnJenkins() throws Exception {

        assertTrue(mockJenkinsParser.getJenkinsFromPath(TestUtilities.JENKINS_FILE_PATH) instanceof Jenkins);

    }

    @Test
    public void givenValidJenkinsJsonFile_ShouldReturnJenkinsWithJobs() throws Exception {

        assertTrue(mockJenkinsParser.getJenkinsFromPath(TestUtilities.JENKINS_FILE_PATH).getJobs().get(0) instanceof JenkinsJob);

    }

    @Test
    public void givenValidJenkinsJsonFile_ShouldReturnJenkinsWithAllJobs() throws Exception {

        assertEquals(2, mockJenkinsParser.getJenkinsFromPath(TestUtilities.JENKINS_FILE_PATH).getJobs().size());

    }

    @Test
    public void givenValidJenkinsJsonFile_ShouldReturnJenkinsWithJobsContainingLastBuild() throws Exception {

        assertTrue(mockJenkinsParser.getJenkinsFromPath(TestUtilities.JENKINS_FILE_PATH).getJobs().get(0).getLastBuild() instanceof JenkinsBuild);

    }

    /* JENKINS JOB */

    @Test
    public void givenValidJenkinsJobJsonFile_ShouldReturnJenkinsJob() throws Exception {

        assertTrue(mockJobParser.getJenkinsJobFromPath(TestUtilities.JENKINS_JOB_FILE_PATH) instanceof JenkinsJob);

    }

    @Test
    public void givenValidJenkinsJobJsonFile_ShouldReturnJenkinsJobWithLastBuild() throws Exception {

        assertTrue(mockJobParser.getJenkinsJobFromPath(TestUtilities.JENKINS_JOB_FILE_PATH).getLastBuild() instanceof JenkinsBuild);
    }

    @Test
    public void givenValidJenkinsJobJsonFile_ShouldReturnJenkinsJobWithCorrectLastBuild() throws Exception {

        JenkinsBuild lastBuild = mockJobParser.getJenkinsJobFromPath(TestUtilities.JENKINS_JOB_FILE_PATH).getLastBuild();

        assertEquals(SAMPLE_BUILD_NAME_WITH_BUILD_NUMBER, lastBuild.getFullDisplayName());

    }

    @Test
    public void givenValidJenkinsJobJsonFile_ShouldReturnJenkinsJobWithCorrectName() throws Exception {

        JenkinsJob job = mockJobParser.getJenkinsJobFromPath(TestUtilities.JENKINS_JOB_FILE_PATH);

        assertEquals(SAMPLE_JOB_NAME, job.getName());

    }

    @Test
    public void givenValidJenkinsJobJsonFileWithNoLastBuild_ShouldSetNullOnLastBuild() throws Exception {

        JenkinsJob job = mockJobParser.getJenkinsJobFromPath(TestUtilities.JENKINS_JOB_NO_LAST_BUILD_FILE_PATH);

        assertNull(job.getLastBuild());

    }

    /* JENKINS BUILD */

    @Test
    public void givenValidJenkinsBuildJsonFile_ShouldReturnJenkinsBuild() throws Exception {

        assertTrue(mockBuildParser.getJenkinsBuildFromPath(TestUtilities.JENKINS_BUILD_FILE_PATH) instanceof JenkinsBuild);

    }

    @Test
    public void givenValidJenkinsBuildJsonFile_ShouldReturnJenkinsBuildName() throws Exception {

        JenkinsBuild build = mockBuildParser.getJenkinsBuildFromPath(TestUtilities.JENKINS_BUILD_FILE_PATH);

        assertEquals(SAMPLE_BUILD_NAME_WITH_BUILD_NUMBER, build.getFullDisplayName());

    }

    @Test
    public void givenValidJenkinsBuildJsonFile_ShouldReturnJenkinsBuilding() throws Exception {

        JenkinsBuild build = mockBuildParser.getJenkinsBuildFromPath(TestUtilities.JENKINS_BUILD_FILE_PATH);

        assertEquals(true, build.getBuilding());

    }

    @Test
    public void givenValidJenkinsBuildJsonFile_ShouldReturnJenkinsBuildStatus() throws Exception {

        JenkinsBuild build = mockBuildParser.getJenkinsBuildFromPath(TestUtilities.JENKINS_BUILD_FILE_PATH);

        assertEquals(SAMPLE_BUILD_STATUS_RESULT, build.getBuildStatus());

    }

    @Test
    public void givenValidJenkinsBuildJsonFile_ShouldReturnJenkinsBuildNumber() throws Exception {

        JenkinsBuild build = mockBuildParser.getJenkinsBuildFromPath(TestUtilities.JENKINS_BUILD_FILE_PATH);

        assertEquals(SAMPLE_BUILD_NUMBER, build.getBuildNumber());

    }

    @Test
    public void givenValidJenkinsBuildJsonFile_ShouldReturnJenkinsBuildCommitMessage() throws Exception {

        JenkinsBuild build = mockBuildParser.getJenkinsBuildFromPath(TestUtilities.JENKINS_BUILD_FILE_PATH);

        assertEquals(SAMPLE_COMMIT_MESSAGE, build.getCommitMessage());

    }

    @Test
    public void givenValidJenkinsBuildJsonFile_ShouldReturnJenkinsBuildCommitUser() throws Exception {

        JenkinsBuild build = mockBuildParser.getJenkinsBuildFromPath(TestUtilities.JENKINS_BUILD_FILE_PATH);

        assertEquals(SAMPLE_COMMIT_USER, build.getCommitUser());

    }

    @Test
    public void givenValidJenkinsBuildJsonFileWithNoCommitInfo_ShouldNotSetCommitUser() throws Exception {

        JenkinsBuild build = mockBuildParser.getJenkinsBuildFromPath(TestUtilities.JENKINS_BUILD_NO_COMMIT_FILE_PATH);

        assertEquals(null, build.getCommitUser());

    }

    @Test
    public void givenValidJenkinsBuildJsonFileWithNoCommitInfo_ShouldNotSetCommitMessage() throws Exception {

        JenkinsBuild build = mockBuildParser.getJenkinsBuildFromPath(TestUtilities.JENKINS_BUILD_NO_COMMIT_FILE_PATH);

        assertEquals(null, build.getCommitMessage());

    }

    /* JSON Utils */

    @Test
    public void givenString_ShouldReturnWithAppendWithJenkinsApiPath() throws Exception {

        String buildPath = parser.getJenkinsApiPathFromURL(TestUtilities.JENKINS_BUILD_FILE_PATH);

        assertEquals(TestUtilities.JENKINS_BUILD_FILE_PATH + JENKINS_URL_SUFFIX, buildPath);

    }

    @Test(expected = IOException.class)
    public void givenAPathToMalformedJson_ShouldThrowException() throws Exception {

        parser.getJenkinsFromPath(TestUtilities.JENKINS_MALFORMED_FILE_PATH);

    }

    @Test(expected = IOException.class)
    public void givenInvalidJsonFileExtension_ThenThrowJsonException() throws Exception {

        parser.getJenkinsFromPath(TestUtilities.JSON_TXT_FILE_PATH);

    }

}
