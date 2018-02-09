package tasks;

import java.io.IOException;
import java.net.DatagramPacket;

import java.util.concurrent.LinkedBlockingQueue;

import bean.udpbean.UDPRequestBean;
import interfaces.QueueObject;
import services.udp.UdpThreadPool;
import sqls.impl.UserProfessinalImpl;

/***
 * 在线报道业务处理
 * 
 * @author wan
 *
 */
public class OnLineTask implements QueueObject {

	private static LinkedBlockingQueue<DatagramPacket> Queue = new LinkedBlockingQueue<>(100);

	private boolean isStart;// 队列是否在等待标识

	private OnLineTask() {
		if (!isStart) {
			isStart = true;
			UdpThreadPool.excute(new Task());
		}
	}

	static OnLineTask creation() {
		return Builder.Task;
	}

	private static class Builder {
		private static OnLineTask Task = new OnLineTask();
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
					DatagramPacket reviceP = Queue.take();
					ub.disassemblObject(reviceP.getData(), ub);
					if (UserProfessinalImpl.creanUserImpl().updateState(ub, reviceP.getAddress().getHostAddress(),
							reviceP.getPort())) {
						ub.setResult(1);
					} else
						ub.setResult(0);
					byte[] send = ub.sealObject();
					DatagramPacket dPacket = new DatagramPacket(send, send.length, reviceP.getSocketAddress());
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
