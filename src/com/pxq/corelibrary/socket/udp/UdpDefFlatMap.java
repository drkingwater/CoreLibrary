package com.pxq.corelibrary.socket.udp;

import java.net.DatagramPacket;

public class UdpDefFlatMap extends UdpFlatMap<String> {

	@Override
	public String map(DatagramPacket src) {
		if (src == null) {
			return null;
		}
		return new String(src.getData(), src.getOffset(), src.getLength());
	}

}
