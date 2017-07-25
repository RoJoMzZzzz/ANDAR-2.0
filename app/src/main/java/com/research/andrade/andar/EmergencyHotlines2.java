package com.research.andrade.andar;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

public class EmergencyHotlines2 extends AppCompatActivity {

    private Toolbar toolbar;
    private Spinner localSpn, nearbySpn, ncrSpn;
    private Button localBtn,nearbyBtn1,nearbyBtn2,nearbyBtn3,ncrBtn1,ncrBtn2,ncrBtn3,ncrBtn4;
    private ArrayAdapter localAdp, nearbyAdp, ncrAdp;
    private String telNum="", department="", dept="", locDept="", nearbyDept="", ncrDept="";
    private Database db = new Database(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_hotlines2);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Emergency Hotlines");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        localSpn = (Spinner) findViewById(R.id.spnLocal);
        nearbySpn = (Spinner) findViewById(R.id.spnNearby);
        ncrSpn = (Spinner) findViewById(R.id.spnNcr);
        localBtn = (Button) findViewById(R.id.btnLocal);
        nearbyBtn1 = (Button) findViewById(R.id.btnNearby1);
        nearbyBtn2 = (Button) findViewById(R.id.btnNearby2);
        nearbyBtn3 = (Button) findViewById(R.id.btnNearby3);
        ncrBtn1 = (Button) findViewById(R.id.btnNcr1);
        ncrBtn2 = (Button) findViewById(R.id.btnNcr2);
        ncrBtn3 = (Button) findViewById(R.id.btnNcr3);
        ncrBtn4 = (Button) findViewById(R.id.btnNcr4);

        nearbyBtn2.setVisibility(View.GONE);
        nearbyBtn3.setVisibility(View.GONE);
        ncrBtn2.setVisibility(View.GONE);
        ncrBtn3.setVisibility(View.GONE);
        ncrBtn4.setVisibility(View.GONE);

        Resources res = getResources();
        final String[] nearby = res.getStringArray(R.array.nearby);
        final String[] local = res.getStringArray(R.array.local);
        final String[] ncr = res.getStringArray(R.array.ncr);

        localAdp = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, local);
        nearbyAdp = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, nearby);
        ncrAdp = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, ncr);

        localSpn.setAdapter(localAdp);
        nearbySpn.setAdapter(nearbyAdp);
        ncrSpn.setAdapter(ncrAdp);

        // add PhoneStateListener
        PhoneCallListener phoneListener = new PhoneCallListener();
        TelephonyManager telephonyManager = (TelephonyManager) this
                .getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(phoneListener,PhoneStateListener.LISTEN_CALL_STATE);

        ncrSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).toString().equalsIgnoreCase("NDRRMC")){
                    ncrBtn1.setText("(02) 9111406");
                    ncrBtn2.setVisibility(View.VISIBLE);
                    ncrBtn3.setVisibility(View.VISIBLE);
                    ncrBtn2.setText("(02) 9122665");
                    ncrBtn3.setText("(02) 9125668");
                    ncrBtn4.setVisibility(View.GONE);
                    ncrDept="NDRRMC";
                } else if(parent.getItemAtPosition(position).toString().equalsIgnoreCase("Bureau of Fire Protection")){
                    ncrBtn1.setText("(02) 7295166");
                    ncrBtn2.setVisibility(View.VISIBLE);
                    ncrBtn3.setVisibility(View.VISIBLE);
                    ncrBtn2.setText("(02) 4106254");
                    ncrBtn3.setText("(02) 4318859");
                    ncrBtn4.setVisibility(View.VISIBLE);
                    ncrBtn4.setText("(02) 4071230");
                    ncrDept="Bureau of Fire Protection";
                }  else if(parent.getItemAtPosition(position).toString().equalsIgnoreCase("PNP Hotline Patrol")){
                    ncrBtn1.setText("117");
                    ncrBtn2.setVisibility(View.GONE);
                    ncrBtn3.setVisibility(View.GONE);
                    ncrBtn4.setVisibility(View.GONE);
                    ncrDept="PNP Hotline Patrol";
                } else if(parent.getItemAtPosition(position).toString().equalsIgnoreCase("DOTC")){
                    ncrBtn1.setText("7890");
                    ncrBtn2.setVisibility(View.VISIBLE);
                    ncrBtn3.setVisibility(View.GONE);
                    ncrBtn2.setText("09188848484");
                    ncrBtn4.setVisibility(View.GONE);
                    ncrDept="DOTC";
                } else if(parent.getItemAtPosition(position).toString().equalsIgnoreCase("MMDA Metrobase")){
                    ncrBtn1.setText("8185150");
                    ncrBtn2.setVisibility(View.GONE);
                    ncrBtn3.setVisibility(View.GONE);
                    ncrBtn4.setVisibility(View.GONE);
                    ncrDept="MMDA Metrobase";
                } else if(parent.getItemAtPosition(position).toString().equalsIgnoreCase("MMDA Flood Control")){
                    ncrBtn1.setText("8824177");
                    ncrBtn2.setVisibility(View.VISIBLE);
                    ncrBtn3.setVisibility(View.GONE);
                    ncrBtn2.setText("8820925");
                    ncrBtn4.setVisibility(View.GONE);
                    ncrDept="MMDA Flood Control";
                } else if(parent.getItemAtPosition(position).toString().equalsIgnoreCase("DPWH")){
                    ncrBtn1.setText("(02) 3043713");
                    ncrBtn2.setVisibility(View.GONE);
                    ncrBtn3.setVisibility(View.GONE);
                    ncrBtn4.setVisibility(View.GONE);
                    ncrDept="DPWH";
                } else if(parent.getItemAtPosition(position).toString().equalsIgnoreCase("Red Cross")){
                    ncrBtn1.setText("143");
                    ncrBtn2.setVisibility(View.VISIBLE);
                    ncrBtn3.setVisibility(View.GONE);
                    ncrBtn2.setText("(02) 9111876");
                    ncrBtn4.setVisibility(View.GONE);
                    ncrDept="Red Cross";
                } else if(parent.getItemAtPosition(position).toString().equalsIgnoreCase("PAGASA")){
                    ncrBtn1.setText("(02) 4338526");
                    ncrBtn2.setVisibility(View.GONE);
                    ncrBtn3.setVisibility(View.GONE);
                    ncrBtn4.setVisibility(View.GONE);
                    ncrDept="PAGASA";
                } else if(parent.getItemAtPosition(position).toString().equalsIgnoreCase("Philippine Coast Guard")){
                    ncrBtn1.setText("(02) 5273877");
                    ncrBtn2.setVisibility(View.VISIBLE);
                    ncrBtn3.setVisibility(View.VISIBLE);
                    ncrBtn2.setText("(02) 5278481");
                    ncrBtn3.setText("09177243682");
                    ncrBtn4.setVisibility(View.GONE);
                    ncrDept="Philippine Coast Guard";
                } else if(parent.getItemAtPosition(position).toString().equalsIgnoreCase("DOH")){
                    ncrBtn1.setText("7111001");
                    ncrBtn2.setVisibility(View.GONE);
                    ncrBtn3.setVisibility(View.GONE);
                    ncrBtn4.setVisibility(View.GONE);
                    ncrDept="DOH";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        nearbySpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).toString().equalsIgnoreCase("Bonifacio Fire Department")){
                    nearbyBtn1.setText("4946667");
                    nearbyBtn2.setVisibility(View.GONE);
                    nearbyBtn3.setVisibility(View.GONE);
                    nearbyDept="Bonifacio Fire Department";
                } else if(parent.getItemAtPosition(position).toString().equalsIgnoreCase("Taguig Fire Department")){
                    nearbyBtn1.setText("8370704");
                    nearbyBtn2.setVisibility(View.VISIBLE);
                    nearbyBtn3.setVisibility(View.VISIBLE);
                    nearbyBtn2.setText("5423695");
                    nearbyBtn3.setText("8374496");
                    nearbyDept="Taguig Fire Department";
                } else if(parent.getItemAtPosition(position).toString().equalsIgnoreCase("Makati Fire Department")){
                    nearbyBtn1.setText("8185150");
                    nearbyBtn2.setVisibility(View.GONE);
                    nearbyBtn3.setVisibility(View.GONE);
                    nearbyDept="Makati Fire Department";
                } else if(parent.getItemAtPosition(position).toString().equalsIgnoreCase("Pasig Fire Department")){
                    nearbyBtn1.setText("6411939");
                    nearbyBtn2.setText("6412815");
                    nearbyBtn2.setVisibility(View.VISIBLE);
                    nearbyBtn3.setVisibility(View.GONE);
                    nearbyDept="Pasig Fire Department";
                } else if(parent.getItemAtPosition(position).toString().equalsIgnoreCase("Pasay Fire Department")){
                    nearbyBtn1.setText("8442120");
                    nearbyBtn2.setText("8436523");
                    nearbyBtn2.setVisibility(View.VISIBLE);
                    nearbyBtn3.setVisibility(View.GONE);
                    nearbyDept="Pasay Fire Department";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        localSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(parent.getItemAtPosition(position).toString().equalsIgnoreCase("Police")){
                    localBtn.setText("6428235");
                    locDept="Local Police";
                } else if(parent.getItemAtPosition(position).toString().equalsIgnoreCase("Fire")){
                    localBtn.setText("6411365");
                    locDept="Local Fire";
                } else if(parent.getItemAtPosition(position).toString().equalsIgnoreCase("Rescue")){
                    localBtn.setText("6425159");
                    locDept="Local Rescue";
                } else if(parent.getItemAtPosition(position).toString().equalsIgnoreCase("Barangay Poblacion")){
                    localBtn.setText("6415502");
                    locDept="Barangay Poblacion";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        localBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telNum = localBtn.getText().toString();
                dept= localSpn.getSelectedItem().toString();
                StartCall(telNum);
            }
        });

        nearbyBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telNum = nearbyBtn1.getText().toString();
                dept=nearbySpn.getSelectedItem().toString();
                StartCall(telNum);
            }
        });

        nearbyBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telNum = nearbyBtn2.getText().toString();
                dept=nearbySpn.getSelectedItem().toString();
                StartCall(telNum);
            }
        });

        nearbyBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telNum = nearbyBtn3.getText().toString();
                dept=nearbySpn.getSelectedItem().toString();
                StartCall(telNum);
            }
        });

        ncrBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telNum = ncrBtn1.getText().toString();
                dept=ncrSpn.getSelectedItem().toString();
                StartCall(telNum);
            }
        });

        ncrBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telNum = ncrBtn2.getText().toString();
                dept=ncrSpn.getSelectedItem().toString();
                StartCall(telNum);
            }
        });

        ncrBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telNum = ncrBtn3.getText().toString();
                dept=ncrSpn.getSelectedItem().toString();
                StartCall(telNum);
            }
        });

        ncrBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                telNum = ncrBtn4.getText().toString();
                dept=ncrSpn.getSelectedItem().toString();
                StartCall(telNum);
            }
        });

    }

    private void StartCall(String telNum){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + telNum));
        if (ActivityCompat.checkSelfPermission(EmergencyHotlines2.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        Calendar c = Calendar.getInstance();
        int minutes = c.get(Calendar.MINUTE);
        int hour = c.get(Calendar.HOUR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DATE);
        int ampm = c.get(Calendar.AM_PM);
        int year = c.get(Calendar.YEAR);
        String chk;
        if(ampm == 1)
            chk = "PM";
        else
            chk = "AM";

        final String currDate = ""+month+"/"+day+"/"+year;
        final String currTime = hour+":"+minutes+" "+chk;

        boolean insCont = db.insertCall(telNum, dept, currDate, currTime);
        if(insCont){
            //Toast.makeText(this, "inserted", Toast.LENGTH_LONG).show();
        }

        else{
            //Toast.makeText(this, "not inserted", Toast.LENGTH_LONG).show();
        }


        startActivity(callIntent);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            EmergencyHotlines2.this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
