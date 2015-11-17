package com.example.wealthpack_9.firstaid;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TreeMap<String, ArrayList<FirstAidSteps>> firstAidMap = readJsonData("data.json", getBaseContext());

        Button buttons[] = {
                (Button) findViewById(R.id.button_1),
                (Button) findViewById(R.id.button_2),
                (Button) findViewById(R.id.button_3),
                (Button) findViewById(R.id.button_4),
                (Button) findViewById(R.id.button_5),
                (Button) findViewById(R.id.button_6),
        };

        int i = 0;

        for (TreeMap.Entry<String, ArrayList<FirstAidSteps>> entry: firstAidMap.entrySet()) {
            if (i < 6) {
                buttons[i].setText(entry.getKey());
                i++;
            }
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
        Context con = view.getContext();
        readJsonData("data.json", con);
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

            for (TreeMap.Entry<String, ArrayList<FirstAidSteps>> entry : firstAidMap.entrySet()) {
                Log.v(TAG, "Accident: " + entry.getKey());

                for (FirstAidSteps f: entry.getValue()) {
                    Log.v(TAG, "StepName: " + f.getStepName());

                    for (String s: f.getSteps()) {
                        Log.v(TAG, "Steps: " + s);
                    }
                }
            }

        } catch (JSONException | IOException e) {
            e.printStackTrace();
            return null;
        }

        return firstAidMap;
    }

    /*
     *     Data structure for storing JSON First Aid data
     */
    public class FirstAidSteps {
        private final String stepName;
        private final ArrayList<String> steps;

        public FirstAidSteps(String stepName, ArrayList<String> steps) {
            this.stepName = stepName;
            this.steps = steps;
        }

        public String getStepName() {
            return stepName;
        }

        public ArrayList<String> getSteps() {
            return steps;
        }
    }
}