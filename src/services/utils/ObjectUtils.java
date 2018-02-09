package services.utils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import bean.udpbean.UDPRequestBean;

/***
 * ����UDP����������ʱ�ĸ��๤��
 * 
 * @author wan
 *
 */
public class ObjectUtils {

	protected DatagramPacket replyPacket;// �ظ��׽��ֶ���
	protected DatagramSocket ds;// �����׽��ֵĶ���
	protected int post;// �����˿�

	protected ObjectUtils(DatagramSocket ds) {
		this.ds = ds;
		this.post = ds.getLocalPort();
	}

	/***
	 * �ظ�����ͷ�����
	 * 
	 * @param ub
	 *            �ظ�����
	 * @param revicePacket
	 *            �ظ�·��
	 * @throws IOException
	 *             �����쳣
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
	 * byte������ȡint��ֵ��������������(��λ��ǰ����λ�ں�)��˳�򣬺ͺ�intToByteLow��������ʹ��
	 *
	 * @param src
	 *            byte����
	 * @return int��ֵ
	 */
	protected int bytesToIntLow(byte[] src) {
		// ������ĵ�offsetλ��ʼ
		int offset = 0;
		int value;
		value = ((src[offset] & 0xFF) | ((src[offset + 1] & 0xFF) << 8) | ((src[offset + 2] & 0xFF) << 16)
				| ((src[offset + 3] & 0xFF) << 24));
		return value;
	}

}
