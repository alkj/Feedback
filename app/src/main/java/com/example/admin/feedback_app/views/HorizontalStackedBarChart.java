package com.example.admin.feedback_app.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @author Nils David Rasamoel
 * Horizontal Stacked Bar Chart View locked to only 4 values
 */
public class HorizontalStackedBarChart extends View {
    private int[] counts = {0,0,0,0};
    private int total;
    private Paint[] paints = {new Paint(Color.WHITE), new Paint(Color.WHITE), new Paint(Color.WHITE), new Paint(Color.WHITE)};
    private final int LENGHT = 4;


    public HorizontalStackedBarChart(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setValues(int[] values){
        counts = values;
        total = 0;
        for (int i : values)
            total += i;

        invalidate();
        requestLayout();
    }

    public void setColors(int[] colors) {
        for (int i = 0; i < colors.length; i++) {
            Paint paint = new Paint();
            paint.setColor(colors[i]);
            paint.setStyle(Paint.Style.FILL);

            paints[i] = paint;
        }
        invalidate();
        requestLayout();
    }

    public int getTotal() {
        return total;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);

        canvas.drawColor(Color.BLACK);
        float right,
                left = 0f,
                top = 0f,
                bottom = getHeight(),
                width = getWidth();
        for (int i = 0; i < LENGHT; i++){
            right = (i < LENGHT-1)?
                    left + width/total * counts[i]
                    :
                    width;

            canvas.drawRect(left,top,right,bottom, paints[i]);

            left = right;
        }


    }


}
