package com.gomo.models;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class RespJenkinsBuildTest {

    private static final String SAMPLE_BUILD_NAME = "app1";
    private static final String SAMPLE_BUILD_RESULT = "fail";
    private static final int SAMPLE_BUILD_NUMBER = 28;
    private static final String SAMPLE_COMMIT_MESSAGE = "Build test message";
    private static final String SAMPLE_COMMIT_USER = "GomoBot";
    private static final int GET_ITEM_POSITION_ZERO = 0;
    private static final boolean SAMPLE_BUILDING_STATE = false;
    private RespJenkinsBuild build;

    @Before
    public void setUp() throws Exception {

        build = new RespJenkinsBuild();

    }

    @Test
    public void shouldSetFullDisplayNameOnRespJenkinsBuildObj() {

        build.setFullDisplayName(SAMPLE_BUILD_NAME);

        assertEquals(SAMPLE_BUILD_NAME, build.getFullDisplayName());

    }

    @Test
    public void shouldSetResultOnRespJenkinsBuildObj() {

        build.setResult(SAMPLE_BUILD_RESULT);

        assertEquals(SAMPLE_BUILD_RESULT, build.getResult());
    }


    @Test
    public void shouldSetNumberOnRespJenkinsBuildObj() throws Exception {

        build.setNumber(SAMPLE_BUILD_NUMBER);

        assertEquals(SAMPLE_BUILD_NUMBER, build.getNumber());
    }

    @Test
    public void shouldSetBuildCommitMessage() throws Exception {

        RespJenkinsBuild.ChangeSet changeSet = new RespJenkinsBuild.ChangeSet();

        RespJenkinsBuild.ChangeSet.Items item = new RespJenkinsBuild.ChangeSet.Items();

        item.setMsg(SAMPLE_COMMIT_MESSAGE);

        List<RespJenkinsBuild.ChangeSet.Items> items = new ArrayList<RespJenkinsBuild.ChangeSet.Items>();

        items.add(item);

        changeSet.setItems(items);

        build.setChangeSet(changeSet);

        assertEquals(SAMPLE_COMMIT_MESSAGE, build.getChangeSet().getItems().get(GET_ITEM_POSITION_ZERO).getMsg());

    }


    @Test
    public void shouldSetBuildCommitUser() throws Exception {

        RespJenkinsBuild.ChangeSet changeSet = new RespJenkinsBuild.ChangeSet();

        RespJenkinsBuild.ChangeSet.Items item = new RespJenkinsBuild.ChangeSet.Items();

        item.setUser(SAMPLE_COMMIT_USER);

        List<RespJenkinsBuild.ChangeSet.Items> items = new ArrayList<RespJenkinsBuild.ChangeSet.Items>();

        items.add(item);

        changeSet.setItems(items);

        build.setChangeSet(changeSet);

        assertEquals(SAMPLE_COMMIT_USER, build.getChangeSet().getItems().get(GET_ITEM_POSITION_ZERO).getUser());

    }

    @Test
    public void shouldSetBuildingOnRespJenkinsObj() throws Exception {

        build.setBuilding(SAMPLE_BUILDING_STATE);

        assertEquals(SAMPLE_BUILDING_STATE, build.getBuilding());


    }
}
