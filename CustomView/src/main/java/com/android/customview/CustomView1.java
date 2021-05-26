package com.android.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by Administrator on 2021/5/25 0025
 */
public class CustomView1 extends View {
    public CustomView1(Context context) {
        super(context);
        initView();
    }

    public CustomView1(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CustomView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    public CustomView1(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private String text = "Hello World";
    private Paint paint;

    private void initView(){
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(30f);
        setBackgroundColor(Color.YELLOW);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawText(text, getWidth()/2f-paint.measureText(text)/2, getHeight()/2f, paint);
    }
}
