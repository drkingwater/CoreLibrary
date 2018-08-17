package com.pxq.corelibrary.socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import com.pxq.corelibrary.socket.ISocket;
import com.pxq.corelibrary.socket.ISocket.OnUdpListener;
import com.pxq.corelibrary.utils.LogUtil;
import com.pxq.corelibrary.utils.ThreadPoolUtils;

public class UdpSocket implements ISocket {

	private DatagramSocket mDatagramSocket;

	private String ip;

	private int port;

	private OnUdpListener<DatagramPacket> onUdpListener;

	private boolean stop = false;

	public UdpSocket(UdpBuilder builder) {
		if (builder != null) {
			this.ip = builder.ip;
			this.port = builder.port;
			this.onUdpListener = builder.onUdpListener;
		}
	}

	@Override
	public void run() {

		try {
			if (mDatagramSocket == null) {
				mDatagramSocket = new DatagramSocket(null);
			}
			mDatagramSocket.setReuseAddress(true);
			mDatagramSocket.bind(new InetSocketAddress(port));
			if (onUdpListener != null) {
				onUdpListener.onUdpOpen();
			}
		} catch (SocketException e) {
			e.printStackTrace();
			if (onUdpListener != null) {
				onUdpListener.onUdpError("udp start failed");
			}
			return;
		}
		byte[] bytes = new byte[1024];
		DatagramPacket mDatagramPacket = new DatagramPacket(bytes, bytes.length);

		try {
			while (!stop) {
				mDatagramSocket.receive(mDatagramPacket);
				if (onUdpListener != null) {
					onUdpListener.onReceive(mDatagramPacket);
				}

			}
		} catch (IOException e) {
			e.printStackTrace();
			if (onUdpListener != null) {
				onUdpListener.onUdpError(e.getMessage());
			}
		}
		if (onUdpListener != null) {
			onUdpListener.onUdpClose();
		}
	}

	@Override
	public void send(byte[] data) {
		send(ip, port, data);
	}

	@Override
	public void shutdown() {
		stop = true;
		if (mDatagramSocket != null) {
			mDatagramSocket.close();
		}
	}

	@Override
	public void startServer() {
		ThreadPoolUtils.execute(this);
	}

	@Override
	public boolean isActived() {
		return mDatagramSocket != null && !mDatagramSocket.isClosed();
	}

	@Override
	public void send(final String ip, final int port, final byte[] data) {
		if (isActived()) {
			ThreadPoolUtils.execute(new Runnable() {
				
				@Override
				public void run() {
					try {
						mDatagramSocket.send(new DatagramPacket(data, data.length, InetAddress.getByName(ip), port));
						LogUtil.d("send " + data + " to : " + InetAddress.getByName(ip) + " at port " + port);
					} catch (UnknownHostException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			
		}
	}

}
