package com.gomo.utilities;

import org.json.JSONException;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class GomoHttpClient {

    private static final String JSON_APPLICATION_CONTENT_TYPE = "application/json;charset=UTF-8";
    private static final String CONTENT_TYPE_UNKNOWN = "content/unknown";
    private static final String TEMPORARY_FILE_NAME = "temp-file";
    private static final String JSON_FILE_EXTENSION = ".json";

    public static File getFileFromUrl(String urlString) throws IOException, JSONException {

        TrustManager[] trustAllCerts = new TrustManager[] {
                new X509TrustManager() {
                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                        @Override
                    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

                    }

                    @Override
                    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    }
                }
        };

        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

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
