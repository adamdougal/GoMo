package com.gomo.utilities;

import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class GomoHttpClientTest {

    private static final String SAMPLE_VALID_JOB_URL = "http://10.65.87.90:8080/api/json";
    private static final String SAMPLE_NON_VALID_JOB_URL = "http://www.sky.com/";
    private static final String SAMPLE_INVALID_URL = "asdfhgfdsad";

    @Test
    public void givenValidJsonUrl_ShouldReturnFile() throws Exception {

        File file = GomoHttpClient.getFileFromUrl(SAMPLE_VALID_JOB_URL);
        assertTrue(file instanceof File);

    }

    @Test(expected = JSONException.class)
    public void givenValidNonJsonUrl_ShouldThrowIOException() throws Exception {

        GomoHttpClient.getFileFromUrl(SAMPLE_NON_VALID_JOB_URL);

    }

    @Test(expected = IOException.class)
    public void givenInvalidUrl_ShouldThrowIOException() throws Exception {

        GomoHttpClient.getFileFromUrl(SAMPLE_INVALID_URL);

    }

}
