package com.example.android.menu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.menu.CanvasDrawing.CanvasActivity;


public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        Button button = (Button) view;

        if(button.getTag().toString() == getResources().getString(R.string.william_chart)) {
            Intent iToWilliamChartActivity = new Intent(this, WilliamChartActivity.class);
            startActivity(iToWilliamChartActivity);
        }

        if(button.getTag().toString() == getResources().getString(R.string.canvas)) {
            Intent iToCanvasActivity = new Intent(this, CanvasActivity.class);
            startActivity(iToCanvasActivity);
        }
    }
}