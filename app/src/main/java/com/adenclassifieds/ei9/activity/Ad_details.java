package com.adenclassifieds.ei9.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.adenclassifieds.ei9.R;
import com.adenclassifieds.ei9.adapter.AdListAdapter;
import com.adenclassifieds.ei9.adapter.PhotoPagerAdapter;
import com.adenclassifieds.ei9.business.Program;
import com.adenclassifieds.ei9.server.ad_detail_parser;
import com.adenclassifieds.ei9.utils.DrawableManager;
import com.adenclassifieds.ei9.utils.ListViewInsideScrollView;
import com.adenclassifieds.ei9.utils.MyPreferenceManager;
import com.adenclassifieds.ei9.utils.xiti;
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
    private TextView fisc_label;
    private TableLayout options;
    private ViewPager mPager;
    private CirclePageIndicator indicator;
    private ListView available_ad_list;

    private DrawableManager imagemanager;

    private Program program;

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
        fisc_label = (TextView) findViewById(R.id.fisc_label);
        info_ask = (Button) findViewById(R.id.info_ask);
        options = (TableLayout) findViewById(R.id.options_tab);
        available_ad_list = (ListView) findViewById(R.id.available_ad_list);

        mPager = (ViewPager) findViewById(R.id.pager);
        indicator = (CirclePageIndicator) findViewById(R.id.indicator);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT, Gravity.LEFT | Gravity.CENTER_VERTICAL);
        View customNav = LayoutInflater.from(this).inflate(R.layout.actionbar, null); // layout which contains your button.
        actionBar.setCustomView(customNav, lp);
        actionBar.setDisplayShowCustomEnabled(true);

        imagemanager = new DrawableManager();

        ad_detail_parser.launchParsing(this, ref);

        setlistener();

        xiti.hit(getString(R.string.hit_detail));
    }



    private void setlistener() {
        info_ask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Ad_details.this, Contact_form.class);
                i.putExtra(Ad_details.TAG_REF,ref);
                startActivity(i);
            }
        });
        mPager.setOnTouchListener(new View.OnTouchListener() {
            private float pointX;
            private float pointY;
            private int tolerance = 50;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        return false;
                    case MotionEvent.ACTION_DOWN:
                        pointX = event.getX();
                        pointY = event.getY();
                        break;
                    case MotionEvent.ACTION_UP:
                        boolean sameX = pointX + tolerance > event.getX() && pointX - tolerance < event.getX();
                        boolean sameY = pointY + tolerance > event.getY() && pointY - tolerance < event.getY();
                        if (sameX && sameY) {

                            Intent i = new Intent(Ad_details.this, Fullscreen_slider.class);
                            i.putExtra(Fullscreen_slider.TAG_INTENT,program.getPhotos_urls());
                            startActivity(i);

                        }
                }
                return false;
            }
        });
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_ad_details, menu);
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


    public void setProgrammInformation(Program p){
        saveProgrammRef(p);
        this.program = p;

        localisation.setText(p.getName() + ", " + p.getCity() + " - " + p.getCode_postal());
        delivery_date.setText(p.getDelivery_date());
        modification_date.setText(p.getModification_date());
        ad_type.setText(p.getType());
        if (p.getImmediate_delivery() == 0)
            immediate_delivery.setText(getString(R.string.no));
        else
            immediate_delivery.setText(getString(R.string.yes));
        description.setText(p.getDescription());
        if (p.getInvestmentLaws() == null || p.getInvestmentLaws().isEmpty()) {
            fisc.setVisibility(View.GONE);
            fisc_label.setVisibility(View.GONE);
        }
        fisc.setText(p.getInvestmentLaws());

        if (p.getOptions() != null && p.getOptions().size() != 0) {
            for (int i = 0; i < p.getOptions().size(); i++) {
                final String spec = p.getOptions().get(i);

                TableRow new_ligne = new TableRow(this);
                TableLayout.LayoutParams lp =new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT);
                lp.setMargins(0,4,0,0);
                new_ligne.setLayoutParams(lp);
                new_ligne.setBackgroundColor(getResources().getColor(R.color.grey_light));

                final TextView texte = new TextView(this);
                texte.setText(spec);
                texte.setTextColor(getResources().getColor(R.color.ei9_green_blue));
                texte.setGravity(Gravity.RIGHT);
                texte.setPadding(0,0,4,0);

                new_ligne.addView(texte);
                options.addView(new_ligne,lp);
            }
        }
        else {
            options.setVisibility(View.GONE);
        }

        PhotoPagerAdapter mAdapter = new PhotoPagerAdapter(this, imagemanager, p.getPhotos_urls());
        mPager.setAdapter(mAdapter);

        indicator.setViewPager(mPager);

        AdListAdapter adapter = new AdListAdapter(getApplicationContext(), getLayoutInflater(), p.getLogements(), imagemanager);
        available_ad_list.setAdapter(adapter);
        ListViewInsideScrollView.setListViewInsideAScrollView(available_ad_list);

        progressBar.setVisibility(View.GONE);
        view_info.setVisibility(View.VISIBLE);
    }

    private void saveProgrammRef(Program p) {
        MyPreferenceManager.saveAdRef(getApplicationContext(),p.getRef());
    }

}
