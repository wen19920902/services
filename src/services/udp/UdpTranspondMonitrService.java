package services.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import bean.udpbean.UDPRequestBean;
import sqls.impl.MessageImpl;

/***
 * UDP转发消息回复 监听
 * 
 * @author wan
 *
 */
public class UdpTranspondMonitrService implements Runnable {

	private DatagramSocket dSocket;
	private UDPRequestBean ub = new UDPRequestBean();// 数据实体对象

	private UdpTranspondMonitrService() {
		try {
			dSocket = new DatagramSocket();
			UdpThreadPool.excute(this);
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public static DatagramSocket gainSocket() {
		return Budile.um.dSocket;
	}

	public static UdpTranspondMonitrService crean() {
		return Budile.um;
	}

	private static class Budile {
		static UdpTranspondMonitrService um = new UdpTranspondMonitrService();
	}

	@Override
	public void run() {
		byte[] csbyts = new byte[1024];// 接收用户发送的数据

		while (true) {
			DatagramPacket reviceP = new DatagramPacket(csbyts, csbyts.length);
			try {
				dSocket.receive(reviceP);
				ub.disassemblObject(reviceP.getData(), ub);
				boolean b = MessageImpl.creanMessageImpl().updateMessageReadState(ub.getTargetID());
				System.out.println("修改阅读状态:" + b);
				Thread.sleep(0);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				if (null != dSocket)
					dSocket.close();
				break;
			}
		}
	}

}
