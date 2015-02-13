package com.adenclassifieds.ei9.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import com.adenclassifieds.ei9.R;
import com.onprint.sdk.core.Scanner;
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


        Intent i = new Intent(MainActivity.this, Ad_details.class);
        i.putExtra(Ad_details.TAG_REF,"4229762");

        startActivity(i);

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