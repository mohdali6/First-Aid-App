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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        readJSONData("data.json", con);
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
    *    Reads JSON data recievet String parameter
    */
    public String readJSONData(String fileName, Context con) {

        try {
            JSONObject jsonObject = new JSONObject(loadJsonFromAsset(fileName, con));
            JSONArray accidentKeys = jsonObject.names();

            for(int i=0; i<jsonObject.length(); i++) {
                Log.v(TAG, accidentKeys.getString(i));
            }

        } catch (JSONException | IOException e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }

    /*
     *     Data structure for storing JSON First Aid data
     */
    public class FirstAidSteps {
        private final String stepName;
        private final String[] steps;

        public FirstAidSteps(String stepName, String[] steps) {
            this.stepName = stepName;
            this.steps = steps;
        }

        public String getStepName() {
            return stepName;
        }

        public String[] getSteps() {
            return steps;
        }
    }
}