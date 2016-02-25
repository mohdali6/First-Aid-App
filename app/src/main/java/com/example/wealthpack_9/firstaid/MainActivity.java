package com.example.wealthpack_9.firstaid;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    TreeMap<String, ArrayList<FirstAidSteps>> firstAidMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firstAidMap = readJsonData("data.json", getBaseContext());

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.parent_linear_layout);

        for (TreeMap.Entry<String, ArrayList<FirstAidSteps>> entry : firstAidMap.entrySet()) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.cardview_layout, null);
            TextView textView = (TextView) view.findViewById(R.id.textView);
            textView.setText(entry.getKey());
            linearLayout.addView(view);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //Responds to button click
    public void showTips(View view) {
        CardView cardView = (CardView) view;
        TextView textView = (TextView) cardView.getChildAt(0).findViewById(R.id.textView);
        String buttonText = textView.getText().toString();

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putParcelableArrayListExtra("accident_name", firstAidMap.get(buttonText));
        startActivity(intent);
    }

    /*
    *    Loads JSON data from a file in Assets
    */
    public String loadJsonFromAsset(String fileName, Context con) throws IOException {
        String text = null;
        InputStream is = con.getAssets().open(fileName, AssetManager.ACCESS_BUFFER);
        int size = is.available();
        byte[] buffer = new byte[size];
        is.read(buffer);
        is.close();
        text = new String(buffer, "UTF-8");
        return text;
    }

    /*
    *    Reads JSON data recieved as String parameter
    */
    public TreeMap<String, ArrayList<FirstAidSteps>> readJsonData(String fileName, Context con) {
        TreeMap<String, ArrayList<FirstAidSteps>> firstAidMap = new TreeMap<>();

        try {
            String accidentName = null;
            String stepName = null;
            ArrayList<String> steps;
            FirstAidSteps firstAidStepsObj;
            ArrayList<FirstAidSteps> firstAidStepsObjArrayList;

            JSONObject accidents = new JSONObject(loadJsonFromAsset(fileName, con));
            JSONArray accidentKeys = accidents.names();

            for (int i = 0; i < accidents.length(); i++) {
                accidentName = accidentKeys.getString(i);
                JSONObject firstAidSteps = accidents.getJSONObject(accidentName);
                JSONArray firstAidStepsKeys = firstAidSteps.names();
                firstAidStepsObjArrayList = new ArrayList<>();

                for (int j = 0; j < firstAidSteps.length(); j++) {
                    stepName = firstAidStepsKeys.getString(j);
                    JSONArray aidSteps = firstAidSteps.getJSONArray(stepName);

                    steps = new ArrayList<>();

                    if (aidSteps != null) {
                        for (int k = 0; k < aidSteps.length(); k++) {
                            steps.add(aidSteps.get(k).toString());
                        }
                    }

                    firstAidStepsObj = new FirstAidSteps(stepName, steps);
                    firstAidStepsObjArrayList.add(firstAidStepsObj);
                }

                firstAidMap.put(accidentName, firstAidStepsObjArrayList);
            }

        } catch (JSONException | IOException e) {
            e.printStackTrace();
            return null;
        }

        return firstAidMap;
    }
}