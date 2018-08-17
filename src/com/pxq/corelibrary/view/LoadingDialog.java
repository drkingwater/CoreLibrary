package com.pxq.corelibrary.view;


import com.pxq.corelibrary.R;

import android.app.Dialog;
import android.content.Context;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class LoadingDialog extends Dialog{
	
	private ImageView loading_iv;
	private TextView detail_tv;
	private RotateAnimation rotateAnimation;

	public LoadingDialog(Context context) {
		super(context, R.style.dialog);
		// TODO Auto-generated constructor stub
		//setTitle("");
		initView();
	}
	
	private void initView(){
		setContentView(R.layout.loading_dialog_layout);
		loading_iv = (ImageView) findViewById(R.id.loading_iv);
		initAnim();
	}
	
	private void initAnim(){
		rotateAnimation = new RotateAnimation(0, 360, Animation.RESTART, 0.5f, Animation.RESTART, 0.5f);
		rotateAnimation.setDuration(1000);
		rotateAnimation.setRepeatCount(Animation.INFINITE);
		rotateAnimation.setRepeatMode(Animation.RESTART);
		rotateAnimation.setStartTime(Animation.START_ON_FIRST_FRAME);
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		loading_iv.startAnimation(rotateAnimation);
		super.show();
	}
	
	@Override
	public void dismiss() {
		// TODO Auto-generated method stub
		rotateAnimation.cancel();
		super.dismiss();
	}
}
