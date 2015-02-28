package com.adenclassifieds.ei9.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.adenclassifieds.ei9.R;
import com.adenclassifieds.ei9.business.ContactForm;
import com.adenclassifieds.ei9.server.contact_promoter_parser;
import com.adenclassifieds.ei9.utils.EmailValidator;

public class Contact_form extends ActionBarActivity {

    private String ref;
    private EditText name;
    private EditText first_name;
    private EditText mail;
    private EditText tel;
    private EditText editViewAdress;
    private EditText cp;
    private EditText town;
    private EditText editViewmessage;
    private Button send;
    private View form_layout;
    private ProgressBar progress;
    private Spinner civilite_spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_form);

        ref = getIntent().getStringExtra(Ad_details.TAG_REF);

        name = (EditText) findViewById(R.id.name);
        first_name = (EditText) findViewById(R.id.first_name);
        mail = (EditText) findViewById(R.id.email);
        tel = (EditText) findViewById(R.id.tel);
        editViewAdress = (EditText) findViewById(R.id.adress);
        cp = (EditText) findViewById(R.id.cp);
        town = (EditText) findViewById(R.id.town);
        editViewmessage = (EditText) findViewById(R.id.message);
        send = (Button) findViewById(R.id.send);
        progress = (ProgressBar) findViewById(R.id.progress);
        form_layout = findViewById(R.id.form_layout);
        civilite_spinner = (Spinner) findViewById(R.id.civilite_spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,getResources().getStringArray(R.array.civilites));

        civilite_spinner.setAdapter(adapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

    }

    private void attemptLogin() {

        // Reset errors.
        mail.setError(null);
        name.setError(null);

        // Store values at the time of the login attempt.
        final String nom = name.getText().toString();
        final String prenom = first_name.getText().toString();
        final String adress = editViewAdress.getText().toString();
        final String code_postal = cp.getText().toString();
        final String ville = town.getText().toString();
        final String civilite = (String) civilite_spinner.getSelectedItem();
        final String email = mail.getText().toString();
        final String message = editViewmessage.getText().toString();

        boolean cancel = false;
        View focusView = null;



        if (((String)civilite_spinner.getSelectedItem()).isEmpty()) {
            focusView = civilite_spinner;
            Toast.makeText(getApplicationContext(),getString(R.string.civilite_error),Toast.LENGTH_SHORT).show();
            cancel = true;
        }
        else if (TextUtils.isEmpty(nom)) {
            focusView = name;
            name.setError(getString(R.string.error));
            cancel = true;
        }
        else if (TextUtils.isEmpty(prenom)) {
            focusView = first_name;
            first_name.setError(getString(R.string.error));
            cancel = true;
        }
        else if (TextUtils.isEmpty(email) || !EmailValidator.isEmailValid(email)) {
            focusView = mail;
            mail.setError(getString(R.string.error));
            cancel = true;
        }
        else if (TextUtils.isEmpty(adress)) {
            focusView = editViewAdress;
            editViewAdress.setError(getString(R.string.error));
            cancel = true;
        }
        else if (TextUtils.isEmpty(code_postal)) {
            focusView = cp;
            cp.setError(getString(R.string.error));
            cancel = true;
        }
        else if (TextUtils.isEmpty(ville)) {
            focusView = town;
            town.setError(getString(R.string.error));
            cancel = true;
        }
        else if (TextUtils.isEmpty(message)) {
            focusView = editViewmessage;
            editViewmessage.setError(getString(R.string.error));
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            ContactForm contactForm = new ContactForm(ville,nom,prenom,adress,code_postal,civilite,ref,email,message);
            if (!tel.getText().toString().isEmpty())
                contactForm.setTelephone(tel.getText().toString());
            if (contactForm != null)
                contact_promoter_parser.launchParsing(this,contactForm);
        }
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            form_layout.setVisibility(show ? View.GONE : View.VISIBLE);
            form_layout.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    form_layout.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progress.setVisibility(show ? View.VISIBLE : View.GONE);
            progress.animate().setDuration(shortAnimTime).alpha(show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progress.setVisibility(show ? View.VISIBLE : View.GONE);
            form_layout.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    public void display_server_message(String line) {
        showProgress(false);

        AlertDialog.Builder alt_bld = new AlertDialog.Builder(this);

        alt_bld.setMessage(line)
                .setCancelable(true)
                .setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        finish();
                    }
                });

        AlertDialog alert = alt_bld.create();
        alert.setTitle(getString(R.string.server_message));
        alert.show();

    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_contact_form, menu);
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
