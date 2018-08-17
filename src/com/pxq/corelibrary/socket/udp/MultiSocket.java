package com.pxq.corelibrary.socket.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

import com.pxq.corelibrary.socket.ISocket;
import com.pxq.corelibrary.socket.ISocket.OnUdpListener;
import com.pxq.corelibrary.utils.LogUtil;
import com.pxq.corelibrary.utils.ThreadPoolUtils;

public class MultiSocket implements ISocket {

	private MulticastSocket mMulticastSocket;

	private String ip;

	private int port;

	private OnUdpListener<DatagramPacket> onUdpListener;

	private boolean stop = false;

	public MultiSocket(UdpBuilder builder) {
		if (builder != null) {
			this.ip = builder.ip;
			this.port = builder.port;
			this.onUdpListener = builder.onUdpListener;
		}
	}

	@Override
	public void run() {
		LogUtil.d("start multicast server");
		if (mMulticastSocket == null) {
			try {
				mMulticastSocket = new MulticastSocket(null);
				mMulticastSocket.setReuseAddress(true);
				mMulticastSocket.bind(new InetSocketAddress(port));
				mMulticastSocket.joinGroup(InetAddress.getByName(ip));
				mMulticastSocket.setLoopbackMode(false);
				LogUtil.d("join group " + InetAddress.getByName(ip).getHostAddress() + " port : " + port);
				if (onUdpListener != null) {
					onUdpListener.onUdpOpen();
				}
			} catch (IOException e) {
				e.printStackTrace();
				LogUtil.d("multicast open failed!!");
				if (onUdpListener != null) {
					onUdpListener.onUdpError("multicast open failed!!");
				}
				return;
			}
		} else {
			LogUtil.d("already join group ");
		}
		
		byte[] bytes = new byte[1024];
		DatagramPacket mDatagramPacket = new DatagramPacket(bytes, bytes.length);

		try {
			while (!stop) {
				mMulticastSocket.receive(mDatagramPacket);
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
	public void send(final String ip, final int port, final byte[] data) {
		if (isActived()) {
			ThreadPoolUtils.execute(new Runnable() {
				@Override
				public void run() {
					try {
						mMulticastSocket.send(new DatagramPacket(data, data.length, InetAddress.getByName(ip), port));
						LogUtil.d("multicast send " + data + " to : " + InetAddress.getByName(ip) + " at port " + port);
					} catch (UnknownHostException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			});
			
		}
	}

	@Override
	public void shutdown() {
		LogUtil.d("call shutdown");
		if (isActived()) {
			try {
				mMulticastSocket.leaveGroup(InetAddress.getByName(ip));
				mMulticastSocket.close();
				mMulticastSocket = null;
				LogUtil.d("shutdown success");
				return;
			} catch (UnknownHostException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void startServer() {
		LogUtil.d("startServer");
		if (!isActived()) {
			ThreadPoolUtils.execute(this);
		} else {
			LogUtil.d("multicast server already start!");
		}
	}

	@Override
	public boolean isActived() {
		return mMulticastSocket != null && !mMulticastSocket.isClosed();
	}

}
