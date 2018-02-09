package tasks;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.concurrent.LinkedBlockingQueue;

import bean.udpbean.UDPRequestBean;
import interfaces.QueueObject;
import services.udp.UdpThreadPool;
import services.udp.UdpTranspondMonitrService;
import sqls.bean.KeyValuesBean;
import sqls.impl.MessageImpl;
import sqls.impl.UserProfessinalImpl;

/***
 * 转发文本消息的任务
 * 
 * @author wan
 *
 */
public class TranspondWordTask implements QueueObject {

	private static LinkedBlockingQueue<DatagramPacket> Queue = new LinkedBlockingQueue<>(100);
	private boolean isStart;// 队列是否在等待标识

	private TranspondWordTask() {
		if (!isStart) {
			isStart = true;
			UdpThreadPool.excute(new Task());
		}
	}

	static TranspondWordTask creation() {
		return Builder.Task;
	}

	static class Builder {
		private static TranspondWordTask Task = new TranspondWordTask();
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
	 * @author wan 任务
	 *
	 */
	class Task extends UdpObjectTask {

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
					if (MessageImpl.creanMessageImpl().insertMessage_Text(ub)) {
						ub.setResult(1);
					} else
						ub.setResult(0);
					byte[] send = ub.sealObject();
					DatagramPacket dPacket = new DatagramPacket(send, send.length, reivceP.getSocketAddress());
					dSocket.send(dPacket);

					String ip_post = UserProfessinalImpl.creanUserImpl().selectStateIp_Post(ub.getTargetID());
					DatagramPacket dPacket2 = new DatagramPacket(send, send.length,
							getIP(KeyValuesBean.getValues(ip_post)), KeyValuesBean.getKey(ip_post));
					UdpTranspondMonitrService.gainSocket().send(dPacket2);

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
