package com.adenclassifieds.ei9.server;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;

import com.adenclassifieds.ei9.R;
import com.adenclassifieds.ei9.activity.Contact_form;
import com.adenclassifieds.ei9.business.ContactForm;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;

/**
 * Created by Antoine GALTIER on 23/02/15.
 */
public class contact_promoter_parser extends AsyncTask<Void, Integer, Boolean> {

    private ContactForm form;
    private WeakReference<Contact_form> contactActivity = null;
    String line;

    private final static String TAG_ID = "id";
    private final static String TAG_ITEM_ID = "itemClassifiedId";
    private final static String TAG_AGENCY_NAME = "nomAgence";
    private final static String TAG_MAIL = "email";
    private final static String TAG_TEL = "telephone";
    private final static String TAG_MESSAGE = "message";
    private final static String TAG_STAT_CLASSIFIED_ID = "statClassifiedId";
    private final static String TAG_STAT_CUSTOMER_ID = "statCustomerId";
    private final static String TAG_COPIE_AU_CLIENT = "copieAuClient";
    private final static String TAG_ORIGINE = "origine";
    private final static String TAG_CIVILITE = "civilite";
    private final static String TAG_NAME = "nom";
    private final static String TAG_FIRST_NAME = "prenom";
    private final static String TAG_ADRESS = "adresse";
    private final static String TAG_CP = "codePostal";
    private final static String TAG_TOWN = "ville";

    public contact_promoter_parser(Contact_form pActivity, ContactForm form) {
        contactActivity = new WeakReference<Contact_form>(pActivity);
        this.form = form;
    }


    @Override
    protected void onPostExecute(Boolean result) {
        if (result) {
            if (contactActivity != null)
                contactActivity.get().display_server_message(line);
        }
    }

    @Override
    protected Boolean doInBackground(Void... params) {

        try {
            HttpClient httpclient = new DefaultHttpClient();

            HttpPost httpPost = new HttpPost(contactActivity.get().getResources().getString(R.string.contact_form_url));

            String json;

            JSONObject jsonObject = new JSONObject();

            jsonObject.accumulate(TAG_ID, form.getId());
            jsonObject.accumulate(TAG_MAIL, form.getEmail());
            jsonObject.accumulate(TAG_MESSAGE, form.getMessage());
            jsonObject.accumulate(TAG_CIVILITE, form.getCivilite());
            jsonObject.accumulate(TAG_NAME, form.getNom());
            jsonObject.accumulate(TAG_FIRST_NAME, form.getPrenom());
            jsonObject.accumulate(TAG_ADRESS, form.getAdresse());
            jsonObject.accumulate(TAG_CP, form.getCodePostal());
            jsonObject.accumulate(TAG_TOWN, form.getVille());

            jsonObject.accumulate(TAG_TEL, form.getTelephone());

            json = jsonObject.toString();

            StringEntity se = null;
            try {
                se = new StringEntity(json);

                httpPost.setEntity(se);

                httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");

                HttpResponse httpResponse = httpclient.execute(httpPost);
                final int code_http = httpResponse.getStatusLine().getStatusCode();



                InputStream is = httpResponse.getEntity().getContent();

                StringBuilder sb = new StringBuilder();

                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);


                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                is.close();

                if (code_http == 200 && (line == null || line.isEmpty()))
                    line = contactActivity.get().getResources().getString(R.string.server_message_ok);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return true;
    }


    @SuppressLint("NewApi")
    public static void launchParsing(Contact_form pActivity, ContactForm form) {
        AsyncTask<Void, Integer, Boolean> asynctask = new contact_promoter_parser(pActivity, form);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB_MR1) {
            asynctask.execute();
        } else {
            asynctask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }

}
