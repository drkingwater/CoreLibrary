package com.pxq.corelibrary.utils;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.util.Log;
import android.view.View;

/**
 * 动画工具类
 * @author pxq
 * @date 2018-3-12
 */
public class AnimUtils {
	
	
	public static final int ANIM_DURATION = 240;
	
	/**
	 * 位移动画
	 * @param target
	 * @param x
	 * @param y
	 */
	public static final void translation(Object target, float x, float y){
		ObjectAnimator tranX = ObjectAnimator.ofFloat(target, "translationX", x);
		ObjectAnimator tranY = ObjectAnimator.ofFloat(target, "translationY", y);
		
		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.play(tranX).with(tranY);
		animatorSet.setDuration(ANIM_DURATION);
		animatorSet.start();
	}
	
	/**
	 * Y轴位移动画
	 * @param target
	 * @param x
	 * @param y
	 */
	public static final void translationY(Object target, float y, AnimatorListenerAdapter adapter){
		ObjectAnimator tranY = ObjectAnimator.ofFloat(target, "translationY", y);
		tranY.setDuration(ANIM_DURATION);
		if (adapter != null) {
			tranY.addListener(adapter);
		}
		tranY.start();
		/*AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.play(tranX).with(tranY);
		animatorSet.setDuration(ANIM_DURATION);
		animatorSet.start();*/
	}
	
	/**
	 * 透明动画
	 * @param target
	 * @param from
	 * @param to
	 */
	public static final void alpha(Object target, float from, float to){
		ObjectAnimator animator = ObjectAnimator.ofFloat(target, "alpha", from, to);
		animator.setDuration(ANIM_DURATION);
		animator.start();
	}
	
	/**
	 * 缩放动画
	 * @param target
	 * @param from
	 * @param to
	 */
	public static final void scale(Object target, float from, float to){
		ObjectAnimator scaleX = ObjectAnimator.ofFloat(target, "scaleX", from, to);
		ObjectAnimator scaleY = ObjectAnimator.ofFloat(target, "scaleY", from, to);
		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.play(scaleX).with(scaleY);
		animatorSet.setDuration(ANIM_DURATION);
		animatorSet.start();
	}
	

}
