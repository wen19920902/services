package tasks;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.concurrent.LinkedBlockingQueue;

import bean.udpbean.UDPRequestBean;
import interfaces.QueueObject;
import services.udp.UdpThreadPool;
import sqls.impl.MessageImpl;

/***
 * �洢��Ϣ��ҵ�� ����
 * 
 * @author wan
 *
 */

public class SendWordTask implements QueueObject {

	private static LinkedBlockingQueue<DatagramPacket> Queue = new LinkedBlockingQueue<>(100);

	private boolean isStart;// �����Ƿ��ڵȴ���ʶ

	private SendWordTask() {
		if (!isStart) {
			isStart = true;
			UdpThreadPool.excute(new Task());
		}
	}

	static SendWordTask creation() {
		return Builder.Task;
	}

	private static class Builder {
		private static SendWordTask Task = new SendWordTask();
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
					DatagramPacket reivceP = Queue.take();
					ub.disassemblObject(reivceP.getData(), ub);
					if (MessageImpl.creanMessageImpl().insertMessage_Text(ub)) {
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

		}
	}

}
