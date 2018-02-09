package tasks;

import java.net.DatagramPacket;
import java.util.concurrent.LinkedBlockingQueue;

import bean.udpbean.UDPRequestBean;
import interfaces.QueueObject;
import services.udp.UdpThreadPool;

/***
 * ������������
 * 
 * @author wan
 *
 */
public class PersonalDataTask implements QueueObject {

	private static LinkedBlockingQueue<DatagramPacket> Queue = new LinkedBlockingQueue<>(100);

	private boolean isStart;// �����Ƿ��ڵȴ���ʶ

	private PersonalDataTask() {
		if (!isStart) {
			isStart = true;
			UdpThreadPool.excute(new Task());
		}
	}

	static PersonalDataTask creation() {
		return Builder.Task;
	}

	private static class Builder {
		private static PersonalDataTask Task = new PersonalDataTask();
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

		public Task() {
			super();
		}

		@Override
		public void run() {
			while (isStart) {
				try {
					DatagramPacket oBean = Queue.take();
					ub.disassemblObject(oBean.getData(), ub);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}
	}

}
