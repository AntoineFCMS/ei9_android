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

/**
 * Created by antoinegaltier on 11/02/15.
 */
public class ParserHelper {

    private static final int timeoutConnection = 3000;
    private static final int timeoutSocket = 5000;

    public static InputStream getInputStreamFromUrl(String url) throws IOException {
        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
        HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);

        DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
        HttpPost httpPost = new HttpPost(url);

        HttpResponse httpResponse = httpClient.execute(httpPost);
        HttpEntity httpEntity = httpResponse.getEntity();
        return httpEntity.getContent();
    }

}
