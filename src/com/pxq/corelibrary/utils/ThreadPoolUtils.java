package com.pxq.corelibrary.utils;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.os.Handler;

public class ThreadPoolUtils {

	public static final int CPU_COUNT = Runtime.getRuntime()
			.availableProcessors();

	public static final int CORE_POOL_SIZE = CPU_COUNT + 1;

	public static final int MAX_POOL_SIZE = CPU_COUNT * 2 + 1;

	public static final int KEEP_ALIVE = 1;

	private static ThreadPoolExecutor sUseCaseExecutor;

	private ThreadPoolUtils() {

	}

	public static void execute(Runnable runnable) {
		if (sUseCaseExecutor == null) {
			sUseCaseExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE,
					MAX_POOL_SIZE, KEEP_ALIVE, TimeUnit.SECONDS,
					new LinkedBlockingQueue<Runnable>());
		}
		
		sUseCaseExecutor.execute(runnable);
		
		
	}
	
	public interface OnExecuteLisenter{
		void onComplete();
	}

}
