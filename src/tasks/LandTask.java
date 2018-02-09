package tasks;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.concurrent.LinkedBlockingQueue;

import bean.udpbean.UDPRequestBean;
import bean.udpbean.UDPRequestBean.UdpUserDataBean;
import interfaces.QueueObject;
import services.udp.UdpThreadPool;
import sqls.impl.UserProfessinalImpl;

/***
 * ��¼ҵ�� �������
 * 
 * @author wan
 *
 */

public class LandTask implements QueueObject {

	private static LinkedBlockingQueue<DatagramPacket> Queue = new LinkedBlockingQueue<>(100);

	private boolean isStart;// �����Ƿ��ڵȴ���ʶ

	private LandTask() {
		if (!isStart) {
			isStart = true;
			UdpThreadPool.excute(new Task());
		}
	}

	static LandTask creation() {
		return Builder.Task;
	}

	private static class Builder {
		private static LandTask Task = new LandTask();
	}

	@Override
	public synchronized void put(DatagramPacket qb) {
		try {
			Queue.put(qb);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/***
	 * ҵ����
	 * 
	 * @author wan
	 *
	 */
	private class Task extends UdpObjectTask {

		private UDPRequestBean ub = new UDPRequestBean();// ����ʵ�����
		private UdpUserDataBean udb = new UdpUserDataBean();// �û�����ʵ�����

		public Task() {
			super();
		}

		@Override
		public void run() {
			while (isStart) {
				try {
					DatagramPacket reivceP = Queue.take();
					ub.disassemblObject(reivceP.getData(), ub);
					udb.disassemblObject(ub.getData(), udb);
					if (UserProfessinalImpl.creanUserImpl().loginCheck(udb.getUserAcoount(), udb.getUserPassword())) {
						ub.setResult(1);
					} else
						ub.setResult(0);
					byte[] send = ub.sealObject();
					DatagramPacket dPacket = new DatagramPacket(send, send.length, reivceP.getSocketAddress());
					dSocket.send(dPacket);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (null != dSocket)
				dSocket.close();

		}
	}

}
