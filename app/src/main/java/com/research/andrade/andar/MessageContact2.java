package com.research.andrade.andar;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MessageContact2 extends AppCompatActivity {

    private Toolbar toolbar;
    private ArrayAdapter sendAdp, locAdp, assAdp;
    private Spinner sendSpn, locSpn, assSpn;
    private EditText othersEdt, usernameEdt;
    private Button sendBtn;
    private String sendData="", locData="", assData="", remarkses="";
    private ArrayList<String> listItems=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_contact2);

        final Database db = new Database(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Message Contacts");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sendSpn = (Spinner) findViewById(R.id.spnSendTo);
        locSpn = (Spinner) findViewById(R.id.spnLoc);
        assSpn = (Spinner) findViewById(R.id.spnAssistance);
        othersEdt = (EditText) findViewById(R.id.editText);
        usernameEdt = (EditText) findViewById(R.id.edtUserName);
        sendBtn = (Button) findViewById(R.id.btnSendEm);

        Resources res = getResources();
        final String[] sendTo = res.getStringArray(R.array.send);
        final String[] loc = res.getStringArray(R.array.loc);
        final String[] ass = res.getStringArray(R.array.ass);

        sendAdp = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, sendTo);
        locAdp = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, loc);
        assAdp = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, ass);

        sendSpn.setAdapter(sendAdp);
        locSpn.setAdapter(locAdp);
        assSpn.setAdapter(assAdp);


        sendSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sendData = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        locSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                locData = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        assSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String spnItem = parent.getItemAtPosition(position).toString();
                if (spnItem.equalsIgnoreCase("Others")) {
                    othersEdt.setEnabled(true);
                    othersEdt.setVisibility(View.VISIBLE);
                } else{
                    othersEdt.getText().clear();
                    othersEdt.setEnabled(false);
                    othersEdt.setVisibility(View.INVISIBLE);
                }

                assData = spnItem;

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(usernameEdt.getText().toString().trim().length() == 0){
                    usernameEdt.setError("Please fill-up this field");
                } else {

                    Cursor res1 = db.getAllContact();

                    if(sendData.equalsIgnoreCase("both")){
                        listItems.add("09951651215");
                        while (res1.moveToNext()){
                            listItems.add(res1.getString(1));
                        }
                    } else if(sendData.equalsIgnoreCase("Barangay Poblacion")){
                        listItems.add("09951651215");
                    } else if(sendData.equalsIgnoreCase("Emergency Contacts")){
                        while (res1.moveToNext()){
                            listItems.add(res1.getString(1));
                        }
                    }

                    if (assData.equalsIgnoreCase("Others")){
                        assData = othersEdt.getText().toString();
                    }
                    Toast.makeText(MessageContact2.this,"Name: "+usernameEdt.getText().toString()+"\n"+"Location: "+locData+"\n"+"Assistance for: "+assData,Toast.LENGTH_SHORT).show();
                    for (int i = 0; i < listItems.size(); i++)
                    {

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

                        final String currDate = ""+month+"/"+day+"/"+year+"\n"+hour+":"+minutes+" "+chk;

                        String message = "ANDAR EMERGENCY MESSAGE\n\n"+"NAME: "+usernameEdt.getText().toString()+"\n"+"LOCATION: "+locData+"\n"+"ASSISTANCE FOR: "+assData;
                        String tempMobileNumber = listItems.get(i).toString();
                        MultipleSMS(tempMobileNumber, message);

                        boolean insCont = db.insertMessage(listItems.get(i).toString(), currDate, remarkses, message);
                        if(insCont){
                            //Toast.makeText(MessageContact2.this, "inserted", Toast.LENGTH_LONG).show();
                        }
                        else{
                            //Toast.makeText(MessageContact2.this, "not inserted", Toast.LENGTH_LONG).show();
                        }

                    }
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            MessageContact2.this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void MultipleSMS(String phoneNumber, final String message) {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        final Database db = new Database(this);

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


        final String currDate = ""+month+"/"+day+"/"+year+"\n"+hour+":"+minutes+" "+chk;

        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(
                SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(this, 0,
                new Intent(DELIVERED), 0);

        // ---when the SMS has been sent---
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        ContentValues values = new ContentValues();
                        for (int i = 0; i < listItems.size() - 1; i++) {
                            values.put("address", listItems.get(i).toString());
                            // txtPhoneNo.getText().toString());
                            values.put("body", message);

                        }
                        getContentResolver().insert(
                                Uri.parse("content://sms/sent"), values);
                        Toast.makeText(getBaseContext(), "SMS sent",
                                Toast.LENGTH_SHORT).show();
                        remarkses="Sent";
                        break;

                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        remarkses="Not Sent";
                        break;

                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service",
                                Toast.LENGTH_SHORT).show();
                        remarkses="Not Sent";
                        break;

                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        remarkses="Not Sent";
                        break;

                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off",
                                Toast.LENGTH_SHORT).show();
                        remarkses="Not Sent";
                        break;

                }
            }
        }, new IntentFilter(SENT));

        // ---when the SMS has been delivered---
        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }

}
