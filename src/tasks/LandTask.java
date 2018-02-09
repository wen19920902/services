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
 * 登录业务 处理队列
 * 
 * @author wan
 *
 */

public class LandTask implements QueueObject {

	private static LinkedBlockingQueue<DatagramPacket> Queue = new LinkedBlockingQueue<>(100);

	private boolean isStart;// 队列是否在等待标识

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
	 * 业务处理
	 * 
	 * @author wan
	 *
	 */
	private class Task extends UdpObjectTask {

		private UDPRequestBean ub = new UDPRequestBean();// 数据实体对象
		private UdpUserDataBean udb = new UdpUserDataBean();// 用户数据实体对象

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
