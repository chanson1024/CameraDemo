package com.jd.lib;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.jd.lib.util.ScreenUtils;

/**
 * Created by Administrator on 2017/3/14 0014.
 */


public class CameraSurfaceView extends SurfaceView  {

    private static final String TAG = "CameraSurfaceView";
    SurfaceHolder mSurfaceHolder;
    public float rectLeft, rectTop, rectRight, rectBottom;
    private int mScreenHeight, mScreenWidth;
    Paint rectPaint;
    private android.graphics.Xfermode xfermode_XOR = new PorterDuffXfermode(
            PorterDuff.Mode.XOR);

    public CameraSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mSurfaceHolder = getHolder();
        mSurfaceHolder.setFormat(PixelFormat.TRANSPARENT);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
//        mSurfaceHolder.addCallback(this);

        mScreenHeight = ScreenUtils.getScreenHeight(App.getContext());
        mScreenWidth = ScreenUtils.getScreenWidth(App.getContext());

        // Mark rect in the center of screen.
        rectLeft = (float) (mScreenWidth * 0.2);
        rectRight = mScreenWidth - rectLeft;
        rectTop = (mScreenHeight - (mScreenWidth - 2 * rectLeft)) / 2;
        rectBottom = mScreenHeight - rectTop;

        rectPaint = new Paint();
        rectPaint.setColor(Color.BLUE);
        rectPaint.setStyle(Paint.Style.FILL);
        rectPaint.setXfermode(xfermode_XOR);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        Log.w(TAG,"onDraw=============");

        //小遮罩
        canvas.drawRect(rectLeft,rectTop,rectRight,rectBottom,rectPaint);
        //大遮罩
        canvas.drawRect(0,0,mScreenWidth,mScreenHeight,rectPaint);


    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                break;

            case MotionEvent.ACTION_UP:
                break;
        }

        return super.onTouchEvent(event);
    }
}
