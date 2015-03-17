package com.adenclassifieds.ei9.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;


public class DrawableManager {
    private static Map<String, SoftReference<Drawable>> drawableMap;
    public Drawable drawable_res;

    public DrawableManager() {
        if (drawableMap == null)
            drawableMap = new HashMap<String, SoftReference<Drawable>>();
    }

    public Drawable fetchDrawable(String urlString) {
        if (drawableMap.containsKey(urlString)) {
        	Drawable res = drawableMap.get(urlString).get();
        	if (res != null)
        		return res;
        }



        InputStream is = null;
        Drawable drawable = null;
        try {
            is = fetch(urlString);
            
        	final BitmapFactory.Options options = new BitmapFactory.Options();
//    		options.inPreferredConfig = Bitmap.Config.RGB_565;
//    		options.inSampleSize = 1;
    		
    		while (drawable == null && options.inSampleSize <= 10) {
    			//Log.d("Drawablemanager", String.valueOf(options.inSampleSize));
    			try {
    				drawable = Drawable.createFromResourceStream(null, null, is, "src", options);
    			}
    			catch (OutOfMemoryError e) {}
    			options.inSampleSize++;
    		}

            if (drawable != null) {
                drawableMap.put(urlString, new SoftReference<Drawable>(drawable));
            } else {
              Log.w(this.getClass().getSimpleName(), "could not get thumbnail");
            }
            
        } catch (MalformedURLException e) {
            Log.e(this.getClass().getSimpleName(), "fetchDrawable failed", e);
            return null;
        } catch (IOException e) {
            Log.e(this.getClass().getSimpleName(), "fetchDrawable failed", e);
            return null;
        }
        finally
        {
            // Clean-up if we failed on save
            if (is != null)
            {
                try
                {
                	is.close();
                }
                catch (IOException e)
                {
                }
            }
        }
        return drawable;
    }
    
    public void fetchDrawableOnThread(final String urlString, final ImageView imageView) {
    	fetchDrawableOnThread(urlString, imageView, null);
    }

    public void fetchDrawableOnThread(final String urlString, final ImageView imageView, final ProgressBar progress_circle) {
    	Drawable res = null;
        if (drawableMap.containsKey(urlString) && (res = drawableMap.get(urlString).get()) != null) {
        	if (res != null)
        		if (progress_circle != null)
            		progress_circle.setVisibility(View.GONE);
        		imageView.setImageDrawable(res);
        }
        else {

            final Handler handler = new Handler() {
                @Override
                public void handleMessage(Message message) {
                    if (progress_circle != null)
                        progress_circle.setVisibility(View.GONE);
                    imageView.setImageDrawable((Drawable) message.obj);
                }
            };

            Thread thread = new Thread() {
                @Override
                public void run() {
                    //TODO : set imageView to a "pending" image
                    Drawable drawable = fetchDrawable(urlString);
                    Message message = handler.obtainMessage(1, drawable);
                    handler.sendMessage(message);
                }
            };
            thread.start();
        }
    }
    
    public Drawable getDrawableOnThread(final String urlString) {
    	drawable_res = null;
        if (drawableMap.containsKey(urlString) && (drawable_res = drawableMap.get(urlString).get()) != null) {
        	if (drawable_res != null)
        		return drawable_res;
        }
        else {

        Thread thread = new Thread() {
            @Override
            public void run() {
            	drawable_res = fetchDrawable(urlString);
            }
        };
        thread.start();
        }
        return drawable_res;
    }

    private InputStream fetch(String urlString) throws MalformedURLException, IOException {
        //Log.d(this.getClass().getSimpleName(), "image url:" + urlString);
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet request = new HttpGet(urlString);
        HttpResponse response = httpClient.execute(request);
        return response.getEntity().getContent();
    }
    
    public void fetchDrawableIfAlreadyDL(final String urlString, final ImageView imageView) {
        if (drawableMap.containsKey(urlString)) {
        	Drawable res = drawableMap.get(urlString).get();
        	if (res != null)
            imageView.setImageDrawable(res);
        }
    }
}