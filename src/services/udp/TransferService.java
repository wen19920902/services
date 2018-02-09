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
 * 中转服务 监听
 * 
 * @author Administrator
 *
 */
public class TransferService {

	private static Map<String, OccupyContentBean> IPTOCCUPYCONTENT = new ConcurrentHashMap<>();// IP的使用人数统计

	private static final String[] ipList = { "192.168.18.70" };

	private static int logo;// UDP使用端口唯一标识

	private static final int THREADTOLERANCE = 5; // 线程承受最佳数量

	private static final int POSTTOLERANCE = 100;// 端口承受最佳人数

	/***
	 * 开始监听
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

						if (IPTOCCUPYCONTENT.size() > 0) {// 已有用户创建连接
							UDPTransferBean ut = selctListValues(ub.getUserId());
							if (ut != null) {// 如果存在记录直接返回之前使用的端口号
								System.out.println("这个人已经来过了 好不要脸呀 还来");
								byte[] send = ut.sealObject();
								DatagramPacket dp = new DatagramPacket(send, send.length, dPacket.getSocketAddress());
								dSocket.send(dp);
								continue;
							}
							System.out.println("1===" + IPTOCCUPYCONTENT.size());
							boolean isIpUsable = false;// IP是否可用
							for (String ip : IPTOCCUPYCONTENT.keySet()) {// 查询IP集合对象
								OccupyContentBean oBean = IPTOCCUPYCONTENT.get(ip);
								if (oBean.getIpUseTime() <= THREADTOLERANCE) {// 一个IP开启三个端口后就不能再使用此Ip了
									isIpUsable = true;
									Map<Integer, Integer> postContent = oBean.getPostContent();// 端口使用人数统计对象
									boolean postEnough = false;// 开启的端口是否够用
									for (Integer post : postContent.keySet()) {// 查看端口使用情况
										int num = postContent.get(post);
										if (num < POSTTOLERANCE) {// 使用人数未大于规定数量 则可以继续使用
											postEnough = true;
											ub.setIp(ip);
											ub.setPost(post);
											ub.setUserId(getLogo());
											byte[] send = ub.sealObject();
											DatagramPacket dp = new DatagramPacket(send, send.length,
													dPacket.getSocketAddress());
											dSocket.send(dp);

											System.out.println(post + "端口使用人数" + num);
											// 将连接用户添加唯一标识和端口的绑定
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
									if (!postEnough) {// 不够用的时候 添加
										if (oBean.getIpUseTime() < THREADTOLERANCE) {// 添加时判断此IP是否可以再开启端口
											System.out.println("开始新的端口号");
											int post = RequestService.createServie(creanSocket());
											ub.setIp(ip);
											ub.setPost(post);
											ub.setUserId(getLogo());
											byte[] send = ub.sealObject();
											DatagramPacket dp = new DatagramPacket(send, send.length,
													dPacket.getSocketAddress());
											dSocket.send(dp);

											// 将连接用户添加唯一标识和端口的绑定
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
							if (!isIpUsable) {// 如果集合中的IP都不能使用时 添加 一个新的IP--这个是属于跨服务器获取端口号的操作未完成 待续
								for (String ip : ipList) {// 遍历IP列表
									if (!IPTOCCUPYCONTENT.containsKey(ip)) {// 查询出还没有开启端口的IP地址
										int post = RequestService.createServie(creanSocket());
										OccupyContentBean oc = new OccupyContentBean();
										oc.setIpUseTime(1);
										oc.setPostCount(1);
										Map<Integer, Integer> postContent = new ConcurrentHashMap<>();
										postContent.put(post, 1);
										oc.setPostContent(postContent);

										IPTOCCUPYCONTENT.put(ip, oc);// 查询到后就添加
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
						} else {// 第一个建立连接的用户
							System.out.println("第一次");
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
							// 将连接用户添加唯一标识和端口的绑定
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
	 * @return 创建一个空闲的UDPSocket
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
	 *            查询目标
	 * @return 查询集合的值是否存在查询目标
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
