package com.adenclassifieds.ei9;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.adenclassifieds.ei9.server.ad_detail_parser;
import com.onprint.sdk.activity.ScanActivity;
import com.onprint.sdk.activity.Settings;
import com.onprint.sdk.core.Scanner;
import com.onprint.sdk.core.exception.WSException;
import com.onprint.sdk.core.model.classes.ImageScanResult;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    private static final int RETURN_CODE = 1;
    private List<ImageScanResult> result_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Scanner.setAPIKey(getApplicationContext(), "BD3B7446-F099-4288-9E99-A790E3330B47");

//        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.test);
//        try {
//            result_list = Scanner.scanImage(b);
//        } catch (WSException e) {
//            e.printStackTrace();
//        }
//
//
//        for (ImageScanResult im : result_list){
//            Toast.makeText(getApplicationContext(),im.getTitle(),Toast.LENGTH_SHORT).show();
//        }

//        Intent i = new Intent(MainActivity.this, ScanActivity.class);
//
//        startActivityForResult(i, RETURN_CODE);

        ad_detail_parser.launchParsing(getApplicationContext(),"31401334");


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RETURN_CODE){
        }


        super.onActivityResult(requestCode, resultCode, data);
    }


    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
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
