package com.ukazair.finalfantasycompendium;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity {

    JSONArray results;
    int category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        category = getIntent().getIntExtra("CATEGORY", 0);

        TextView tv_category = (TextView) findViewById(R.id.tv_category);
        if (category == 1) {
            tv_category.setText("Weapons");
        }
        else if (category == 2) {
            tv_category.setText("Armors");
        }

        try {
            setupListView();
        }
        catch (JSONException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        Button btn_results_home = (Button) findViewById(R.id.btn_results_home);
        btn_results_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultsActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupListView() throws JSONException {
        try {
            results = new JSONArray(getIntent().getStringExtra("results"));
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        ListView lv_results = (ListView) findViewById(R.id.lv_results);
        if (category == 1) {
            ArrayList<WeaponDataModel> dmArrayList = new ArrayList<>();
            for (int i = 0; i < results.length(); i++) {
                WeaponDataModel dModel = new WeaponDataModel();
                JSONObject dataObject = results.getJSONObject(i);

                dModel.setCharacter(dataObject.getString("character"));
                dModel.setName(dataObject.getString("name"));
                dModel.setAttack(dataObject.getString("attack"));
                dModel.setAccuracy(dataObject.getString("accuracy"));
                dModel.setMagic(dataObject.getString("magic"));
                dModel.setCost(dataObject.getString("cost"));

                dmArrayList.add(dModel);
            }
            ArrayAdapter<WeaponDataModel> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, dmArrayList);
            lv_results.setAdapter(adapter);
        }

        else if (category == 2) {
            ArrayList<ArmorDataModel> dmArrayList = new ArrayList<>();
            for (int i = 0; i < results.length(); i++) {
                ArmorDataModel dModel = new ArmorDataModel();
                JSONObject dataObject = results.getJSONObject(i);

                dModel.setName(dataObject.getString("name"));
                dModel.setDef(dataObject.getString("def"));
                dModel.setDef_p(dataObject.getString("def%"));
                dModel.setMdef(dataObject.getString("mdef"));
                dModel.setMdef_p(dataObject.getString("mdef%"));
                dModel.setCost(dataObject.getString("cost"));

                dmArrayList.add(dModel);
            }
            ArrayAdapter<ArmorDataModel> adapter = new ArrayAdapter<>(this,
                    android.R.layout.simple_list_item_1, dmArrayList);
            lv_results.setAdapter(adapter);
        }


    }

}
