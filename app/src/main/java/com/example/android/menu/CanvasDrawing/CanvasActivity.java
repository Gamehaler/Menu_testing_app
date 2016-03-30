package com.example.android.menu.CanvasDrawing;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.LinearLayout;

import com.example.android.menu.R;

public class CanvasActivity extends AppCompatActivity {

    MyView myView;
    LinearLayout linearLayout, innerLinearLayout;
    int x, y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        linearLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.activity_canvas, null);
        innerLinearLayout = (LinearLayout) linearLayout.findViewById(R.id.inner_linear_layout);
        myView = new MyView(this);
        innerLinearLayout.addView(myView);

        setContentView(linearLayout);
    }

    @Override
    protected void onPause() {
        super.onPause();
        myView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        myView.resume();
    }

    public class MyView extends SurfaceView implements Runnable {

        // widith - 1/6 of myView width
        // height - myView height
        float width, height;

        // x_part - 1/20 of width
        // inhale and exhale times are divided by 40 (2 * width = 40 * x_part) and holds are
        // divided by 20 (width = 20 * x_part) when calculating sleep intervals of Thread
        float x_part;

        // y = bx
        // b = y (height) / x (width)
        // b is positive while inhaling and negative while exhaling
        // while holding breath y is equal 0 or 1 depends on the part of the graph
        float bi, be;

        // paints defined in prepPaintBrushes() method that is called from constructor
        Paint bluePaintbrushStroke, redPaintbrushFill;
        // path used to draw a graph
        Path graph = new Path();
        // boolean used to check if variables are set in run
        boolean isSet;

        Thread t = null;
        SurfaceHolder holder;
        //boolean used to check if activity is on screen; and regulate drawing
        boolean isItOk = false;

        /**
         * constructor
         *
         * @param context
         */
        public MyView(Context context) {
            super(context);
            holder = getHolder();
            isSet = false;
            prepPaintBrushes();
        }

        /**
         * method called by t.start() in resume()
         * main method of activity
         */
        @Override
        public void run() {

            while (isItOk){
                if (!holder.getSurface().isValid()) {
                    continue;
                }

                if(!isSet) {
                    width = (myView.getWidth() / 6);
                    height = myView.getHeight();
                    x_part = width /30;
                    x = 0;
                    y = (int) height;
                    drawGraph();

                    bi = -1 * (height / (width * 2));
                    be = height / (width * 2);

                    isSet = true;
                }

                Canvas c = holder.lockCanvas();
                drawing(c);
                holder.unlockCanvasAndPost(c);
            }
        }

        /**
         * Used to call all others drawing methods
         *
         * @param canvas
         */
        protected void drawing(Canvas canvas){
            canvas.drawARGB(255, 255, 255, 255);
            canvas.drawPath(graph, bluePaintbrushStroke);
            drawingCircle(canvas);
        }

        /**
         * Used to draw Graph
         */
        private void drawGraph() {
            graph.moveTo(0, (height - 10));
            graph.lineTo((width * 2), 10);
            graph.moveTo((width * 2), 10);
            graph.lineTo((width * 3), 10);
            graph.moveTo((width * 3), 10);
            graph.lineTo((width * 5), height - 10);
            graph.moveTo((width * 5), height - 10);
            graph.lineTo((width * 6), height - 10);
        }

        /**
         * Used to draw Circle
         *
         * @param c
         */
        private void drawingCircle (Canvas c) {
            Log.v("Test", "" + x_part);
            if(x + x_part > (width * 6)) {
                x = 0;
            }
            // inhale
            if(x < (width * 2)) {
                x += x_part;
                y = (int) (bi * (x - (width * 2)));
            }
            // 1st hold
            if((x >= (width * 2)) && (x < (width * 3))) {
                x += x_part;
                y = 0;


            }
            // exhale
            if((x >= (width * 3)) && ( x < (width * 5))) {
                x += x_part;
                y = (int) (be * (x - (width * 3)));


            }
            // 2nd hold
            if (x >= (width * 5)) {
                x += x_part;
                y = (int) height;

                /*
                try {
                    Thread.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } */
            }
            c.drawCircle(x, y, toPxs(20), redPaintbrushFill);
        }

        /**
         * Gets called from onPause() in CanvasActivity class
         * Stops Thread and sets t to null
         */
        public void pause() {
            isItOk = false;
            while (true){
                try{
                    t.join();
                }catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            t = null;
        }

        /**
         * Gets called from onResume() in CanvasActivity class
         * Creates new Thread and starts it
         */
        public void resume() {
            isItOk = true;
            t = new Thread(this);
            t.start();
        }

        /**
         * Gets called from constructor
         * Prepares brushes for drawing
         */
        private void prepPaintBrushes() {
            bluePaintbrushStroke = new Paint();
            bluePaintbrushStroke.setColor(Color.BLUE);
            bluePaintbrushStroke.setStyle(Paint.Style.STROKE);
            bluePaintbrushStroke.setStrokeWidth(5);

            redPaintbrushFill = new Paint();
            redPaintbrushFill.setColor(Color.RED);
            redPaintbrushFill.setStyle(Paint.Style.FILL);
            redPaintbrushFill.setStrokeWidth(20);
        }

        //transforms density pixesls to pixels
        private int toPxs (int dp){
            return (int) (dp * getResources().getDisplayMetrics().density);
        }

    }
}
