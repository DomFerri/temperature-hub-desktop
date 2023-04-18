package application;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class MulticastHandler extends Thread {

	public MulticastHandler() {

	}

	public void run() {
		try {
			InetAddress hubAddress = InetAddress.getByName("224.0.1.3");
			InetSocketAddress group = new InetSocketAddress(hubAddress, 10001);
			NetworkInterface nic = NetworkInterface.getByInetAddress(InetAddress.getByName("192.168.1.116"));
			MulticastSocket s = new MulticastSocket(10001);
			s.setSoTimeout(10);
			
			s.joinGroup(new InetSocketAddress(hubAddress, 0), nic);
			

			while (true) {
				try {
					byte[] buf = new byte[9];
					DatagramPacket packet = new DatagramPacket(buf, buf.length);
					s.receive(packet);
					String received = new String(packet.getData(), 0, packet.getLength());
					
					ByteBuffer buffer = ByteBuffer.allocate(9);
					buffer.order(ByteOrder.LITTLE_ENDIAN);
					buffer.put(packet.getData());
					
					
					System.out.println(Arrays.toString(buffer.array()));
					System.out.printf("%.2f", buffer.getFloat(5));
					System.out.println();
				} catch (SocketTimeoutException e) {
					
				} 
				
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
