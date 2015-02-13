package com.adenclassifieds.ei9.server;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;

import com.adenclassifieds.ei9.Ad_details;
import com.adenclassifieds.ei9.R;
import com.adenclassifieds.ei9.business.Logement;
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
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by antoinegaltier on 11/02/15.
 */
public class ad_detail_parser extends AsyncTask<Void, Integer, Boolean> {

    private String ref;
    private String url;
    private WeakReference<Ad_details> mActivity = null;

    private final String TAG_PROGRAM = "program";
    private final String TAG_TYPE_BIEN = "estateType";
    private final String TAG_MODIFICATION_DATE = "modificationDate";
    private final String TAG_DELIVERY_DATE = "deliveryDate";
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
    private final String TAG_PROMOTER_NAME= "promoterName";
    private final String TAG_SURFACE_CERTIFICATION= "surfaceCertification";
    private final String TAG_RCS= "rcs";
    private final String TAG_FLOOR= "floor";

    private Program program;

    public ad_detail_parser(Ad_details pActivity, String ref) {
        mActivity = new WeakReference<Ad_details>(pActivity);
        this.ref = ref;
        url = mActivity.get().getString(R.string.ad_url)+ref;
    }


    @Override
    protected void onPostExecute(Boolean result) {
        if (result && mActivity.get() != null) {
            mActivity.get().setProgrammInformation(program);
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
        String name = null;
        try {
            event = myParser.getEventType();
            program = new Program();
            Logement logement = null;
            while (event != XmlPullParser.END_DOCUMENT) {
                name = myParser.getName();
                switch (event){
                    case XmlPullParser.START_TAG:
                        if (name.equals(TAG_CLASSIFIEDS)){
                            program.setLogements(new ArrayList<Logement>());
                        }
                        else if (name.equals(TAG_CLASSIFIED)){
                            logement = new Logement();
                            while (!(event == XmlPullParser.END_TAG && name.equals(TAG_CLASSIFIED))) {
                                switch (event){
                                    case XmlPullParser.TEXT:
                                        text = myParser.getText();
                                        break;
                                    case XmlPullParser.END_TAG:
                                        if(name.equals(TAG_SURFACE_UNIT)){
                                            logement.setSurface_unit(text);
                                        }
                                        else if(name.equals(TAG_SURFACE_CERTIFICATION)){
                                            logement.setSurface_certification(Integer.parseInt(text));
                                        }
                                        else if(name.equals(TAG_RCS)){
                                            logement.setRcs(text);
                                        }
                                        else if(name.equals(TAG_CREATION_DATE)){
                                            logement.setCreation_date(text);
                                        }
                                        else if(name.equals(TAG_TYPE_LOGEMENT)){
                                            logement.setAd_type(text);
                                        }
                                        else if(name.equals(TAG_FLOOR)){
                                            logement.setFloor(text);
                                        }
                                        else if(name.equals(TAG_REF)){
                                            logement.setRef(text);
                                        }
                                        else if(name.equals(TAG_MODIFICATION_DATE)){
                                            logement.setModification_date(text);
                                        }
                                        else if(name.equals(TAG_MIN_AMOUNT)){
                                            logement.setAmount_min(Float.parseFloat(text));
                                        }
                                        else if(name.equals(TAG_PHOTO)){
                                            if (logement.getPhotos_urls() == null){
                                                logement.setPhotos_urls(new ArrayList<String>());
                                            }
                                            logement.getPhotos_urls().add(myParser.getAttributeValue(null,TAG_URL));
                                        }
                                        else if(name.equals(TAG_NBROOMS)){
                                            logement.setNb_rooms(Integer.parseInt(text));
                                        }
                                        break;
                                }
                                event = myParser.next();
                                name = myParser.getName();
                            }
                            program.getLogements().add(logement);
                        }
                        break;
                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        if(name.equals(TAG_NAME)){
                            program.setName(text);
                        }
                        else if(name.equals(TAG_PROMOTER_NAME)){
                            program.setPromoter_name(text);
                        }
                        else if(name.equals(TAG_TYPE_BIEN)){
                            program.setType(text);
                        }
                        else if(name.equals(TAG_DELIVERY_DATE)){
                            program.setDelivery_date(text);
                        }
                        else if(name.equals(TAG_MODIFICATION_DATE)){
                            program.setModification_date(text);
                        }
                        else if(name.equals(TAG_REF)){
                            program.setRef(text);
                        }
                        else if(name.equals(TAG_IMMEDIATE_DELIVERY)){
                            program.setImmediate_delivery(Integer.parseInt(text));
                        }
                        else if(name.equals(TAG_NAME)){
                            program.setName(text);
                        }
                        else if(name.equals(TAG_DESCRIPTION)){
                            program.setDescription(text);
                        }
                        else if(name.equals(TAG_DESCRIPTION_PROMOTER)){
                            program.setDescription_promoter(text);
                        }
                        else if(name.equals(TAG_LAWS)){
                            program.setInvestmentLaws(text);
                        }
                        else if(name.equals(TAG_ADDRESS)){
                            program.setAdress(text);
                        }
                        else if(name.equals(TAG_CP)){
                            program.setCode_postal(text);
                        }
                        else if(name.equals(TAG_CITY)){
                            program.setCity(text);
                        }
                        else if(name.equals(TAG_DPT_CODE)){
                            program.setDpt_code(text);
                        }
                        else if(name.equals(TAG_DPT)){
                            program.setDpt_label(text);
                        }
                        else if(name.equals(TAG_COUNTRY)){
                            program.setCountry(text);
                        }
                        else if(name.equals(TAG_REGION)){
                            program.setRegion(text);
                        }
                        else if(name.equals(TAG_LONGITUDE)){
                            program.setLongitude(Float.parseFloat(text));
                        }
                        else if(name.equals(TAG_LATITUDE)){
                            program.setLatitude(Float.parseFloat(text));
                        }
                        else if(name.equals(TAG_OPTION)){
                            if (program.getOptions() == null){
                                program.setOptions(new ArrayList<String>());
                            }
                            program.getOptions().add(text);
                        }
                        else if(name.equals(TAG_PHOTO)){
                            if (program.getPhotos_urls() == null){
                                program.setPhotos_urls(new ArrayList<String>());
                            }
                            program.getPhotos_urls().add(myParser.getAttributeValue(null,TAG_URL));
                        }
                        else if(name.equals(TAG_UNDER_CONSTRUCTION)){
                            program.setUnderConstruction(Integer.parseInt(text));
                        }
                }
                event = myParser.next();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("NewApi")
    public static void launchParsing(Ad_details pActivity, String ref) {
        AsyncTask<Void, Integer, Boolean> asynctask = new ad_detail_parser(pActivity, ref);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.HONEYCOMB_MR1) {
            asynctask.execute();
        } else {
            asynctask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }
}
