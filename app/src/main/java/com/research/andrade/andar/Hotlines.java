package com.research.andrade.andar;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Hotlines extends AppCompatActivity {

    private Toolbar toolbar;
    private Button locPoliceBtn,locFireBtn,locRescueBtn,locBrgyBtn;
    private String telNum="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotlines);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Emergency Hotlines");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // add PhoneStateListener
        PhoneCallListener phoneListener = new PhoneCallListener();
        TelephonyManager telephonyManager = (TelephonyManager) this
                .getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneListener,PhoneStateListener.LISTEN_CALL_STATE);

        locPoliceBtn = (Button) findViewById(R.id.btnLocPolice);
        locPoliceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telNum = locPoliceBtn.getText().toString();
                StartCall(telNum);
            }
        });

        locBrgyBtn = (Button) findViewById(R.id.btnPoblacion);
        locBrgyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telNum = locBrgyBtn.getText().toString();
                StartCall(telNum);
            }
        });

        locFireBtn = (Button) findViewById(R.id.btnLocFire);
        locFireBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telNum = locFireBtn.getText().toString();
                StartCall(telNum);
            }
        });

        locRescueBtn = (Button) findViewById(R.id.btnLocRescue);
        locRescueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telNum = locRescueBtn.getText().toString();
                StartCall(telNum);
            }
        });

    }

    private void StartCall(String telNum){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + telNum));
        if (ActivityCompat.checkSelfPermission(Hotlines.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(callIntent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Hotlines.this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //monitor phone call activities
    private class PhoneCallListener extends PhoneStateListener {

        private boolean isPhoneCalling = false;

        String LOG_TAG = "LOGGING 123";

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {

            if (TelephonyManager.CALL_STATE_RINGING == state) {
                // phone ringing
                Log.i(LOG_TAG, "RINGING, number: " + incomingNumber);
            }

            if (TelephonyManager.CALL_STATE_OFFHOOK == state) {
                // active
                Log.i(LOG_TAG, "OFFHOOK");

                isPhoneCalling = true;
            }

            if (TelephonyManager.CALL_STATE_IDLE == state) {
                // run when class initial and phone call ended,
                // need detect flag from CALL_STATE_OFFHOOK
                Log.i(LOG_TAG, "IDLE");

                if (isPhoneCalling) {

                    Log.i(LOG_TAG, "restart app");

                    // restart app
                    /*Intent i = getBaseContext().getPackageManager()
                            .getLaunchIntentForPackage(
                                    getBaseContext().getPackageName());
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);*/

                    isPhoneCalling = false;
                }

            }
        }
    }
}
