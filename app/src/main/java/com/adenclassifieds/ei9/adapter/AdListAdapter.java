package com.adenclassifieds.ei9.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.adenclassifieds.ei9.R;
import com.adenclassifieds.ei9.business.Logement;
import com.adenclassifieds.ei9.utils.DrawableManager;

import java.util.ArrayList;

/**
 * Created by Antoine GALTIER on 16/02/15.
 */
public class AdListAdapter extends BaseAdapter{

    private ArrayList<Logement> list;
    private static LayoutInflater mInflater;
    private DrawableManager imagemanager;
    private Context ctx;

    public AdListAdapter(Context ctx, LayoutInflater li, ArrayList<Logement> l, DrawableManager imagemanager){
        mInflater = li;
        list = l;
        this.imagemanager = imagemanager;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.ad_row, null);

            holder = new ViewHolder();
            holder.price = (TextView) convertView.findViewById(R.id.ad_price);
            holder.ad_type = (TextView) convertView.findViewById(R.id.ad_type);
            holder.nb_room = (TextView) convertView.findViewById(R.id.nb_room);
            holder.surface = (TextView) convertView.findViewById(R.id.surface);
            holder.etage = (TextView) convertView.findViewById(R.id.etage);
            holder.icon = (ImageView) convertView.findViewById(R.id.miniature);
            holder.needInflate = false;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }


        final Logement logement = (Logement)getItem(position);
        if(logement != null) {
            holder.price.setText(logement.getAd_type());

            holder.ad_type.setText(logement.getAd_type());

            holder.nb_room.setText(String.valueOf(logement.getNb_rooms()));

            if (logement.getSurface() == 0 && logement.getSurface_min() == 0 && logement.getSurface_max() == 0)
                holder.surface.setText(ctx.getString(R.string.nc));
            else {
                if (logement.getSurface() != 0)
                    holder.surface.setText(String.valueOf(logement.getSurface())+" "+ctx.getString(R.string.m2));
                else if (logement.getSurface_min() == logement.getSurface_max())
                    holder.surface.setText(String.valueOf(logement.getSurface_min())+" "+ctx.getString(R.string.m2));
                else if (logement.getSurface_max() == 0)
                    holder.surface.setText(ctx.getString(R.string.from)+" "+String.valueOf(logement.getSurface_min())+" "+ctx.getString(R.string.m2));
                else if (logement.getSurface_min() < logement.getSurface_max())
                    holder.surface.setText(ctx.getString(R.string.de)+" "+String.valueOf(logement.getSurface_min())+" "+ctx.getString(R.string.a)+" "+String.valueOf(logement.getSurface_max())+" "+ctx.getString(R.string.m2));
            }

            if (logement.getFloor() == 0)
                holder.etage.setText(ctx.getString(R.string.nc));
            else
                holder.etage.setText(String.valueOf(logement.getFloor()));
            imagemanager.fetchDrawableOnThread(logement.getPhotos_urls().get(0), holder.icon);
        }
        return convertView;
    }

    static class ViewHolder {
        public boolean needInflate;
        public TextView price;
        public TextView ad_type;
        public TextView nb_room;
        public TextView surface;
        public TextView etage;
        public ImageView icon;
    }
}
