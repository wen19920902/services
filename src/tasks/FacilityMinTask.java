package tasks;

import java.net.DatagramPacket;
import java.util.concurrent.LinkedBlockingQueue;

import bean.udpbean.UDPRequestBean;
import interfaces.QueueObject;
import services.udp.UdpThreadPool;

/***
 * 上传设备时长的任务
 * 
 * @author wan
 *
 */
public class FacilityMinTask implements QueueObject {

	private static LinkedBlockingQueue<DatagramPacket> Queue = new LinkedBlockingQueue<>(100);

	private boolean isStart;// 队列是否在等待标识

	private FacilityMinTask() {
		if (!isStart) {
			isStart = true;
			UdpThreadPool.excute(new Task());
		}
	}

	static FacilityMinTask creation() {
		return Builder.Task;
	}

	private static class Builder {
		private static FacilityMinTask Task = new FacilityMinTask();
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
	 * 业务处理
	 * 
	 * @author wan
	 *
	 */
	private class Task extends UdpObjectTask {

		private UDPRequestBean ub = new UDPRequestBean();// 数据实体对象

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
