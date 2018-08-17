package com.pxq.corelibrary.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by pxq on 2017/7/12.
 * 
 * 效果：字体从左往右闪烁
 */

public class FlashTextView extends TextView {

    private Paint mPaint, mPaint1, mPaint2;
    
    private int mViewWidth = 0;
    
    private LinearGradient mLinearGradient;
    
    private Matrix mGradientMatrix;
    
    private int mTraslate;
 

    public FlashTextView(Context context) {
        super(context);

      
    }

    public FlashTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint1 = new Paint();
        mPaint1.setColor(getResources().getColor(android.R.color.holo_blue_light));
        mPaint1.setStyle(Paint.Style.FILL);
        mPaint2 = new Paint();
        mPaint2.setColor(Color.YELLOW);
        mPaint2.setStyle(Paint.Style.FILL);
    }

    public FlashTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
       
    }

    @Override
    protected void onDraw(Canvas canvas) {
    	
        //canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint1);
        //canvas.drawRect(0, 0, getMeasuredWidth() - 10, getMeasuredHeight() - 10, mPaint2);
        //
        //canvas.save();
        //canvas.translate(10, 0);
        super.onDraw(canvas); //
        //canvas.restore();
        
        if (mGradientMatrix != null) {
        	mTraslate += mViewWidth / 5;
        	Log.d("pxq", mTraslate + "  " + mViewWidth);
        	if (mTraslate > mViewWidth) {
				mTraslate = -mViewWidth;
			}
			mGradientMatrix.setTranslate(mTraslate, 0);
			mLinearGradient.setLocalMatrix(mGradientMatrix);
			postInvalidateDelayed(100);
		}
        
    }
    
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    	// TODO Auto-generated method stub
    	super.onSizeChanged(w, h, oldw, oldh);
    	
    	if (mViewWidth == 0) {
			mViewWidth = getMeasuredWidth();
			
			if (mViewWidth > 0) {
				mPaint = getPaint();
				//mPaint.setTextSize(14);
				mLinearGradient = new LinearGradient(0, 0, mViewWidth, getMeasuredHeight(), new int[]{Color.BLUE, 0xffffffff, Color.RED}, null, Shader.TileMode.CLAMP);
				mPaint.setShader(mLinearGradient);
				mGradientMatrix = new Matrix();
			}
			
		}
    	
    	
    }
}
