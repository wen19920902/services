package services.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/***
 * TCPת���ļ��ļ���
 * 
 * @author wan
 *
 */

public class TcpTranspondService {
	
	private static ServerSocket socket;// �����û������ļ���Socket

	public static int reviceTcp() {
		if (socket == null)
			synchronized (socket) {
				try {
					socket = new ServerSocket(0);
					addScoket(socket.accept());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		return socket.getLocalPort();
	}

	/***
	 * ���һ��TCP����
	 * 
	 * @param socket
	 *            ����Ŀ��
	 */
	private static void addScoket(Socket socket) {
		TcpTranspondFileSocket tcpSocket = new TcpTranspondFileSocket(socket);
		TcpThreadPool.excute(tcpSocket);
	}

}
