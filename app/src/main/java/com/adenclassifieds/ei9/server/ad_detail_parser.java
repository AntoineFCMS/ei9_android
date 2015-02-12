package com.adenclassifieds.ei9.server;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import com.adenclassifieds.ei9.R;
import com.adenclassifieds.ei9.business.Program;
import com.adenclassifieds.ei9.utils.ParserHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * Created by antoinegaltier on 11/02/15.
 */
public class ad_detail_parser extends AsyncTask<Void, Integer, Boolean> {

    private String ref;
    private String url;
    private Context ctx;

    private final String TAG_PROGRAM = "program";
    private final String TAG_TYPE_BIEN = "estateType";
    private final String TAG_MODIFICATION_DATE = "modificationDate";
    private final String TAG_REF = "id";
    private final String TAG_IMMEDIATE_DELIVERY = "immediateDelivery";
    private final String TAG_NAME= "name";
    private final String TAG_CLASSIFIEDS= "classifieds";
    private final String TAG_CLASSIFIED= "classified";
    private final String TAG_SURFACE_UNIT= "surfaceUnit";
    private final String TAG_CREATION_DATE= "creationDate";
    private final String TAG_TYPE_LOGEMENT= "goodType";
    private final String TAG_MIN_AMOUNT= "amountMin";
    private final String TAG_PHOTO= "photo";
    private final String TAG_PHOTOS= "photos";
    private final String TAG_URL= "url";
    private final String TAG_NBROOMS= "nbRooms";
    private final String TAG_DESCRIPTION= "description";
    private final String TAG_DESCRIPTION_PROMOTER= "descriptionPromoter";
    private final String TAG_LAWS= "investmentLaws";
    private final String TAG_LOCALISATION= "localisation";
    private final String TAG_ADDRESS= "address";
    private final String TAG_CP= "postalCode";
    private final String TAG_CITY= "cityLabel";
    private final String TAG_DPT_CODE= "departmentCode";
    private final String TAG_DPT= "departmentLabel";
    private final String TAG_COUNTRY= "countryLabel";
    private final String TAG_REGION= "regionLabel";
    private final String TAG_LONGITUDE= "longitudeCity";
    private final String TAG_LATITUDE= "latitudeCity";
    private final String TAG_OPTIONS= "options";
    private final String TAG_OPTION= "option";
    private final String TAG_UNDER_CONSTRUCTION= "underConstruction";

    private Program program;

    public ad_detail_parser(Context ctx, String ref) {
        this.ctx = ctx;
        this.ref = ref;
        url = ctx.getString(R.string.ad_url)+ref;
    }


    @Override
    protected void onPostExecute(Boolean result) {
        if (result) {

        }
    }

    @Override
    protected Boolean doInBackground(Void... params) {

        XmlPullParserFactory xmlFactoryObject = null;
        XmlPullParser myparser = null;
        InputStream is = null;

        try {
            xmlFactoryObject = XmlPullParserFactory.newInstance();
            myparser = xmlFactoryObject.newPullParser();
            myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);

            try {
                is = ParserHelper.getInputStreamFromUrl(url);
                myparser.setInput(is, null);
                parseXML(myparser);
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }


        return true;
    }

    private void parseXML(XmlPullParser myParser) {
        int event;
        String text=null;
        try {
            event = myParser.getEventType();
            program = new Program();
            while (event != XmlPullParser.END_DOCUMENT) {
                String name=myParser.getName();
                switch (event){
                    case XmlPullParser.START_TAG:
                        break;
                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if(name.equals(TAG_NAME)){
                            program.setName(text);
                        }
//                        else if(name.equals("humidity")){
//                            humidity = myParser.getAttributeValue(null,"value");
//                        }
//                        else if(name.equals("pressure")){
//                            pressure = myParser.getAttributeValue(null,"value");
//                        }
//                        else if(name.equals("temperature")){
//                            temperature = myParser.getAttributeValue(null,"value");
//                        }
//                        else{
//                        }
                        break;
                }
                event = myParser.next();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("NewApi")
    public static void launchParsing(Context ctx, String ref) {
        AsyncTask<Void, Integer, Boolean> asynctask = new ad_detail_parser(ctx, ref);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB_MR1) {
            asynctask.execute();
        } else {
            asynctask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }
}
