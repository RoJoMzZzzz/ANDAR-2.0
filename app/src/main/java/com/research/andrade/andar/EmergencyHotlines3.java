package com.research.andrade.andar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class EmergencyHotlines3 extends AppCompatActivity {

    private Toolbar toolbar;
    private Spinner localSpn, nearbySpn, ncrSpn;
    private Button localBtn,nearbyBtn1,nearbyBtn2,nearbyBtn3,ncrBtn1,ncrBtn2,ncrBtn3,ncrBtn4;
    private ArrayAdapter localAdp, nearbyAdp, ncrAdp;
    private String telNum="", department="", dept="", locDept="", nearbyDept="", ncrDept="";
    private Database db = new Database(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_hotlines3);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Emergency Hotlines");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            EmergencyHotlines3.this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
