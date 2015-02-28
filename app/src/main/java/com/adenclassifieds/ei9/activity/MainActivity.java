package com.adenclassifieds.ei9.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.adenclassifieds.ei9.R;
import com.adenclassifieds.ei9.adapter.SavedAdAdapter;
import com.adenclassifieds.ei9.business.Program;
import com.adenclassifieds.ei9.server.ad_detail_parser;
import com.adenclassifieds.ei9.utils.DrawableManager;
import com.adenclassifieds.ei9.utils.MyPreferenceManager;
import com.onprint.sdk.core.BitmapScannerAsyncTask;
import com.onprint.sdk.core.Scanner;
import com.onprint.sdk.core.ScannerAsyncTaskCallback;
import com.onprint.sdk.core.model.classes.ImageScanResponse;

import java.util.ArrayList;
import java.util.Set;


public class MainActivity extends ActionBarActivity implements ScannerAsyncTaskCallback {

    private static final int SELECT_PICTURE_RESULT_CODE = 1;
    private ListView saved_ref_list;
    private DrawableManager imagemanager;
    private RelativeLayout progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        saved_ref_list = (ListView) findViewById(R.id.list);
        progress = (RelativeLayout) findViewById(R.id.progress);

        imagemanager = new DrawableManager();

        Scanner.setAPIKey(getApplicationContext(), getResources().getString(R.string.onprint));
    }

    @Override
    protected void onResume() {
        final Set<String> set = MyPreferenceManager.getAdsRefs(getApplicationContext());
        if (set != null && !set.isEmpty())
            ad_detail_parser.launchParsing(this, set.toArray(new String[set.size()]));
        else
            setSavedAdInformation(new ArrayList<Program>());

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
            progress.setVisibility(View.VISIBLE);
            Bitmap photo;
            photo = (Bitmap) data.getExtras().get("data");

//            Uri selectedImageUri = data.getData();
//            String selectedImagePath = getPath(selectedImageUri);
//
//            Bitmap imageBitmap = BitmapFactory.decodeFile(selectedImagePath);

            if (photo != null) {
                BitmapScannerAsyncTask task = new BitmapScannerAsyncTask(this);
                task.execute(photo);
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
        View view = getLayoutInflater() .inflate(R.layout.no_saved_ad, null);
        ViewGroup viewGroup= ( ViewGroup)saved_ref_list.getParent();
        viewGroup.addView(view);
        saved_ref_list.setEmptyView(view);

        final SavedAdAdapter adapter = new SavedAdAdapter(getApplicationContext(), getLayoutInflater(),programs,imagemanager);
        saved_ref_list.setAdapter(adapter);

        saved_ref_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openAd(((Program) adapter.getItem(position)).getRef());
            }
        });
    }
}
