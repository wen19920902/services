package services.utils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import bean.udpbean.UDPRequestBean;

/***
 * 处理UDP的请求连接时的父类工具
 * 
 * @author wan
 *
 */
public class ObjectUtils {

	protected DatagramPacket replyPacket;// 回复套接字对象
	protected DatagramSocket ds;// 连接套接字的对象
	protected int post;// 监听端口

	protected ObjectUtils(DatagramSocket ds) {
		this.ds = ds;
		this.post = ds.getLocalPort();
	}

	/***
	 * 回复请求客服对象
	 * 
	 * @param ub
	 *            回复数据
	 * @param revicePacket
	 *            回复路径
	 * @throws IOException
	 *             发送异常
	 */
	protected void replyClent(UDPRequestBean ub, DatagramPacket revicePacket) throws IOException {
		byte[] s = ub.sealObject();
		if (replyPacket == null)
			synchronized (replyPacket) {
				replyPacket = new DatagramPacket(s, s.length, revicePacket.getSocketAddress());
			}
		else
			replyPacket.setData(s);
		ds.send(replyPacket);
	}

	/**
	 * byte数组中取int数值，本方法适用于(低位在前，高位在后)的顺序，和和intToByteLow（）配套使用
	 *
	 * @param src
	 *            byte数组
	 * @return int数值
	 */
	protected int bytesToIntLow(byte[] src) {
		// 从数组的第offset位开始
		int offset = 0;
		int value;
		value = ((src[offset] & 0xFF) | ((src[offset + 1] & 0xFF) << 8) | ((src[offset + 2] & 0xFF) << 16)
				| ((src[offset + 3] & 0xFF) << 24));
		return value;
	}

}
