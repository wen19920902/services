package services.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import bean.OccupyContentBean;
import bean.udpbean.UDPTransferBean;

/**
 * ��ת���� ����
 * 
 * @author Administrator
 *
 */
public class TransferService {

	private static Map<String, OccupyContentBean> IPTOCCUPYCONTENT = new ConcurrentHashMap<>();// IP��ʹ������ͳ��

	private static final String[] ipList = { "192.168.18.70" };

	private static int logo;// UDPʹ�ö˿�Ψһ��ʶ

	private static final int THREADTOLERANCE = 5; // �̳߳����������

	private static final int POSTTOLERANCE = 100;// �˿ڳ����������

	/***
	 * ��ʼ����
	 */
	public static void revice() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				byte[] csbytes = new byte[12];
				UDPTransferBean ub = new UDPTransferBean();
				DatagramSocket dSocket = null;
				try {
					dSocket = new DatagramSocket(12345);
					while (true) {
						DatagramPacket dPacket = new DatagramPacket(csbytes, csbytes.length);
						dSocket.receive(dPacket);
						ub.disassemblObject(dPacket.getData(), ub);

						if (IPTOCCUPYCONTENT.size() > 0) {// �����û���������
							UDPTransferBean ut = selctListValues(ub.getUserId());
							if (ut != null) {// ������ڼ�¼ֱ�ӷ���֮ǰʹ�õĶ˿ں�
								System.out.println("������Ѿ������� �ò�Ҫ��ѽ ����");
								byte[] send = ut.sealObject();
								DatagramPacket dp = new DatagramPacket(send, send.length, dPacket.getSocketAddress());
								dSocket.send(dp);
								continue;
							}
							System.out.println("1===" + IPTOCCUPYCONTENT.size());
							boolean isIpUsable = false;// IP�Ƿ����
							for (String ip : IPTOCCUPYCONTENT.keySet()) {// ��ѯIP���϶���
								OccupyContentBean oBean = IPTOCCUPYCONTENT.get(ip);
								if (oBean.getIpUseTime() <= THREADTOLERANCE) {// һ��IP���������˿ں�Ͳ�����ʹ�ô�Ip��
									isIpUsable = true;
									Map<Integer, Integer> postContent = oBean.getPostContent();// �˿�ʹ������ͳ�ƶ���
									boolean postEnough = false;// �����Ķ˿��Ƿ���
									for (Integer post : postContent.keySet()) {// �鿴�˿�ʹ�����
										int num = postContent.get(post);
										if (num < POSTTOLERANCE) {// ʹ������δ���ڹ涨���� ����Լ���ʹ��
											postEnough = true;
											ub.setIp(ip);
											ub.setPost(post);
											ub.setUserId(getLogo());
											byte[] send = ub.sealObject();
											DatagramPacket dp = new DatagramPacket(send, send.length,
													dPacket.getSocketAddress());
											dSocket.send(dp);

											System.out.println(post + "�˿�ʹ������" + num);
											// �������û����Ψһ��ʶ�Ͷ˿ڵİ�
											Map<Integer, List<Integer>> connectMap = oBean.getConnectMap();
											List<Integer> userIdList = connectMap.get(post);
											userIdList.add(ub.getUserId());
											connectMap.put(post, userIdList);
											oBean.setConnectMap(connectMap);

											postContent.put(post, num + 1);
											oBean.setPostContent(postContent);
											IPTOCCUPYCONTENT.put(ip, oBean);
											break;
										}
									}
									if (!postEnough) {// �����õ�ʱ�� ���
										if (oBean.getIpUseTime() < THREADTOLERANCE) {// ���ʱ�жϴ�IP�Ƿ�����ٿ����˿�
											System.out.println("��ʼ�µĶ˿ں�");
											int post = RequestService.createServie(creanSocket());
											ub.setIp(ip);
											ub.setPost(post);
											ub.setUserId(getLogo());
											byte[] send = ub.sealObject();
											DatagramPacket dp = new DatagramPacket(send, send.length,
													dPacket.getSocketAddress());
											dSocket.send(dp);

											// �������û����Ψһ��ʶ�Ͷ˿ڵİ�
											Map<Integer, List<Integer>> connectMap = oBean.getConnectMap();
											List<Integer> userIdList = new CopyOnWriteArrayList<>();
											userIdList.add(ub.getUserId());
											connectMap.put(post, userIdList);
											oBean.setConnectMap(connectMap);

											oBean.setIpUseTime(oBean.getIpUseTime() + 1);
											postContent.put(post, 1);
											oBean.setPostContent(postContent);
											IPTOCCUPYCONTENT.put(ip, oBean);
										}
									}
								}
							}
							if (!isIpUsable) {// ��������е�IP������ʹ��ʱ ��� һ���µ�IP--��������ڿ��������ȡ�˿ںŵĲ���δ��� ����
								for (String ip : ipList) {// ����IP�б�
									if (!IPTOCCUPYCONTENT.containsKey(ip)) {// ��ѯ����û�п����˿ڵ�IP��ַ
										int post = RequestService.createServie(creanSocket());
										OccupyContentBean oc = new OccupyContentBean();
										oc.setIpUseTime(1);
										oc.setPostCount(1);
										Map<Integer, Integer> postContent = new ConcurrentHashMap<>();
										postContent.put(post, 1);
										oc.setPostContent(postContent);

										IPTOCCUPYCONTENT.put(ip, oc);// ��ѯ��������
										ub.setIp(ip);
										ub.setPost(post);
										ub.setUserId(getLogo());
										byte[] send = ub.sealObject();
										DatagramPacket dp = new DatagramPacket(send, send.length,
												dPacket.getSocketAddress());
										dSocket.send(dp);
										break;
									}
								}
							}
						} else {// ��һ���������ӵ��û�
							System.out.println("��һ��");
							String ip = ipList[0];
							int post = RequestService.createServie(creanSocket());

							ub.setIp(ip);
							ub.setPost(post);
							ub.setUserId(getLogo());

							OccupyContentBean oc = new OccupyContentBean();
							oc.setIpUseTime(1);
							oc.setPostCount(1);
							Map<Integer, Integer> postContent = new ConcurrentHashMap<>();
							postContent.put(post, 1);
							oc.setPostContent(postContent);
							// �������û����Ψһ��ʶ�Ͷ˿ڵİ�
							Map<Integer, List<Integer>> connectMap = new ConcurrentHashMap<>();
							List<Integer> userIdList = new CopyOnWriteArrayList<>();
							userIdList.add(ub.getUserId());
							connectMap.put(post, userIdList);
							oc.setConnectMap(connectMap);

							IPTOCCUPYCONTENT.put(ip, oc);

							byte[] send = ub.sealObject();
							DatagramPacket dp = new DatagramPacket(send, send.length, dPacket.getSocketAddress());
							dSocket.send(dp);
						}
					}
				} catch (SocketException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					if (null != dSocket)
						dSocket.close();
				}
			}
		}).start();
	}

	/***
	 * 
	 * @return ����һ�����е�UDPSocket
	 * @throws SocketException
	 */
	private static DatagramSocket creanSocket() throws SocketException {
		return new DatagramSocket();
	}

	private static synchronized int getLogo() {
		return logo + 1;
	}

	/***
	 * 
	 * @param userID
	 *            ��ѯĿ��
	 * @return ��ѯ���ϵ�ֵ�Ƿ���ڲ�ѯĿ��
	 */
	private static UDPTransferBean selctListValues(int userID) {
		for (String ip : IPTOCCUPYCONTENT.keySet()) {
			Map<Integer, List<Integer>> connectMap = IPTOCCUPYCONTENT.get(ip).getConnectMap();
			for (Integer post : connectMap.keySet()) {
				for (Integer logo : connectMap.get(post)) {
					if (logo == userID) {
						UDPTransferBean ub = new UDPTransferBean();
						ub.setPost(post);
						ub.setIp(ip);
						ub.setUserId(logo);
						return ub;
					}
				}
			}
		}
		return null;
	}

}
