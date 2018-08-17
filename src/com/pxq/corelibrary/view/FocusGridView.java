package com.pxq.corelibrary.view;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.ViewDebug.ExportedProperty;
import android.widget.GridView;

/**
 * 解决gridview抢焦点问题
 * @author pxq
 * @date 2018-1-11
 */
public class FocusGridView extends GridView{

	public FocusGridView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public FocusGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public FocusGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	@ExportedProperty
	public boolean isInTouchMode() {
		if(19 == Build.VERSION.SDK_INT){  
	        return !(hasFocus() && !super.isInTouchMode());  
	    }else{  
	        return super.isInTouchMode();  
	    }  
	}
	
	@Override
	protected void onFocusChanged(boolean gainFocus, int direction,
			Rect previouslyFocusedRect) {
		int lastSelectItem = getSelectedItemPosition();
		super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
		if (gainFocus) {
			if (lastSelectItem < 0) {
				lastSelectItem = 0;
			}
			setSelection(lastSelectItem);
		}
	}

}
