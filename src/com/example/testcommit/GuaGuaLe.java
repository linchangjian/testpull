package com.example.testcommit;

import android.content.Context;
import android.graphics.AvoidXfermode.Mode;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class GuaGuaLe extends View {

	private Canvas mCanvas;
	private Paint mOutterPaint;
	private int mLastX;
	private int mLastY;
	private Path mPath;
	private Bitmap mBitmap;
	
	//
	private Bitmap bitmap;
	
	public GuaGuaLe(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public GuaGuaLe(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();

	}

	private void init() {
		mOutterPaint = new Paint();
		mPath = new Path();
		
		bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
	}

	public GuaGuaLe(Context context) {
		this(context, null);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		int height = getMeasuredHeight();
		int width = getMeasuredWidth();

		mBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		mCanvas = new Canvas(mBitmap);
		setOutPaint();
		
		mCanvas.drawColor(Color.parseColor("#c0c0c0"));
		
	}

	private void setOutPaint() {
		// 设置画笔的属性
		mOutterPaint.setColor(Color.RED);
		mOutterPaint.setAntiAlias(true);
		mOutterPaint.setDither(true);
		mOutterPaint.setStrokeJoin(Paint.Join.ROUND);
		mOutterPaint.setStrokeCap(Paint.Cap.ROUND);
		mOutterPaint.setStyle(Style.STROKE);
		mOutterPaint.setStrokeWidth(20);
	}
	@Override
	protected void onDraw(Canvas canvas) {
		
		
		canvas.drawBitmap(bitmap, 0, 0,null);
		
		drawPath();
		canvas.drawBitmap(mBitmap,0,0,null);
	}
	private void drawPath() {
		
		mOutterPaint.setXfermode(new PorterDuffXfermode(android.graphics.PorterDuff.Mode.DST_OUT));
		
		mCanvas.drawPath(mPath, mOutterPaint);
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		int action = event.getAction();
		int x = (int) event.getX();
		int y = (int) event.getY();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			
			mLastX = x;
			mLastY = y;
			
			mPath.moveTo(mLastX, mLastY);
			break;
		case MotionEvent.ACTION_MOVE:
			int dx = Math.abs(x - mLastX);
			int dy = Math.abs(y - mLastY);
			
			if(dx > 3 || dy >3){
				mPath.lineTo(x, y);
			}
			mLastX = x;
			mLastY = y;
			break;
		case MotionEvent.ACTION_UP:

			break;
		default:
			break;
		}

		invalidate();
		return true;

	}
}
