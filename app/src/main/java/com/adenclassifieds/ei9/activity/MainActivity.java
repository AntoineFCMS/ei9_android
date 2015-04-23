package com.adenclassifieds.ei9.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.adenclassifieds.ei9.R;
import com.adenclassifieds.ei9.adapter.SavedAdAdapter;
import com.adenclassifieds.ei9.business.Program;
import com.adenclassifieds.ei9.server.ad_detail_parser;
import com.adenclassifieds.ei9.utils.DrawableManager;
import com.adenclassifieds.ei9.utils.MyPreferenceManager;
import com.adenclassifieds.ei9.utils.xiti;
import com.at.ATTag;
import com.onprint.sdk.core.BitmapScannerAsyncTask;
import com.onprint.sdk.core.Scanner;
import com.onprint.sdk.core.ScannerAsyncTaskCallback;
import com.onprint.sdk.core.model.classes.ImageScanResponse;

import java.util.ArrayList;
import java.util.Set;


public class MainActivity extends ActionBarActivity implements ScannerAsyncTaskCallback {
    public static ATTag attag = null ;

    private static final int SELECT_PICTURE_RESULT_CODE = 1;
    private ListView saved_ref_list;
    private DrawableManager imagemanager;
    private RelativeLayout progress;
    private SavedAdAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saved_ref_list = (ListView) findViewById(R.id.list);
        progress = (RelativeLayout) findViewById(R.id.progress);

        imagemanager = new DrawableManager();

        Scanner.setAPIKey(getApplicationContext(), getResources().getString(R.string.onprint));

        xiti.initparam(MainActivity.this);

        registerForContextMenu(saved_ref_list);

//        final Set<String> set = MyPreferenceManager.getAdsRefs(getApplicationContext());
//        //if (set != null && set.isEmpty() && savedInstanceState == null){
//        if (set != null && set.isEmpty()){
//            pickImage();
//        }

        View view = getLayoutInflater() .inflate(R.layout.no_saved_ad, null);
        ViewGroup viewGroup= ( ViewGroup)saved_ref_list.getParent();
        viewGroup.addView(view);
        saved_ref_list.setEmptyView(view);

        ActionBar actionBar = getSupportActionBar();
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, Gravity.LEFT | Gravity.CENTER_VERTICAL);
        View customNav = LayoutInflater.from(this).inflate(R.layout.actionbar, null); // layout which contains your button.
        actionBar.setCustomView(customNav, lp);
        actionBar.setDisplayShowCustomEnabled(true);
    }

    @Override
    protected void onResume() {
        final Set<String> set = MyPreferenceManager.getAdsRefs(getApplicationContext());
        if (set != null && !set.isEmpty())
            ad_detail_parser.launchParsing(this, set.toArray(new String[set.size()]));



        super.onResume();
    }

    public void pickImage() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, SELECT_PICTURE_RESULT_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == SELECT_PICTURE_RESULT_CODE) {
            //saved_ref_list.setVisibility(View.GONE);
            progress.setVisibility(View.VISIBLE);
            Bitmap photo;
            photo = (Bitmap) data.getExtras().get("data");

            if (photo != null) {
                BitmapScannerAsyncTask task = new BitmapScannerAsyncTask(this);
                task.execute(photo);
                xiti.hit(getString(R.string.hit_scan_onprint));
            }
        }


        super.onActivityResult(requestCode, resultCode, data);
    }

    private void openAd(String ref){
        Intent i = new Intent(MainActivity.this, Ad_details.class);
        i.putExtra(Ad_details.TAG_REF,ref);
        startActivity(i);
    }

    @Override
    public void onScanCompleted(ImageScanResponse imageScanResponse) {
        progress.setVisibility(View.GONE);
        //saved_ref_list.setVisibility(View.VISIBLE);
        if (imageScanResponse != null && imageScanResponse.getResults() != null && !imageScanResponse.getResults().isEmpty() &&
                imageScanResponse.getResults().get(0).getActions() != null && !imageScanResponse.getResults().get(0).getActions().isEmpty()){
            final String ref = imageScanResponse.getResults().get(0).getActions().get(0).getURL();
            openAd(ref);
        }
        else{
            Toast.makeText(getApplicationContext(),getString(R.string.scan_no_result),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onScanFailed(Exception e) {
        progress.setVisibility(View.GONE);
        saved_ref_list.setVisibility(View.VISIBLE);
        Toast.makeText(getApplicationContext(),getString(R.string.scan_error),Toast.LENGTH_SHORT).show();
    }

        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        final MenuItem item = menu.findItem(R.id.menu_scan_image);
        item.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(item);
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        final int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.menu_scan_image) {
            pickImage();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setSavedAdInformation(ArrayList<Program> programs) {


        adapter = new SavedAdAdapter(getApplicationContext(), getLayoutInflater(),programs,imagemanager);
        saved_ref_list.setAdapter(adapter);


        saved_ref_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openAd(((Program) adapter.getItem(position)).getRef());
            }
        });


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        if (v.getId()==R.id.list) {
            AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)menuInfo;
            menu.setHeaderTitle(((Program)adapter.getItem(info.position)).getName());
            String[] menuItems = getResources().getStringArray(R.array.list_menu);
            for (int i = 0; i<menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        MyPreferenceManager.deleteRef(getApplicationContext(),adapter.getRef(info.position));
        final Set<String> set = MyPreferenceManager.getAdsRefs(getApplicationContext());
        if (set != null && !set.isEmpty())
            ad_detail_parser.launchParsing(this, set.toArray(new String[set.size()]));
        else
            setSavedAdInformation(new ArrayList<Program>());

        return super.onContextItemSelected(item);
    }
}
