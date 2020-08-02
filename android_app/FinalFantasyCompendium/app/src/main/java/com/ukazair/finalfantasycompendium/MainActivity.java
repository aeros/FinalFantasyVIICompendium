package com.ukazair.finalfantasycompendium;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClickWeapons(View view) {
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        int intCategory = 1;
        intent.putExtra("CATEGORY", (int) intCategory);
        startActivity(intent);
    }

    public void onClickArmors(View view) {
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        int intCategory = 2;
        intent.putExtra("CATEGORY", (int) intCategory);
        startActivity(intent);
    }
}