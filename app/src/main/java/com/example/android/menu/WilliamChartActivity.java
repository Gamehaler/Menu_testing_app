package com.example.android.menu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.db.chart.model.LineSet;
import com.db.chart.model.Point;
import com.db.chart.view.AxisController;
import com.db.chart.view.LineChartView;
import com.db.chart.view.animation.Animation;
import com.db.chart.view.animation.easing.BaseEasingMethod;
import com.db.chart.view.animation.easing.BounceEase;

public class WilliamChartActivity extends AppCompatActivity {

    String[] labels = {"", "", "", ""};
    float[] values = {0f, 1f, 1f, 0f};
    LineSet dataSet = new LineSet(labels, values);
    Animation animation = new Animation(5000);
    NekiEasing nekiEasing = new NekiEasing();
    BounceEase sineEase = new BounceEase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_william_chart);

        int[] order = {0, 1, 2, 3, 4};
        //animation.setOverlap(0f, order);
        animation.setEasing(sineEase);

        LineChartView lineChart = (LineChartView) findViewById(R.id.linechart2);
        lineChart.setXLabels(AxisController.LabelPosition.NONE);
        lineChart.setYLabels(AxisController.LabelPosition.NONE);
        //LineChartView lineChart = new LineChartView(getApplicationContext());
        Point nekaTocka = new Point("", 0f);
        dataSet.addPoint(nekaTocka);
        dataSet.beginAt(0);
        lineChart.addData(dataSet);
        lineChart.show(animation);
    }

    public class NekiEasing extends BaseEasingMethod {

        public NekiEasing() {super();}

        @Override
        protected float easeOut(float time) {
            return time;
        }

        @Override
        protected float easeInOut(float time) {
            return time;
        }

        @Override
        protected float easeIn(float time) {
            return time;
        }
    }
}
