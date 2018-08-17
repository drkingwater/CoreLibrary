package com.pxq.corelibrary.utils;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolProxy {
	
	private ThreadPoolExecutor mThreadPoolExecutor;
	
	private int corePoolSize = Runtime.getRuntime().availableProcessors() + 1;
	
	private int maximumPoolSize = Runtime.getRuntime().availableProcessors() * 2 + 1;
	
	private long keepAliveTime;
	
	public ThreadPoolProxy(){
		
	}
	
	public ThreadPoolProxy(int corePoolSize, int maximumPoolSize, long keepAliveTime){
		this.corePoolSize = corePoolSize;
		this.maximumPoolSize = maximumPoolSize;
		this.keepAliveTime = keepAliveTime;
	}
	
	private ThreadPoolExecutor initExecutor(){
		if (mThreadPoolExecutor == null) {
			synchronized (ThreadPoolProxy.class) {
				if (mThreadPoolExecutor == null) {
					TimeUnit unit = TimeUnit.MILLISECONDS;
					ThreadFactory factory = Executors.defaultThreadFactory();
					RejectedExecutionHandler handler = new ThreadPoolExecutor.AbortPolicy();
					LinkedBlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<Runnable>();
					
					mThreadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, factory, handler);
					
				}
			}
		}
		return mThreadPoolExecutor;
	}
	
	public Future<?> commitTask(Runnable r){
		initExecutor();
		return mThreadPoolExecutor.submit(r);
	}
	
	public void removeTask(Runnable r){
		initExecutor();
		mThreadPoolExecutor.remove(r);
		
	}
	
	public long getTaskCount(){
		initExecutor();
		return mThreadPoolExecutor.getTaskCount();
	}
	
	public void shutdown(){
		if (mThreadPoolExecutor != null) {
			mThreadPoolExecutor.shutdown();
		}
	}
	
	public void shutdownNow(){
		if (mThreadPoolExecutor != null) {
			mThreadPoolExecutor.shutdownNow();
		}
	}
	
	public boolean isTerminated(){
		if (mThreadPoolExecutor != null) {
			return mThreadPoolExecutor.isTerminated();
		}
		return true;
	}
	
	public boolean isShutdown(){
		if (mThreadPoolExecutor != null) {
			return mThreadPoolExecutor.isShutdown();
		}
		return true;
	}


}
