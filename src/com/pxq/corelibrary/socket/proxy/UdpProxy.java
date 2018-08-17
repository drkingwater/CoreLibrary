package com.pxq.corelibrary.socket.proxy;

import java.net.DatagramPacket;

import com.pxq.corelibrary.socket.ISocket;
import com.pxq.corelibrary.socket.ISocket.OnUdpListener;
import com.pxq.corelibrary.socket.udp.UdpBuilder;
import com.pxq.corelibrary.socket.udp.UdpFlatMap;
import com.pxq.corelibrary.utils.LogUtil;


public class UdpProxy<T> implements ISocketProxy {

	private ISocket mUdpSocket;

	private String ip;

	private int port;

	private UdpFlatMap<T> flatMap;

	private OnReceiveListener<T> onReceiveListener;

	private String targetIp;
	private int targetPort;

	public UdpProxy(Builder<T> proxyBuilder) {

		this.ip = proxyBuilder.ip;
		this.port = proxyBuilder.port;
		this.flatMap = proxyBuilder.flatMap;
		this.onReceiveListener = proxyBuilder.onReceiveListener;

		UdpBuilder builder = new UdpBuilder();
		mUdpSocket = builder.setIp(ip).setPort(port)
				.setMulticast(proxyBuilder.isMulticast)
				.setOnUdpListener(new OnUdpListener<DatagramPacket>() {

					@Override
					public void onUdpOpen() {
						LogUtil.d("onUdpOpen");
					}

					@Override
					public void onUdpError(String errorInfo) {
						LogUtil.d("onUdpError");
					}

					@Override
					public void onUdpClose() {
						LogUtil.d("onUdpClose");
					}

					@Override
					public void onReceive(DatagramPacket data) {
						targetIp = data.getAddress().getHostAddress();
						targetPort = data.getPort();
						LogUtil.d("onReceive from ip : " + targetIp + " port : " + targetPort);
						if (flatMap != null) {
							T result = flatMap.map(data);
							if (onReceiveListener != null) {
								onReceiveListener.onReceive(result);
							}
						}
					}
				}).build();
	}

	@Override
	public void start() {
		if (!mUdpSocket.isActived()) {
			mUdpSocket.startServer();
		}
	}

	@Override
	public void shutdown() {
		mUdpSocket.shutdown();
	}

	public static class Builder<T> implements IProxyBuilder<T> {

		public String ip;

		public int port;

		public boolean isMulticast;

		public OnReceiveListener<T> onReceiveListener;

		public UdpFlatMap<T> flatMap;

		@Override
		public IProxyBuilder<T> setIp(String ip) {
			this.ip = ip;
			return this;
		}

		@Override
		public IProxyBuilder<T> setPort(int port) {
			this.port = port;
			return this;
		}

		@Override
		public IProxyBuilder<T> setFlatMap(UdpFlatMap<T> flatMap) {
			this.flatMap = flatMap;
			return this;
		}

		@Override
		public IProxyBuilder<T> setOnReceiveListener(OnReceiveListener<T> onReceiveListener) {
			this.onReceiveListener = onReceiveListener;
			return this;
		}

		@Override
		public ISocketProxy build() {
			return new UdpProxy<T>(this);
		}

		@Override
		public IProxyBuilder<T> setMulticast(boolean isMulticast) {
			this.isMulticast = isMulticast;
			return this;
		}

	}

	@Override
	public void send(byte[] data) {
		LogUtil.d("ip : " + targetIp + " port : " + port);
		mUdpSocket.send(targetIp, targetPort, data);
	}

	@Override
	public void send(String ip, int port, byte[] data) {
		mUdpSocket.send(ip, port, data);
	}

}
