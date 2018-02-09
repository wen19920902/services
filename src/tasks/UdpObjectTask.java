package tasks;

import java.io.File;
import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public abstract class UdpObjectTask implements Runnable {

//	protected static final String IP = "192.168.18.70";

	protected DatagramSocket dSocket;

	protected UdpObjectTask() {
		try {
			dSocket = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取查找 服务器对象
	 *
	 * @param ip
	 *            请求服务器的IP
	 * @return 连接对象
	 */
	protected InetAddress getIP(String ip) {
		InetAddress local = null;
		try {
			local = InetAddress.getByName(ip);
		} catch (UnknownHostException e) {
			return null;
		}
		return local;
	}

	/***
	 * 
	 * @param userID
	 *            文件目录名
	 * @param fileName
	 *            文件名
	 * @return 获取文件存储 路径
	 * @throws IOException
	 */
	protected File creanFile(int userID, String fileName) throws IOException {
		File file = new File("F:\\test\\" + userID);
		if (file.exists())
			file.mkdirs();
		File file2 = new File(file.getAbsolutePath() + "\\" + fileName);
		if (file2.exists())
			file2.createNewFile();
		return file;
	}

}
