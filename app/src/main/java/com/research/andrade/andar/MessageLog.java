package com.research.andrade.andar;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MessageLog extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView lvMessLogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_log);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Message Logs");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Database db = new Database(this);

        ArrayList<MessageResults> messageResults = GetMessageResults();

        lvMessLogs = (ListView) findViewById(R.id.lvMessageLog);

        lvMessLogs.setAdapter(new CustomBaseAdapter(this, messageResults));

        lvMessLogs.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object o = lvMessLogs.getItemAtPosition(position);
                MessageResults fullObject = (MessageResults) o;
                Toast.makeText(MessageLog.this, "You have chosen: " + " " + fullObject.getReceiver(), Toast.LENGTH_LONG).show();
            }
        });



    }

    private ArrayList<MessageResults> GetMessageResults(){
        ArrayList<MessageResults> results = new ArrayList<MessageResults>();
        Database db = new Database(this);
        MessageResults sr = new MessageResults();

        Cursor res = db.getAllMessages();
        while (res.moveToNext()){
            sr = new MessageResults();
            sr.setReceiver(res.getString(2));
            sr.setDay(res.getString(3));
            sr.setBody(res.getString(1));
            sr.setTime(res.getString(4));
            results.add(sr);
        }

        /*sr.setReceiver("Justin Schultz");
        sr.setBody("San Francisco, CA");
        sr.setTime("415-555-1234");
        sr.setDay("Monday");
        results.add(sr);

        sr = new MessageResults();
        sr.setReceiver("Jane Doe");
        sr.setBody("Las Vegas, NV");
        sr.setTime("702-555-1234");
        sr.setDay("Monday");
        results.add(sr);

        sr = new MessageResults();
        sr.setReceiver("Lauren Sherman");
        sr.setBody("San Francisco, CA");
        sr.setTime("415-555-1234");
        sr.setDay("Monday");
        results.add(sr);

        sr = new MessageResults();
        sr.setReceiver("Fred Jones");
        sr.setBody("Minneapolis, MN");
        sr.setTime("612-555-8214");
        sr.setDay("Monday");
        results.add(sr);

        sr = new MessageResults();
        sr.setReceiver("Bill Withers");
        sr.setBody("Los Angeles, CA");
        sr.setTime("424-555-8214");
        sr.setDay("Monday");
        results.add(sr);

        sr = new MessageResults();
        sr.setReceiver("Donald Fagen");
        sr.setBody("Los Angeles, CA");
        sr.setTime("424-555-1234");
        sr.setDay("Monday");
        results.add(sr);

        sr = new MessageResults();
        sr.setReceiver("Steve Rude");
        sr.setBody("Oakland, CA");
        sr.setTime("515-555-2222");
        sr.setDay("Monday");
        results.add(sr);

        sr = new MessageResults();
        sr.setReceiver("Roland Bloom");
        sr.setBody("Chelmsford, MA");
        sr.setTime("978-555-1111");
        sr.setDay("Monday");
        results.add(sr);

        sr = new MessageResults();
        sr.setReceiver("Sandy Baguskas");
        sr.setBody("Chelmsford, MA");
        sr.setTime("978-555-2222");
        sr.setDay("Monday");
        results.add(sr);

        sr = new MessageResults();
        sr.setReceiver("Scott Taylor");
        sr.setBody("Austin, TX");
        sr.setTime("512-555-2222");
        sr.setDay("Monday");
        results.add(sr);*/

        return results;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            MessageLog.this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
