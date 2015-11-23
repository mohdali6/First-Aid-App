package com.example.wealthpack_9.firstaid;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent i = getIntent();
        ArrayList<FirstAidSteps> l =  i.getParcelableArrayListExtra("accident_name");
        Toast toast = Toast.makeText(getApplicationContext(), l.toString(), Toast.LENGTH_SHORT);
        toast.show();

        ListView listView = (ListView) findViewById(R.id.list_view_steps);
        StepsAdapter adapter1 = new StepsAdapter(this, l);
        listView.setAdapter(adapter1);
    }
}