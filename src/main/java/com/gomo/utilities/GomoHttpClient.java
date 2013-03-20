package com.gomo.utilities;

import org.json.JSONException;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class GomoHttpClient {

    private static final String JSON_APPLICATION_CONTENT_TYPE = "application/json;charset=UTF-8";
    private static final String CONTENT_TYPE_UNKNOWN = "content/unknown";
    private static final String TEMPORARY_FILE_NAME = "temp-file";
    private static final String JSON_FILE_EXTENSION = ".json";

    public static File getFileFromUrl(String urlString) throws IOException, JSONException {

        URL url = new URL(urlString);
        URLConnection urlConnection = url.openConnection();

        if (!urlConnection.getContentType().equals(JSON_APPLICATION_CONTENT_TYPE) && !urlConnection.getContentType().equals(CONTENT_TYPE_UNKNOWN))
            throw new JSONException("Not a JSON file");

        InputStream inputStream = urlConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        File file = File.createTempFile(TEMPORARY_FILE_NAME, JSON_FILE_EXTENSION);

        FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

        String inputLine;
        while ((inputLine = bufferedReader.readLine()) != null)
            bufferedWriter.write(inputLine);

        bufferedReader.close();
        bufferedWriter.close();

        return file;
    }


}
