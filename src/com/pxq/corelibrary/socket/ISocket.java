package com.pxq.corelibrary.socket;

import java.net.DatagramPacket;

public interface ISocket extends Runnable{

	void send(byte[] data);
	
	void send(String ip, int port, byte[] data);

	void shutdown();
	
	void startServer();
	
	boolean isActived();

	public interface OnUdpListener<T> {
		void onUdpOpen();

		void onReceive(T data);

		void onUdpClose();

		void onUdpError(String errorInfo);

	}

	public interface IUdpBuilder {
		
		IUdpBuilder setIp(String ip);
		
		IUdpBuilder setPort(int port);
		
		IUdpBuilder setOnUdpListener(OnUdpListener<DatagramPacket> onUdpListener);
		
		/**
		 * 是否是组�?,默认单播
		 * @param isMulticast
		 */
		IUdpBuilder setMulticast(boolean isMulticast);
		
		ISocket build();
	}

	
}
