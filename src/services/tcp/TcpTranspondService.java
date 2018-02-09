package services.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/***
 * TCP转发文件的监听
 * 
 * @author wan
 *
 */

public class TcpTranspondService {
	
	private static ServerSocket socket;// 监听用户发送文件的Socket

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
	 * 添加一个TCP连接
	 * 
	 * @param socket
	 *            连接目标
	 */
	private static void addScoket(Socket socket) {
		TcpTranspondFileSocket tcpSocket = new TcpTranspondFileSocket(socket);
		TcpThreadPool.excute(tcpSocket);
	}

}
