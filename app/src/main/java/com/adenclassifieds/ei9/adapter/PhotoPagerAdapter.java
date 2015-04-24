package com.adenclassifieds.ei9.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.adenclassifieds.ei9.R;
import com.adenclassifieds.ei9.utils.DrawableManager;

import java.util.ArrayList;

public class PhotoPagerAdapter extends PagerAdapter {
	private LayoutInflater inflater;
	private String url_top_image;
	private ArrayList<String> list_images;
	private String image_quality;
	private ProgressBar progress_circle;
	private DrawableManager imagemanager;
	

	public PhotoPagerAdapter(Context ctx, DrawableManager imagemanager, ArrayList<String> list_images) {
		super();
		inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.list_images = list_images;
		this.imagemanager = imagemanager;
	}

	@Override
	public int getCount() {
		return list_images.size();
	}

	@Override
	public Object instantiateItem(ViewGroup collection, int position) {
		RelativeLayout view = null;

		if (list_images != null && list_images.get(position) != null) {
			
			view = (RelativeLayout) inflater.inflate(R.layout.image_pager, null);
			
			progress_circle = (ProgressBar) view.findViewById(R.id.progressBar);
			ImageView imageView = (ImageView) view.findViewById(R.id.image);

            imagemanager.fetchDrawableOnThread(list_images.get(position),imageView,progress_circle);

			collection.addView(view, 0);
		}

		return view;
	}

	@Override
	public void destroyItem(ViewGroup collection, int arg1, Object arg2) {
		collection.removeView((View) arg2);
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == ((View) arg1);
	}
	
}