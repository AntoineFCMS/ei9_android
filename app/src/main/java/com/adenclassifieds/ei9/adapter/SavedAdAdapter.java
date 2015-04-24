package com.adenclassifieds.ei9.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.adenclassifieds.ei9.R;
import com.adenclassifieds.ei9.business.Program;
import com.adenclassifieds.ei9.utils.DrawableManager;

import java.util.ArrayList;

/**
 * Created by Antoine GALTIER on 20/02/15.
 */
public class SavedAdAdapter extends BaseAdapter {

    private ArrayList<Program> programs;
    private static LayoutInflater mInflater;
    private DrawableManager imagemanager;
    private Context ctx;

    public SavedAdAdapter(Context ctx, LayoutInflater li, ArrayList<Program> programs, DrawableManager imagemanager){
        mInflater = li;
        this.imagemanager = imagemanager;
        this.ctx = ctx;
        this.programs = programs;
    }

    @Override
    public int getCount() {
        return programs.size();
    }

    @Override
    public Object getItem(int position) {
        return programs.get(position);
    }

    public String getRef(int position) {
        return programs.get(position).getRef();
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.saved_ad_row, null);

            holder = new ViewHolder();
            holder.ad_name = (TextView) convertView.findViewById(R.id.ad_name);
            holder.ad_localisation = (TextView) convertView.findViewById(R.id.ad_localisation);
            holder.icon = (ImageView) convertView.findViewById(R.id.miniature);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }


        final Program p = programs.get(position);
        if(p != null) {
            holder.ad_name.setText(p.getName());
            holder.ad_localisation.setText(p.getAdress() + ", " + p.getCity() + " - " + p.getCode_postal());

            if (p.getPhotos_urls() != null && !p.getPhotos_urls().isEmpty())
                imagemanager.fetchDrawableOnThread(p.getPhotos_urls().get(0), holder.icon);
            else
                holder.icon.setImageDrawable(ctx.getResources().getDrawable(R.drawable.ic_launcher));
        }
        return convertView;
    }

    static class ViewHolder {
        public TextView ad_name;
        public TextView ad_localisation;
        public ImageView icon;
    }
}
