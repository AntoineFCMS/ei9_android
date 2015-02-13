package com.adenclassifieds.ei9;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.adenclassifieds.ei9.adapter.PhotoPagerAdapter;
import com.adenclassifieds.ei9.business.Program;
import com.adenclassifieds.ei9.server.ad_detail_parser;
import com.adenclassifieds.ei9.utils.DrawableManager;
import com.viewpagerindicator.CirclePageIndicator;


public class Ad_details extends ActionBarActivity {

    public final static String TAG_REF = "ref";

    private String ref;

    private ProgressBar progressBar;
    private View view_info;
    private TextView localisation;
    private TextView delivery_date;
    private TextView modification_date;
    private TextView ad_type;
    private TextView immediate_delivery;
    private Button info_ask;
    private TextView description;
    private TextView fisc;
    private TableLayout options;
    private ViewPager mPager;
    private CirclePageIndicator indicator;

    private DrawableManager imagemanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ad_details);

        ref = getIntent().getStringExtra(TAG_REF);

        progressBar = (ProgressBar) findViewById(R.id.progress_descriptif_circle);
        view_info = findViewById(R.id.view_info_ad);
        localisation = (TextView) findViewById(R.id.localisation);
        delivery_date = (TextView) findViewById(R.id.delivery_date);
        modification_date = (TextView) findViewById(R.id.modification_date);
        ad_type = (TextView) findViewById(R.id.ad_type);
        immediate_delivery = (TextView) findViewById(R.id.immediate_delivery);
        description = (TextView) findViewById(R.id.ad_description);
        fisc = (TextView) findViewById(R.id.fisc);
        info_ask = (Button) findViewById(R.id.info_ask);
        options = (TableLayout) findViewById(R.id.options_tab);

        mPager = (ViewPager) findViewById(R.id.pager);
        indicator = (CirclePageIndicator) findViewById(R.id.indicator);

        imagemanager = new DrawableManager();

        ad_detail_parser.launchParsing(this, ref);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ad_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void setProgrammInformation(Program p){
        localisation.setText(p.getName() + " " + p.getCity() + " " + p.getCode_postal());
        delivery_date.setText(p.getDelivery_date());
        modification_date.setText(p.getModification_date());
        ad_type.setText(p.getType());
        if (p.getImmediate_delivery() == 0)
            immediate_delivery.setText(getString(R.string.no));
        else
            immediate_delivery.setText(getString(R.string.yes));
        description.setText(p.getDescription());
        fisc.setText(p.getInvestmentLaws());

        if (p.getOptions() != null && p.getOptions().size() != 0) {
            for (int i = 0; i < p.getOptions().size(); i++) {
                final String spec = p.getOptions().get(i);

                TableRow new_ligne = new TableRow(this);
                final TextView texte = new TextView(this);
                texte.setText(spec);
                texte.setGravity(Gravity.RIGHT);
                new_ligne.addView(texte);
                options.addView(new_ligne);
            }
        }
        else {
            options.setVisibility(View.GONE);
        }

        PhotoPagerAdapter mAdapter = new PhotoPagerAdapter(this, imagemanager, p.getPhotos_urls());
        mPager.setAdapter(mAdapter);
        indicator.setViewPager(mPager);

        progressBar.setVisibility(View.INVISIBLE);
        view_info.setVisibility(View.VISIBLE);
    }

}
