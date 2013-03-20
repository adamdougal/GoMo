package com.gomo;

import com.gomo.models.Jenkins;
import com.gomo.models.JenkinsBuild;
import com.gomo.models.JenkinsJob;
import com.gomo.utilities.GomoJsonParser;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestUtilities {

    private static final String UMV_JOB_NAME = "umv";
    private static final String UMV_JOB_STATUS = "FAILURE";
    private static final String UMV_COMMIT_USER = "thabtis";
    private static final String UMV_COMMIT_MESSAGE = "Fixed some stuff";
    private static final int UMV_LAST_BUILD_NUMBER = 52;
    private static final int UMV_JOB_INDEX_POSITION = 1;

    public static final String JENKINS_FILE_PATH = "file://" + System.getProperty("user.dir") + "/src/test/testfiles/jenkins.json";
    public static final String JENKINS_JOB_FILE_PATH = "file://" + System.getProperty("user.dir") + "/src/test/testfiles/job.json";
    public static final String JENKINS_JOB_NO_LAST_BUILD_FILE_PATH = "file://" + System.getProperty("user.dir") + "/src/test/testfiles/jobNoLastBuild.json";
    public static final String JENKINS_BUILD_FILE_PATH = "file://" + System.getProperty("user.dir") + "/src/test/testfiles/build.json";
    public static final String JENKINS_BUILD_NO_RESULT_FILE_PATH = "file://" + System.getProperty("user.dir") + "/src/test/testfiles/buildNoResult.json";
    public static final String JENKINS_BUILD_NO_COMMIT_FILE_PATH = "file://" + System.getProperty("user.dir") + "/src/test/testfiles/buildNoCommit.json";
    public static final String JENKINS_MALFORMED_FILE_PATH = "file://" + System.getProperty("user.dir") + "/src/test/testfiles/MalFormedJson.json";
    public static final String JSON_TXT_FILE_PATH = "file://" + System.getProperty("user.dir") + "/src/test/testfiles/build.txt";

    public static Jenkins getTestJenkins() throws Exception {

        GomoJsonParser mockJenkinsParser = getMockJenkinsGomoJsonParser();

        Jenkins jenkins = mockJenkinsParser.getJenkinsFromPath(JENKINS_FILE_PATH);

        setUpUmvJenkinsJob(jenkins);

        return jenkins;

    }

    private static void setUpUmvJenkinsJob(Jenkins jenkins) {

        JenkinsJob umvJob = jenkins.getJobs().get(UMV_JOB_INDEX_POSITION);

        umvJob.setName(UMV_JOB_NAME);

        JenkinsBuild lastBuild = umvJob.getLastBuild();

        lastBuild.setBuildStatus(UMV_JOB_STATUS);

        lastBuild.setBuildNumber(UMV_LAST_BUILD_NUMBER);

        lastBuild.setCommitUser(UMV_COMMIT_USER);

        lastBuild.setCommitMessage(UMV_COMMIT_MESSAGE);

        umvJob.setLastBuild(lastBuild);
    }

    public static GomoJsonParser getMockJenkinsGomoJsonParser() throws Exception {

        GomoJsonParser mockJenkinsParser = mock(GomoJsonParser.class);

        when(mockJenkinsParser.getJenkinsApiPathFromURL(anyString())).then(returnsFirstArg());

        JenkinsJob jenkinsJob = getMockJobGomoJsonParser().getJenkinsJobFromPath(JENKINS_JOB_FILE_PATH);
        JenkinsJob jenkinsJob2 = getMockJobGomoJsonParser().getJenkinsJobFromPath(JENKINS_JOB_FILE_PATH);

        when(mockJenkinsParser.getJenkinsJobFromPath(anyString())).thenReturn(jenkinsJob).thenReturn(jenkinsJob2);
        when(mockJenkinsParser.getJenkinsFromPath(anyString())).thenCallRealMethod();
        when(mockJenkinsParser.getRespJenkinsFromPath(anyString())).thenCallRealMethod();
        when(mockJenkinsParser.shouldGetJob(anyString())).thenReturn(true);

        return mockJenkinsParser;
    }

    public static GomoJsonParser getMockJobGomoJsonParser() throws Exception {

        GomoJsonParser mockJobParser = mock(GomoJsonParser.class);

        when(mockJobParser.getJenkinsApiPathFromURL(anyString())).then(returnsFirstArg());

        JenkinsBuild jenkinsBuild = getMockBuildGomoJsonParser().getJenkinsBuildFromPath(JENKINS_BUILD_FILE_PATH);

        when(mockJobParser.getJenkinsBuildFromPath(anyString())).thenReturn(jenkinsBuild);
        when(mockJobParser.getJenkinsJobFromPath(anyString())).thenCallRealMethod();

        return mockJobParser;
    }

    public static GomoJsonParser getMockBuildGomoJsonParser() throws Exception {

        GomoJsonParser mockBuildParser = mock(GomoJsonParser.class);

        when(mockBuildParser.getJenkinsApiPathFromURL(anyString())).then(returnsFirstArg());
        when(mockBuildParser.getJenkinsBuildFromPath(anyString())).thenCallRealMethod();

        return mockBuildParser;
    }
}
