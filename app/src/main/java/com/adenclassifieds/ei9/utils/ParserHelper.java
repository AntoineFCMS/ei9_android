package com.adenclassifieds.ei9.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Antoine GALTIER on 11/02/15.
 */
public class ParserHelper {

    private static final int timeoutConnection = 2000;
    private static final int readTimeout = 1500;

    public static InputStream getInputStreamFromUrl(String my_url) throws IOException {
//        HttpParams httpParameters = new BasicHttpParams();
//        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
//        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
//
//        DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
//        HttpPost httpPost = new HttpPost(url);
//
//        HttpResponse httpResponse = httpClient.execute(httpPost);
//        HttpEntity httpEntity = httpResponse.getEntity();
//        return httpEntity.getContent();

        URL url = new URL(my_url);
        HttpURLConnection conn = (HttpURLConnection)
                url.openConnection();
        conn.setReadTimeout(readTimeout);
        conn.setConnectTimeout(timeoutConnection);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect();
        return conn.getInputStream();
    }

}
