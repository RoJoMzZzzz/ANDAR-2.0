package com.research.andrade.andar;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class CallLogs extends AppCompatActivity {

    private Toolbar toolbar;
    private ListView callLogLv;
    private ArrayList<String> listItems=new ArrayList<String>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_logs);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Call Logs");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Database db = new Database(this);



        Cursor res = db.getAllCalls();
        adapter=new ArrayAdapter<String>(this,
                R.layout.contacts,
                listItems);

        while (res.moveToNext()){
            listItems.add("Number: "+res.getString(1)+"\nDepartment: "+res.getString(2)+"\nDate: "+res.getString(3)+"\nTime: "+res.getString(4));
            adapter.notifyDataSetChanged();
        }

        callLogLv = (ListView) findViewById(R.id.lvCallLogs);
        callLogLv.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            CallLogs.this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
