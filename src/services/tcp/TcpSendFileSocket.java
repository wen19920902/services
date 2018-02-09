package services.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.Socket;
import bean.tcpbean.FileTransferBean;
import sqls.impl.FileImpl;

/***
 * TCP�ļ�����Socket����
 * 
 * @author wan
 *
 */
public class TcpSendFileSocket implements Runnable {

	private Socket socket;

	public TcpSendFileSocket(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		byte[] reviceB = new byte[1032];
		FileTransferBean fmb = new FileTransferBean();
		DataInputStream is = null;
		DataOutputStream os = null;
		byte[] bs = new byte[1000];// һ�ζ�ȡ�ֽ���
		RandomAccessFile rf = null;// �ļ���ȡ����
		long location = 0;// ��ȡ����ʼλ��
		try {
			is = new DataInputStream(socket.getInputStream());
			os = new DataOutputStream(socket.getOutputStream());
			is.read(reviceB);
			fmb.disassemblObject(reviceB, fmb);
			if (rf == null) {
				String path = FileImpl.creanFileImpl().selectFilePath(fmb.getFileSaveID());
				rf = new RandomAccessFile(new File(path), "r");
				rf.seek(0);
				long size = rf.length();
				long finallyLocation = size - size / bs.length;
				while (location < rf.length()) {
					if (is.read(reviceB) == -1) {
						if (location + bs.length == rf.getFilePointer())
							location = rf.getFilePointer();
						rf.seek(location);
						if (location < finallyLocation)// �жϽ�ȡ��λ���Ƿ����һ֡
							fmb.setFileType(4);
						else
							fmb.setFileType(5);
						fmb.setDataLenght(rf.read(bs));
						fmb.setData(bs);
						os.write(fmb.sealObject());
					} else {
						fmb.disassemblObject(reviceB, fmb);
						location = fmb.getFiletransferNum() * bs.length;
					}
				}
				rf.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != is)
					is.close();
				if (null != os)
					os.close();
				if (null != socket)
					socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
