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
	 * ��ȡ���� ����������
	 *
	 * @param ip
	 *            �����������IP
	 * @return ���Ӷ���
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
	 *            �ļ�Ŀ¼��
	 * @param fileName
	 *            �ļ���
	 * @return ��ȡ�ļ��洢 ·��
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
