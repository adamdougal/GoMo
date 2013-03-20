package com.gomo.utilities;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GomoGravatar {

    public static final String ALGORITHM_TYPE = "MD5";
    private static final String BASE_GRAVATAR_URL = "http://www.gravatar.com/avatar/";
    private static final String SIZE_PARAM = "?s=";
    private static final String FILE_FORMAT = ".jpg";

    public static String getGravatarImageURLWithSize(String email, int size) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        email = email.trim().toLowerCase();

        byte[] hashedEmail = convertEmailToHash(email);

        String hashedEmailString = convertEmailHashToString(hashedEmail);

        return BASE_GRAVATAR_URL + hashedEmailString + SIZE_PARAM + size + FILE_FORMAT;
    }

    private static byte[] convertEmailToHash(String email) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        MessageDigest messageDigest = MessageDigest.getInstance(ALGORITHM_TYPE);
        return messageDigest.digest(email.getBytes("CP1252"));
    }

    private static String convertEmailHashToString(byte[] hashedEmail) {

        StringBuffer stringBuffer = new StringBuffer();

        for (int i = 0; i < hashedEmail.length; i++) {

            String emailHash = Integer.toHexString((hashedEmail[i] & 0xFF) | 0x100).substring(1, 3);
            stringBuffer.append(emailHash);
        }

        return stringBuffer.toString();
    }
}
