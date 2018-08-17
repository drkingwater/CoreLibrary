package com.pxq.corelibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * È«ÆÁ²¥·Å
 * 
 */
public class FullScreenVideoPlayer extends VideoView {

	public FullScreenVideoPlayer(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		int width = getDefaultSize(0, widthMeasureSpec);
        int height = getDefaultSize(0, heightMeasureSpec);
        setMeasuredDimension(width, height);
	}

}
