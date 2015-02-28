package com.adenclassifieds.ei9.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Antoine GALTIER on 20/02/15.
 */
public class MyPreferenceManager {

    private final static String TAG_REFS = "refs";


    public static void saveAdRef(Context ctx, String ref){
        SharedPreferences preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = preferences.edit();
        Set<String> set  = preferences.getStringSet(TAG_REFS,null);
        if (set == null)
            set = new HashSet<>();
        if (!set.contains(ref))
            set.add(ref);


        editor.putStringSet(TAG_REFS, set);
        editor.apply();
    }

    public static Set<String> getAdsRefs(Context ctx){
        SharedPreferences preferences = android.preference.PreferenceManager.getDefaultSharedPreferences(ctx);
        return preferences.getStringSet(TAG_REFS,null);
    }
}
