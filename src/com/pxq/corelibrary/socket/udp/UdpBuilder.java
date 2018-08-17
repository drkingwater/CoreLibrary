package com.pxq.corelibrary.socket.udp;

import java.net.DatagramPacket;

import com.pxq.corelibrary.socket.ISocket;
import com.pxq.corelibrary.socket.ISocket.IUdpBuilder;
import com.pxq.corelibrary.socket.ISocket.OnUdpListener;

public class UdpBuilder implements IUdpBuilder{

	public String ip;

	public int port;

	public OnUdpListener<DatagramPacket> onUdpListener;
	
	private boolean isMulticast = false;

	@Override
	public IUdpBuilder setIp(String ip) {
		this.ip = ip;
		return this;
	}

	@Override
	public IUdpBuilder setPort(int port) {
		this.port = port;
		return this;
	}

	@Override
	public IUdpBuilder setOnUdpListener(OnUdpListener<DatagramPacket> onUdpListener) {
		this.onUdpListener = onUdpListener;
		return this;
	}
	
	@Override
	public IUdpBuilder setMulticast(boolean isMulticast) {
		this.isMulticast = isMulticast;
		return this;
	}

	@Override
	public ISocket build() {
		if (isMulticast) {
			return new MultiSocket(this);
		}
		return new UdpSocket(this);
	}


}
