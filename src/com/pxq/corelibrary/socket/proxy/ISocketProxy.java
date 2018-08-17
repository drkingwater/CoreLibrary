package com.pxq.corelibrary.socket.proxy;

import com.pxq.corelibrary.socket.udp.UdpFlatMap;

public interface ISocketProxy {

	void start();

	void shutdown();

	void send(byte[] data);

	void send(String ip, int port, byte[] data);

	public interface OnReceiveListener<T> {

		void onReceive(T data);

	}

	public interface IProxyBuilder<T> {

		IProxyBuilder<T> setIp(String ip);

		IProxyBuilder<T> setPort(int port);

		IProxyBuilder<T> setFlatMap(UdpFlatMap<T> flatMap);

		IProxyBuilder<T> setOnReceiveListener(OnReceiveListener<T> onReceiveListener);
		
		/**
		 * �Ƿ����鲥
		 * @param isMulticast
		 */
		IProxyBuilder<T> setMulticast(boolean isMulticast);

		ISocketProxy build();
	}

}
