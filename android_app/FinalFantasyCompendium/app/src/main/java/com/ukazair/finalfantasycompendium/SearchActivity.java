package com.ukazair.finalfantasycompendium;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private String url = "http://10.0.2.2:5000/FF7/api";

    Context context;
    int category; // 1: weapons; 2: armors
    Button btnSearch;
    ToggleButton btn1, btn2, btn3, btn4;
    EditText hint1, hint2, hint3, hint4;
    String type, cat1, cat2, cat3, cat4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        context = this;

        Bundle bundle = getIntent().getExtras();
        category = bundle.getInt("CATEGORY");

        TextView attr1 = (TextView) findViewById(R.id.textAttr1);
        TextView attr2 = (TextView) findViewById(R.id.textAttr2);
        TextView attr3 = (TextView) findViewById(R.id.textAttr3);
        TextView attr4 = (TextView) findViewById(R.id.textAttr4);

        btn1 = (ToggleButton) findViewById(R.id.toggleButton1);
        btn2 = (ToggleButton) findViewById(R.id.toggleButton2);
        btn3 = (ToggleButton) findViewById(R.id.toggleButton3);
        btn4 = (ToggleButton) findViewById(R.id.toggleButton4);

        hint1 = (EditText) findViewById(R.id.editTextAttr1);
        hint2 = (EditText) findViewById(R.id.editTextAttr2);
        hint3 = (EditText) findViewById(R.id.editTextAttr3);
        hint4 = (EditText) findViewById(R.id.editTextAttr4);

        if (category == 1) {
            attr1.setText("Attack");
            attr2.setText("Accuracy");
            attr3.setText("Magic");
            attr4.setText("Cost");
            hint1.setHint("1-100");
            hint2.setHint("1-255");
            hint3.setHint("1-55");
            hint4.setHint("1-19000");
        }
        if (category == 2) {
            attr1.setText("Defense");
            attr2.setText("Defense %");
            attr3.setText("M.Defense");
            attr4.setText("M.Defense %");
            hint1.setHint("1-100");
            hint2.setHint("1-100");
            hint3.setHint("1-100");
            hint4.setHint("1-100");
        }


        btnSearch = (Button) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SearchActivity.this, "Searching... ",
                        Toast.LENGTH_SHORT).show();

                // Check Toggle Buttons
                ToggleButton[] buttons = new ToggleButton[]{btn1, btn2, btn3, btn4};
                String[] attr_filters = {"", "", "", ""};
                for (int i = 0; i < buttons.length; i++) {
                    if (buttons[i].isChecked()) {
                        attr_filters[i] = "<=";
                    } else {
                        attr_filters[i] = ">=";
                    }
                }

                // Read value for each attribute, default to 0 if null or invalid
                int attr1 = parseToInt(hint1.getText().toString(), 0);
                int attr2 = parseToInt(hint2.getText().toString(), 0);
                int attr3 = parseToInt(hint3.getText().toString(), 0);
                int attr4 = parseToInt(hint4.getText().toString(), 0);

                ContentValues cv = new ContentValues();

                cv.put("attr1", attr1);
                cv.put("attr2", attr2);
                cv.put("attr3", attr3);
                cv.put("attr4", attr4);
                cv.put("attr1_filter", attr_filters[0]);
                cv.put("attr2_filter", attr_filters[1]);
                cv.put("attr3_filter", attr_filters[2]);
                cv.put("attr4_filter", attr_filters[3]);

                try {
                    doJSONRequest(cv);
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }

                // intent = new Intent(SearchActivity.this, ResultsActivity.class);
                // startActivity(intent);

            }
        });

    }

    public void onClickSearch(View view) {
        Intent intent = new Intent(SearchActivity.this, ResultsActivity.class);
        intent.putExtra("CATEGORY", (int) category);
        startActivity(intent);
    }

    // Build JSON request payload using user inputs, send request, and start results activity after response is received.
    private void doJSONRequest(ContentValues cv) throws JSONException {
        JSONObject payload = buildPayload(cv);

        // Send JSON object request with payload, store the response, and then start results activity
        Request jsonRequest = new JsonObjectRequest(url, payload,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String tag = "jsonRequest.onResponse";
                        Log.d(tag, "JSONObject Response received");
                        try {
                            if (response.has("results")){
                                // Pass JSONArray as string to the result activity
                                Log.d(tag, "Extracting JSONArray results and storing in intent extra as str");
                                JSONArray results  = response.getJSONArray("results");
                                Intent intent = new Intent(context, ResultsActivity.class);
                                intent.putExtra("results", results.toString());
                                intent.putExtra("CATEGORY", category);
                                Log.d(tag, "Starting results activity");
                                startActivity(intent);
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "No results found", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Log.e(tag, e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );

        // Send request using volley queue, which handles it in a separate thread
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonRequest);
    }

    private JSONObject buildPayload(ContentValues cv) throws JSONException {
        JSONObject payload = new JSONObject();
        if (category == 1) {
            type = "weapons";
            cat1 = "attack";
            cat2 = "accuracy";
            cat3 = "magic";
            cat4 = "cost";
        }
        else if (category == 2) {
            type = "armors";
            cat1 = "def";
            cat2 = "def%";
            cat3 = "mdef";
            cat4 = "mdef%";
        }

        payload.put("type", type);
        JSONObject attributes = new JSONObject();
        payload.put("attributes", attributes);

        attributes.put(cat1,
                new JSONArray()
                        .put(cv.getAsString("attr1_filter"))
                        .put(cv.getAsInteger("attr1"))
        );

        attributes.put(cat2,
                new JSONArray()
                        .put(cv.getAsString("attr2_filter"))
                        .put(cv.getAsInteger("attr2"))
        );

        attributes.put(cat3,
                new JSONArray()
                        .put(cv.getAsString("attr3_filter"))
                        .put(cv.getAsInteger("attr3"))
        );

        attributes.put(cat4,
                new JSONArray()
                        .put(cv.getAsString("attr4_filter"))
                        .put(cv.getAsInteger("attr4"))
        );


        return payload;
    }

    private static int parseToInt(String stringToParse, int defaultValue) {
        try {
            return Integer.parseInt(stringToParse);
        } catch (NumberFormatException ex) {
            return defaultValue; //Use default value if parsing failed
        }
    }





}
