package com.adenclassifieds.ei9.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;

import com.adenclassifieds.ei9.R;
import com.adenclassifieds.ei9.adapter.PhotoPagerAdapter;
import com.adenclassifieds.ei9.utils.DrawableManager;
import com.adenclassifieds.ei9.utils.xiti;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

public class Fullscreen_slider extends ActionBarActivity {

    private ViewPager pager;
    private CirclePageIndicator indicator;
    public final static String TAG_INTENT = "list";
    private ArrayList<String> image_list;
    private DrawableManager imagemanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_slider);

        pager = (ViewPager) findViewById(R.id.pager);
        indicator = (CirclePageIndicator) findViewById(R.id.indicator);

        image_list = getIntent().getStringArrayListExtra(TAG_INTENT);

        if (image_list != null)
            feed_pager();

        xiti.hit(getString(R.string.hit_slideshow));
    }

    private void feed_pager() {
        imagemanager = new DrawableManager();
        PhotoPagerAdapter adapter = new PhotoPagerAdapter(getApplicationContext(),imagemanager,image_list);
        pager.setAdapter(adapter);
        indicator.setViewPager(pager);
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_fullscreen_slider, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
