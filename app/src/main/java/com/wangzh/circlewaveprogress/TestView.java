package com.wangzh.circlewaveprogress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 99210 on 2016/12/7.
 */

public class TestView extends View {

    private int mViewHeight;
    private int mViewWidth;
    private Paint mPaint;
    private Path mPath;

    public TestView(Context context) {
        super(context);
        init();
    }

    public TestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        mPaint = new Paint();
        mPath = new Path();
        mPaint.setColor(0xff00BFFF);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mViewHeight = MeasureSpec.getSize(heightMeasureSpec);
        mViewWidth = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(mViewWidth,mViewHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
       /* mPath.moveTo(0,getHeight()/2);
        canvas.drawText("1",0,getHeight()/2,mPaint);
        mPath.lineTo(0,getHeight());
        canvas.drawText("2",0,getHeight(),mPaint);
        mPath.lineTo(getWidth()/2,getHeight());
        canvas.drawText("3",getWidth()/2,getHeight(),mPaint);
        mPath.arcTo(new RectF(0,0,getWidth(),getHeight()),90,90);
        canvas.drawText("4",0,getHeight()/2,mPaint);
        mPath.close();
        canvas.drawPath(mPath,mPaint);

        mPath.reset();
        mPath.moveTo(getWidth(),getHeight()/2);
        mPath.lineTo(getWidth(),getHeight());
        mPath.lineTo(getWidth()/2,getHeight());
        mPath.arcTo(new RectF(0,0,getWidth(),getHeight()),90,-90);
        mPath.close();
        canvas.drawPath(mPath,mPaint);

        mPath.reset();
        mPath.moveTo(0,getHeight()/2);
        mPath.lineTo(0,0);
        mPath.lineTo(getWidth()/2,0);
        mPath.arcTo(new RectF(0,0,getWidth(),getHeight()),270,-90);
        mPath.close();
        canvas.drawPath(mPath,mPaint);

        mPath.reset();
        mPath.moveTo(getWidth()/2,0);
        mPath.lineTo(getWidth(),0);
        mPath.lineTo(getWidth(),getHeight()/2);
        mPath.arcTo(new RectF(0,0,getWidth(),getHeight()),360,-90);
        mPath.close();
        canvas.drawPath(mPath,mPaint);*/
       /* mPath.moveTo(getWidth(),getHeight()/2);
        mPath.lineTo(getWidth(),getHeight()/2);
        mPath.arcTo(new RectF(0,0,getWidth(),getHeight()),0,180);
        mPath.close();
        canvas.drawPath(mPath,mPaint);*/

        mPath.reset();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(0xff00fa9a);
        mPath.moveTo(getWidth()/2,getHeight()/2);
        mPath.quadTo(getWidth()/2+getWidth()/4,getHeight()/2-100,getWidth(),getHeight()/2);
        mPaint.setTextSize(30);
        canvas.drawText("1",getWidth()/2,getHeight()/2,mPaint);
        mPath.close();
        canvas.drawPath(mPath,mPaint);
       /* mPaint.setColor(0xffffffff);
        mPaint.setStyle(Paint.Style.FILL);
        mPath.reset();
        mPath.moveTo(getWidth()/2,getHeight()/2);
        mPath.quadTo(getWidth()/4,getHeight()/2+100,0,getHeight()/2);
        mPaint.setTextSize(30);
        canvas.drawText("1",getWidth()/2,getHeight()/2,mPaint);
        mPath.close();
        canvas.drawPath(mPath,mPaint);*/
    }
}
