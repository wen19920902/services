package interfaces;

import java.net.DatagramPacket;

/***
 * ���еĻ�ȡ����ӵ�ҵ��ӿ�
 * 
 * @author wan
 *
 */
public interface QueueObject {

	// void creanQueue();// ��������

	void put(DatagramPacket qb);// �������

}
