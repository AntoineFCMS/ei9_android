package com.adenclassifieds.ei9.utils;

import android.content.Context;

import com.adenclassifieds.ei9.R;
import com.adenclassifieds.ei9.activity.MainActivity;
import com.at.ATParams;
import com.at.ATTag;

/**
 * Created by Antoine GALTIER on 17/03/15.
 */
public class xiti {

    public static void initparam(Context ctx){
        String SUBDOMAIN=ctx.getString(R.string.subdomain);
        String SITEID=ctx.getString(R.string.siteid);
        String SUBSITE=ctx.getString(R.string.s2);
        MainActivity.attag = ATTag.init(ctx, SUBDOMAIN, SITEID, SUBSITE);
    }

    public static void hit(String tag){
        ATParams atp = new ATParams();
        atp.setPage(tag);
        atp.xt_sendTag();
    }

}
