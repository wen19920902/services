package tasks;

import java.io.IOException;
import java.net.DatagramPacket;
import java.util.concurrent.LinkedBlockingQueue;

import bean.udpbean.UDPRequestBean;
import bean.udpbean.UDPRequestBean.UdpFileDataBean;
import constant.IP;
import interfaces.QueueObject;
import services.tcp.TcpTranspondService;
import services.udp.UdpThreadPool;
import sqls.impl.FileImpl;
import sqls.impl.MessageImpl;

/***
 * ת���ļ�������
 * 
 * @author wan
 *
 */
public class TranspondFileTask implements QueueObject {

	private static LinkedBlockingQueue<DatagramPacket> Queue = new LinkedBlockingQueue<>(100);

	private boolean isStart;// �����Ƿ��ڵȴ���ʶ

	private TranspondFileTask() {
		if (!isStart) {
			isStart = true;
			UdpThreadPool.excute(new Task());
		}
	}

	static TranspondFileTask creation() {
		return Builder.Task;
	}

	private static class Builder {
		private static TranspondFileTask Task = new TranspondFileTask();
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
		private UdpFileDataBean ufb = new UdpFileDataBean();// ���ݿ����ļ�

		public Task() {
			super();
		}

		@Override
		public void run() {
			while (isStart) {
				try {
					DatagramPacket reviceP = Queue.take();
					ub.disassemblObject(reviceP.getData(), ub);
					ufb.disassemblObject(ub.getData(), ufb);
					int id = FileImpl.creanFileImpl().insertFile(ub,
							creanFile(ub.getUserID(), ufb.getFileName()).getAbsolutePath());
					if (id != -1) {
						ufb.setFileSaveID(id);
						ub.setData(ufb.sealObject());
						if (MessageImpl.creanMessageImpl().insertMessage_File(ub)) {
							ub.setTargetIP(IP.IP);
							ub.setTargetPost(TcpTranspondService.reviceTcp());
							ub.setResult(1);
							byte[] sendb = ub.sealObject();
							DatagramPacket sendP = new DatagramPacket(sendb, sendb.length, reviceP.getSocketAddress());
							dSocket.send(sendP);
							continue;
						}
					}
					ub.setResult(0);
					byte[] sendb = ub.sealObject();
					DatagramPacket sendP = new DatagramPacket(sendb, sendb.length, reviceP.getSocketAddress());
					dSocket.send(sendP);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}

}
