package services.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import services.utils.ObjectUtils;
import tasks.DispatchTask;

/***
 * 请求处理服务监听
 * 
 * @author Administrator
 *
 */
public class RequestService extends ObjectUtils implements Runnable {

	private RequestService(DatagramSocket ds) {
		super(ds);
	}

	/***
	 * 
	 * @param ds
	 *            监听目标
	 * @return 创建一个监听UDP 返回当前监听端口
	 */
	public static int createServie(DatagramSocket ds) {
		RequestService rs = new RequestService(ds);
		UdpThreadPool.excute(rs);
		return rs.post;
	}

	@Override
	public void run() {
		byte[] csbyts = new byte[1024];// 接收用户发送的数据
		byte[] heanByte = new byte[4];// 解析用户发送的命令
		try {
			while (true) {
				DatagramPacket reviceP = new DatagramPacket(csbyts, csbyts.length);
				ds.receive(reviceP);
				System.arraycopy(heanByte, 0, reviceP.getData(), 0, heanByte.length);
				switch (bytesToIntLow(heanByte)) {
				case 1:// 用户登录
					DispatchTask.crean(DispatchTask.LAND).put(reviceP);
					break;
				case 2:// 用户注册
					DispatchTask.crean(DispatchTask.ERROR).put(reviceP);
					break;
				case 3:// 在线报道
					DispatchTask.crean(DispatchTask.ONLINE).put(reviceP);
					break;
				case 4:// 请求p2p连接目标
					DispatchTask.crean(DispatchTask.BEGP2P).put(reviceP);
					break;
				case 5:// 发送消息(存储)
					DispatchTask.crean(DispatchTask.SENDWORD).put(reviceP);
					break;
				case 6:// 发送消息(存储加转发)
					DispatchTask.crean(DispatchTask.TSENDWORD).put(reviceP);
					break;
				case 7:// 发送文件(存储)
					DispatchTask.crean(DispatchTask.SENDFILE).put(reviceP);
					break;
				case 8:// 发送文件(存储加转发)
					DispatchTask.crean(DispatchTask.TSENDFILE).put(reviceP);
					break;
				case 9:// 请求获取班级信息
					DispatchTask.crean(DispatchTask.CLASSDATA).put(reviceP);
					break;
				case 10:// 请求获取个人信息
					DispatchTask.crean(DispatchTask.PERSONALDATA).put(reviceP);
					break;
				case 11:// 设备登录
					DispatchTask.crean(DispatchTask.FLAND).put(reviceP);
					break;
				case 12:// 设备注册
					DispatchTask.crean(DispatchTask.FERROR).put(reviceP);
					break;
				case 13:// 上传设备截屏文件
					DispatchTask.crean(DispatchTask.FSENDSCREEN).put(reviceP);
					break;
				case 14:// 上传设备时长
					DispatchTask.crean(DispatchTask.FSENDMIN).put(reviceP);
					break;
				case 15:// 操作设备
					DispatchTask.crean(DispatchTask.FCONTROL).put(reviceP);
					break;

				default:
					System.out.println("这个是什么?");
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != ds)
				ds.close();
		}
	}

}
