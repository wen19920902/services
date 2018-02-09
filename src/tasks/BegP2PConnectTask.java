package tasks;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.concurrent.LinkedBlockingQueue;

import bean.udpbean.UDPRequestBean;
import interfaces.QueueObject;
import services.udp.UdpThreadPool;
import sqls.bean.KeyValuesBean;
import sqls.impl.UserProfessinalImpl;

/***
 * 请求P2P连接
 * 
 * @author wan
 *
 */
public class BegP2PConnectTask implements QueueObject {

	private static LinkedBlockingQueue<DatagramPacket> Queue = new LinkedBlockingQueue<>(100);

	private boolean isStart;// 队列是否在等待标识

	private BegP2PConnectTask() {
		if (!isStart) {
			isStart = true;
			UdpThreadPool.excute(new Task());
		}
	}

	static BegP2PConnectTask creation() {
		return Builder.Task;
	}

	private static class Builder {
		private static BegP2PConnectTask Task = new BegP2PConnectTask();
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
					DatagramPacket reivceP = Queue.take();
					ub.disassemblObject(reivceP.getData(), ub);
					int userID = ub.getUserID();
					int targetID = ub.getTargetID();
					String ip_post = UserProfessinalImpl.creanUserImpl().selectStateIp_Post(targetID);

					// 返回请求用户
					ub.setTargetIP(KeyValuesBean.getValues(ip_post));
					ub.setTargetPost(KeyValuesBean.getKey(ip_post));
					byte[] send = ub.sealObject();
					DatagramPacket dPacket = new DatagramPacket(send, send.length, reivceP.getSocketAddress());
					dSocket.send(dPacket);

					// 返回请求用户请求的目标
					ub.setUserID(targetID);
					ub.setTargetID(userID);
					ub.setTargetIP(reivceP.getAddress().getHostAddress());
					ub.setTargetPost(reivceP.getPort());
					byte[] sendT = ub.sealObject();
					DatagramPacket dPacket2 = new DatagramPacket(sendT, sendT.length,
							getIP(KeyValuesBean.getValues(ip_post)), KeyValuesBean.getKey(ip_post));
					dSocket.send(dPacket2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (dSocket != null)
				dSocket.close();

		}
	}

}
