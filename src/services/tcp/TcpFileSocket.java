package services.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

import bean.FileStoreBean;
import bean.tcpbean.FileTransferBean;
import bean.tcpbean.FileTransferBean.FileMessageBean;
import logs.LogUtil;
import sqls.impl.FileImpl;

/***
 * TCP连接任务处理对象
 * 
 * @author wan
 *
 */
public class TcpFileSocket implements Runnable {

	private Socket socket;

	public TcpFileSocket(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		byte[] reviceB = new byte[1032];
		FileTransferBean fmb = new FileTransferBean();
		FileMessageBean fb = new FileMessageBean();
		FileStoreBean fsb = new FileStoreBean();
		DataInputStream is = null;
		DataOutputStream os = null;
		boolean isConn = true;
		try {
			is = new DataInputStream(socket.getInputStream());
			os = new DataOutputStream(socket.getOutputStream());
			while (isConn) {
				is.read(reviceB);
				fmb.disassemblObject(reviceB, fmb);
				switch (fmb.getFileTransferType()) {
				case 3:// 开始传输
					fb.disassemblObject(fmb.getData(), fb);
					FileOutputStream fos = new FileOutputStream(
							new File(FileImpl.creanFileImpl().selectFilePath(fmb.getFileSaveID())));
					fsb.setFos(fos);
					fsb.setFileSavaID(fmb.getFileSaveID());
					fsb.setSerialNum(fmb.getFiletransferNum());
					fsb.setFileSendType(fmb.getFileTransferType());
					break;
				case 4:// 传输中
					if (fmb.getFiletransferNum() - fsb.getSerialNum() == 1) {
						fsb.setFileUploadLenght(fmb.getFiletransferNum() * fmb.getDataLenght());
						fsb.setSerialNum(fmb.getFiletransferNum());
						fsb.setFileSendType(fmb.getFileTransferType());
						fsb.getFos().write(fmb.getData());
					} else {
						fmb.setFiletransferNum(fsb.getSerialNum());
						os.write(fmb.sealObject());
					}
					break;
				case 5:// 传输结束
					if (fmb.getFiletransferNum() - fsb.getSerialNum() == 1 && fsb.getFileSendType() == 4) {
						fsb.getFos().write(fmb.getData());
						FileImpl.creanFileImpl().updateFileUploadLenght(fsb.getFileSavaID(),
								fsb.getFileUploadLenght() + fmb.getDataLenght());
						FileImpl.creanFileImpl().updateFileSerial(fsb.getFileSavaID(), fmb.getFiletransferNum());
						FileImpl.creanFileImpl().updateFileStatus(fsb.getFileSavaID(), 1);
						isConn = false;
					} else {
						fmb.setFiletransferNum(fsb.getSerialNum());
						os.write(fmb.sealObject());
					}
					break;
				default:
					System.out.println("你不属于我");
					break;
				}
				Thread.sleep(0);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			LogUtil.pick(1).e("tcp--" + e.getMessage());
			FileImpl.creanFileImpl().updateFileStatus(fsb.getFileSavaID(), 0);
		} finally {
			try {
				if (null != fsb.getFos())
					fsb.getFos().close();
				if (null != is)
					is.close();
				if (null != os)
					os.close();
				if (socket != null)
					socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
