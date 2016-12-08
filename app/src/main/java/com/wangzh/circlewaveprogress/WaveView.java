package com.wangzh.circlewaveprogress;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


public class WaveView extends View
{

	private int mViewWidth;     //view宽度
	private int mViewHeight;     //view高度
	private float mLevelLineY;   //波形 Y轴数值
	private float mLevelLineXL;    //波形X轴数值  左边
	private float mLevelLineXR;     //波形X轴数值 右边
	private float mWaveHeight = 0.5f;    //波浪起伏幅度
	private float mWaveWidth = 5;      //波长
	private float mMoveLen;            //波形位移
	public static final float SPEED = 1f;    //水波平移速度
	private float mLeftSide;             //被隐藏的左边波形
	private float angle;                   //角度

	private List<Point> mPointsList;
	private Paint mPaint;
	private Path mWavePath;
	private boolean isMeasured = false;
	private float levelPercent;      // 百分比   0-1

	private Timer timer;
	private MyTimerTask mTask;

	public WaveView(Context context)
	{
		super(context);
		init();
	}

	public WaveView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}

	private void init()
	{
		mPointsList = new ArrayList<Point>();
		timer = new Timer();
		mPaint = new Paint();
		mWavePath = new Path();
		levelPercent = 0.7f;
	}

	@Override
	public void onWindowFocusChanged(boolean hasWindowFocus)
	{
		super.onWindowFocusChanged(hasWindowFocus);
		// 开始波动
		start();
	}

	private void start()
	{
		if (mTask != null)
		{
			mTask.cancel();
			mTask = null;
		}
		mTask = new MyTimerTask(updateHandler);
		timer.schedule(mTask, 0, 10);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if (!isMeasured)
		{
			isMeasured = true;
			mViewHeight = MeasureSpec.getSize(heightMeasureSpec);
			mViewWidth = MeasureSpec.getSize(widthMeasureSpec);
			setMeasuredDimension(mViewWidth,mViewHeight);
			//计算波形Y轴位置
			if (levelPercent>0.5){
				angle = (float) ((levelPercent-0.5)*2*90);
			}else {
				angle = (float) ((0.5 - levelPercent)*2*90);
			}
			mLevelLineY = mViewHeight * (1 - levelPercent);
			mLevelLineXR = (float) ((mViewWidth/2) + (mViewWidth/2) * Math.cos(angle * Math.PI/180));
			mLevelLineXL = (float) ((mViewWidth/2) - (mViewWidth/2) * Math.cos(angle * Math.PI/180));
			// 根据View宽度计算波形峰值
			mWaveHeight = mViewWidth /15.0f;
			// 波长等于四倍View宽度也就是View中只能看到四分之一个波形，这样可以使起伏更明显
			mWaveWidth = mViewWidth * 1.5f;
			// 左边隐藏的距离预留一个波形
			mLeftSide = -mWaveWidth;
			// 这里计算在可见的View宽度中能容纳几个波形，注意n上取整
			int n = (int) Math.round(mViewWidth / mWaveWidth + 0.5);
			// n个波形需要4n+1个点，但是我们要预留一个波形在左边隐藏区域，所以需要4n+1个点
			for (int i = 0; i < (4 * n + 5); i++)
			{
				// 从P0开始初始化到P4n+4，总共4n+5个点
				float x = i * mWaveWidth / 4 - mWaveWidth;
				float y = 0;
				switch (i % 4)
				{
					case 0:
					case 2:
						// 零点位于水位线上
						y = mLevelLineY;
						break;
					case 1:
						// 往下波动的控制点
						y = mLevelLineY + mWaveHeight;
						break;
					case 3:
						// 往上波动的控制点
						y = mLevelLineY - mWaveHeight;
						break;
				}
				mPointsList.add(new Point(x, y));
			}
		}
	}

	/**
	 * @param canvas
	 */
	@Override
	protected void onDraw(Canvas canvas)
	{
		//绘制外圆
		mPaint.setColor(0x5043CD80);
		mPaint.setStyle(Style.FILL_AND_STROKE);
		mPaint.setAntiAlias(true);
		canvas.drawCircle((float)(mViewWidth/2),(float)(mViewHeight/2),getWidth()/2,mPaint);
		/*mPaint.setColor(0x5000BFFF);
		canvas.drawLine(0,getHeight()/2,getWidth(),getHeight()/2,mPaint);*/
		/*//绘制波形
		mPaint.setColor(0x5000BFFF);
		mPaint.setStyle(Style.STROKE);
		mWavePath.reset();
		int i = 0;
		mWavePath.moveTo(mPointsList.get(0).getX(),mPointsList.get(0).getY());
		for (; i < mPointsList.size() - 2; i = i + 2)
		{
			mWavePath.quadTo(mPointsList.get(i + 1).getX()
					, mPointsList.get(i + 1).getY(), mPointsList.get(i + 2).getX()
					,mPointsList.get(i + 2).getY());
		}
		canvas.drawPath(mWavePath, mPaint);
		//封闭波形
		mWavePath.lineTo(mPointsList.get(i).getX(), mLevelLineY+mWaveHeight);
		mWavePath.lineTo(mLeftSide<0?0:mLeftSide, mLevelLineY+mWaveHeight);
		mWavePath.close();
		mPaint.setStyle(Style.FILL);
		mPaint.setColor(0x7000BFFF);
		canvas.drawPath(mWavePath,mPaint);*/
		/*//绘制左下方弧 遮罩层
		mPaint.setColor(0xffffffff);
		mWavePath.reset();
		mWavePath.moveTo(0,mLevelLineY);
		mWavePath.lineTo(0,getHeight());
		mWavePath.lineTo(getWidth()/2,getHeight());
		mWavePath.arcTo(new RectF(0,0,getWidth(),getHeight()),90,90);
		mWavePath.close();
		canvas.drawPath(mWavePath,mPaint);
		//绘制右下方弧  遮罩层
		mWavePath.reset();
		mWavePath.moveTo(getWidth(),mLevelLineY);
		mWavePath.lineTo(getWidth(),getHeight());
		mWavePath.lineTo(getWidth()/2,getHeight());
		mWavePath.arcTo(new RectF(0,0,getWidth(),getHeight()),90,-90);
		mWavePath.close();
		canvas.drawPath(mWavePath,mPaint);
		//绘制左上方弧 遮罩层
		mWavePath.reset();
		mWavePath.moveTo(0,getHeight()/2);
		mWavePath.lineTo(0,0);
		mWavePath.lineTo(getWidth()/2,0);
		mWavePath.arcTo(new RectF(0,0,getWidth(),getHeight()),270,-90);
		mWavePath.close();
		canvas.drawPath(mWavePath,mPaint);
		//绘制右上方弧 遮罩层
		mWavePath.reset();
		mWavePath.moveTo(getWidth()/2,0);
		mWavePath.lineTo(getWidth(),0);
		mWavePath.lineTo(getWidth(),getHeight()/2);
		mWavePath.arcTo(new RectF(0,0,getWidth(),getHeight()),360,-90);
		mWavePath.close();
		canvas.drawPath(mWavePath,mPaint);*/

		//绘制水波下面背景
		mPaint.setStyle(Style.FILL);
		mPaint.setColor(0xff000000);
		mPaint.setStrokeWidth(10);
		mWavePath.reset();
		mWavePath.moveTo(getWidth()/2,mLevelLineY);
		mWavePath.lineTo(mLevelLineXR,mLevelLineY);
		//mWavePath.arcTo(new RectF(0,0,getWidth(),getHeight()),340,220);
		mWavePath.close();
		canvas.drawPath(mWavePath,mPaint);
	}

	/**
	 * 所有点的x坐标都还原到初始状态，也就是一个周期前的状态
	 */
	private void resetPoints()
	{
		mLeftSide = -mWaveWidth;
		for (int i = 0; i < mPointsList.size(); i++)
		{
			mPointsList.get(i).setX(i * mWaveWidth / 4 - mWaveWidth);
		}
	}

	private void getMagicNum(){

	}

	/**
	 * 定时器
	 */
	class MyTimerTask extends TimerTask
	{
		Handler handler;

		public MyTimerTask(Handler handler)
		{
			this.handler = handler;
		}

		@Override
		public void run()
		{
			handler.sendMessage(handler.obtainMessage());
		}

	}

	Handler updateHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			// 记录平移总位移
			mMoveLen += SPEED;
			// 波形平移
			for (int i = 0; i < mPointsList.size(); i++)
			{
				mPointsList.get(i).setX(mPointsList.get(i).getX() + SPEED);
				switch (i % 4)
				{
					case 0:
					case 2:
						mPointsList.get(i).setY(mLevelLineY);
						break;
					case 1:
						mPointsList.get(i).setY(mLevelLineY + mWaveHeight);
						break;
					case 3:
						mPointsList.get(i).setY(mLevelLineY - mWaveHeight);
						break;
				}
			}
			if (mMoveLen >= mWaveWidth)
			{
				// 波形平移超过一个完整波形后复位
				mMoveLen = 0;
				resetPoints();
			}
			invalidate();
		}
	};

	class Point
	{
		private float x;
		private float y;

		public float getX()
		{
			return x;
		}

		public void setX(float x)
		{
			this.x = x;
		}

		public float getY()
		{
			return y;
		}

		public void setY(float y)
		{
			this.y = y;
		}

		public Point(float x, float y)
		{
			this.x = x;
			this.y = y;
		}

	}

}
