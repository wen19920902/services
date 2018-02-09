package services.tcp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import bean.FileStoreBean;
import bean.tcpbean.FileTransferBean;
import bean.tcpbean.FileTransferBean.FileMessageBean;
import bean.udpbean.UDPRequestBean;
import bean.udpbean.UDPRequestBean.UdpFileDataBean;
import constant.IP;
import logs.LogUtil;
import services.udp.UdpTranspondMonitrService;
import sqls.bean.KeyValuesBean;
import sqls.impl.FileImpl;
import sqls.impl.UserProfessinalImpl;

/***
 * TCP文件转发的处理
 * 
 * @author wan
 *
 */
public class TcpTranspondFileSocket implements Runnable {

	private Socket socket;// TCP连接Socket

	public TcpTranspondFileSocket(Socket socket) {
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
		int date = 0, time = 0;
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
					date = fmb.byteToInts(fmb.getNowDate());
					time = fmb.byteToInts(fmb.getNowTime());
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

						String ip_post = UserProfessinalImpl.creanUserImpl().selectStateIp_Post(fmb.getTragerID());

						UDPRequestBean urb = new UDPRequestBean();
						UdpFileDataBean uf = new UdpFileDataBean();
						urb.setOrder(7);
						urb.setUserID(fmb.getUserID());
						urb.setTargetID(fmb.getTragerID());
						urb.setTargetIP(IP.IP);
						urb.setTargetPost(TcpSendFileService.reviceTcp());
						urb.setWordType(fmb.getFileType());
						urb.setDate(date);
						urb.setTime(time);

						uf.setFileLenght(fsb.getFileUploadLenght());
						uf.setFileName(fb.getFileName());
						uf.setFileSaveID(fsb.getFileSavaID());
						uf.setFileCreanData(fb.getFileCreanTime());

						byte[] fileb = uf.sealObject();
						urb.setDataLeght(fileb.length);
						urb.setData(fileb);
						byte[] send = urb.sealObject();
						DatagramPacket dPacket = new DatagramPacket(send, send.length,
								getIP(KeyValuesBean.getValues(ip_post)), KeyValuesBean.getKey(ip_post));
						UdpTranspondMonitrService.gainSocket().send(dPacket);
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
			isConn = false;
			FileImpl.creanFileImpl().updateFileUploadLenght(fsb.getFileSavaID(), fsb.getFileUploadLenght());
			FileImpl.creanFileImpl().updateFileSerial(fsb.getFileSavaID(), fsb.getSerialNum());
			FileImpl.creanFileImpl().updateFileStatus(fsb.getFileSavaID(), 0);
		} finally {
			try {
				if (null != fsb.getFos())
					fsb.getFos().close();
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

	/**
	 * 获取查找 服务器对象
	 *
	 * @param ip
	 *            请求服务器的IP
	 * @return 连接对象
	 */

	private InetAddress getIP(String ip) {
		InetAddress local = null;
		try {
			local = InetAddress.getByName(ip);
		} catch (UnknownHostException e) {
			return null;
		}
		return local;
	}

}
