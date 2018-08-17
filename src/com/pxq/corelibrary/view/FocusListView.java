package com.pxq.corelibrary.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * 保存listview的焦点状态并还原
 * 
 */
public class FocusListView extends ListView {

	public FocusListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
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
