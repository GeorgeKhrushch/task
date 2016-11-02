package com.skywell.test.ui.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ImageWithTextOnIt extends ImageView {
    private Paint mTextPaint;
    private String mTextToWrite;


    public ImageWithTextOnIt(Context context, String text) {
        super(context);
        mTextPaint = new Paint();
        mTextToWrite = text;
    }

    public ImageWithTextOnIt(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public ImageWithTextOnIt(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setStyle(Paint.Style.FILL);
        mTextPaint.setTextSize(25);
        canvas.drawText(mTextToWrite, canvas.getWidth()- mTextToWrite.length()*13-15,
                canvas.getHeight()-20, mTextPaint);
    }
}
