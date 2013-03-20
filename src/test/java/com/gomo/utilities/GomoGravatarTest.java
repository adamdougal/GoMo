package com.gomo.utilities;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertEquals;


@RunWith(RobolectricTestRunner.class)
public class GomoGravatarTest {

    private GomoGravatar gomoGravatar = new GomoGravatar();


    @Test
    public void givenAMD5HASH_ShouldReturnGravatarURL() throws Exception {

        String email = "wunir@hotmail.com";
        int imageSize = 20;

        String gravatarImageURLWithSize = GomoGravatar.getGravatarImageURLWithSize(email, imageSize);

        String expectedUrl = "http://www.gravatar.com/avatar/b586889bf8dd44a85a647b2c9aab14c7?s=" + imageSize + ".jpg";

        assertEquals(expectedUrl, gravatarImageURLWithSize);
    }
}
