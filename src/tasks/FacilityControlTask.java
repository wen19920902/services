package tasks;

import java.net.DatagramPacket;
import java.util.concurrent.LinkedBlockingQueue;

import bean.udpbean.UDPRequestBean;
import interfaces.QueueObject;
import services.udp.UdpThreadPool;

/***
 * �豸���Ƶ�����
 * 
 * @author wan
 *
 */
public class FacilityControlTask implements QueueObject {

	private static LinkedBlockingQueue<DatagramPacket> Queue = new LinkedBlockingQueue<>(100);

	private boolean isStart;// �����Ƿ��ڵȴ���ʶ

	private FacilityControlTask() {
		if (!isStart) {
			isStart = true;
			UdpThreadPool.excute(new Task());
		}
	}

	static FacilityControlTask creation() {
		return Builder.Task;
	}

	private static class Builder {
		private static FacilityControlTask Task = new FacilityControlTask();
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
