package com.ezqel.pagesourceviewer;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetWorkUtil {
    private static final String LOG_TAG = NetWorkUtil.class.getSimpleName();

    public static String getPage(String url){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        InputStream stream = null;
        StringBuilder builder = null;

        try{
            URL pageUrl = new URL(url);
            urlConnection = (HttpURLConnection) pageUrl.openConnection();

            stream = urlConnection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(stream));

            builder = new StringBuilder();
            String s;

            while ((s = reader.readLine()) != null){
                builder.append(s);
                builder.append("\n");
            }
            reader.close();

            Log.d(LOG_TAG, builder.toString());

        } catch (IOException e) {
            e.printStackTrace();
            return "Connection failed: The site could not be reached";
        } finally {
            try {
                if (stream != null){
                    stream.close();
                }

                if (urlConnection != null){
                    urlConnection.disconnect();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return builder.toString();
    }
}
