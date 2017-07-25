package com.research.andrade.andar;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MessageContact extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView longi, lati;
    private RadioButton immediately, possible, ok;
    private Button fetch,sendMess;
    private TrackGPS gps;
    double longitude, latitude;
    private String message="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_contact);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Message Contacts");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        longi = (TextView) findViewById(R.id.txtLong);
        lati = (TextView) findViewById(R.id.txtLat);
        fetch = (Button) findViewById(R.id.btnFetch);
        sendMess = (Button) findViewById(R.id.btnSend);
        immediately = (RadioButton) findViewById(R.id.rdoImmediately);
        possible = (RadioButton) findViewById(R.id.rdoPossible);
        ok = (RadioButton) findViewById(R.id.rdoOkay);

        fetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fetchClick();
            }
        });
        immediately.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = immediately.getText().toString();
            }
        });
        possible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = possible.getText().toString();
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = ok.getText().toString();
            }
        });

        sendMess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendSMS();
            }
        });



    }

    private void fetchClick(){
        gps = new TrackGPS(MessageContact.this);
        if(gps.canGetLocation()){
            longitude = gps.getLongitude();
            latitude = gps .getLatitude();
            lati.setText(""+latitude);
            longi.setText(""+longitude);
        }
        else
            gps.showSettingsAlert();

    }

    private void sendSMS(){

        if(message.equals("")){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("Error");
            alertDialogBuilder
                    .setMessage("Please Select Message \nMessage Not Sent!!")
                    .setCancelable(true);

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();

        } else {
            String fMess = message+" \nMy Coordinates are Latitude : "+latitude+" and Longitude : "+longitude;
//          Snackbar.make(findViewById(android.R.id.content), ""+fMess, Snackbar.LENGTH_LONG).show();
            Toast.makeText(this, fMess, Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            MessageContact.this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
