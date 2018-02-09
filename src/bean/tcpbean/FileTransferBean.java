package bean.tcpbean;

import java.util.Arrays;

import bean.ObjectUtils;

/***
 * �ļ�����ʵ�����
 * 
 * @author wan
 *
 */
public class FileTransferBean extends ObjectUtils<FileTransferBean> {

	/**
	 * ϵ�к�
	 */
	private static final long serialVersionUID = 8541039522377872991L;

	private int id;// ���
	private byte[] userID = new byte[4];// �û�ID
	private byte[] tragerID = new byte[4];// ����Ŀ��ID
	private byte[] filetransferNum = new byte[4];// �ļ��������
	private byte[] fileTransferType = new byte[4];// �ļ��������ͱ�ʶ
	private byte[] fileSaveID = new byte[4];// �ļ��洢��¼ID
	private byte[] fileType = new byte[4];// �ļ�����
	private byte[] transferType = new byte[4];// ��������(p2p p2s2p)
	private byte[] dataLenght = new byte[4];// ���ݿ���Ч����
	private byte[] data = new byte[4];// ���ݿ�

	@Override
	public byte[] sealObject() {
		byte[] send = new byte[1032];
		jointByte = 0;
		shiftByte(send, userID);
		shiftByte(send, tragerID);
		shiftByte(send, filetransferNum);
		shiftByte(send, fileTransferType);
		shiftByte(send, fileSaveID);
		shiftByte(send, fileType);
		shiftByte(send, transferType);
		shiftByte(send, dataLenght);
		shiftByte(send, data);
		return send;
	}

	@Override
	public void disassemblObject(byte[] datas, FileTransferBean t) {
		t.userID = getByte(datas, 0);
		t.tragerID = getByte(datas, 4);
		t.filetransferNum = getByte(datas, 8);
		t.fileTransferType = getByte(datas, 12);
		t.fileSaveID = getByte(datas, 16);
		t.fileType = getByte(datas, 20);
		t.transferType = getByte(datas, 24);
		t.dataLenght = getByte(datas, 28);
		t.data = getValidByte(datas, 32, t.getDataLenght());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserID() {
		return bytesToIntLow(userID);
	}

	public void setUserID(int userID) {
		this.userID = intToByteLow(userID);
	}

	public int getTragerID() {
		return bytesToIntLow(tragerID);
	}

	public void setTragerID(int tragerID) {
		this.tragerID = intToByteLow(tragerID);
	}

	public int getFiletransferNum() {
		return bytesToIntLow(filetransferNum);
	}

	public void setFiletransferNum(int filetransferNum) {
		this.filetransferNum = intToByteLow(filetransferNum);
	}

	public int getFileTransferType() {
		return bytesToIntLow(fileTransferType);
	}

	public void setFileTransferType(int fileTransferType) {
		this.fileTransferType = intToByteLow(fileTransferType);
	}

	public int getFileSaveID() {
		return bytesToIntLow(fileSaveID);
	}

	public void setFileSaveID(int fileSaveID) {
		this.fileSaveID = intToByteLow(fileSaveID);
	}

	public int getFileType() {
		return bytesToIntLow(fileType);
	}

	public void setFileType(int fileType) {
		this.fileType = intToByteLow(fileType);
	}

	public int getTransferType() {
		return bytesToIntLow(transferType);
	}

	public void setTransferType(int transferType) {
		this.transferType = intToByteLow(transferType);
	}

	public int getDataLenght() {
		return bytesToIntLow(dataLenght);
	}

	public void setDataLenght(int dataLenght) {
		this.dataLenght = intToByteLow(dataLenght);
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	/***
	 * 
	 * @param src
	 *            ת��Ŀ��
	 * @return �ֽ�����ת����
	 */
	public int byteToInts(byte[] src) {
		return bytesToIntLow(src);
	}

	public static class FileMessageBean extends ObjectUtils<FileMessageBean> {

		/**
		 * ϵ�к�
		 */
		private static final long serialVersionUID = 1747416973070748748L;

		private byte[] fileLenght = new byte[4];// �ļ�����
		private byte[] fileName = new byte[72];// �ļ���
		private byte[] fileCreanTime = new byte[32];// �ļ�����ʱ��/�޸�ʱ��

		@Override
		public byte[] sealObject() {
			byte send[] = new byte[108];
			jointByte = 0;
			shiftByte(send, fileLenght);
			shiftByte(send, byteCoding(fileName));
			shiftByte(send, byteCoding(fileCreanTime));
			return null;
		}

		@Override
		public void disassemblObject(byte[] datas, FileMessageBean t) {
			t.fileLenght = getByte(datas, 0);
			t.fileName = byteCoding(getValidByte(datas, 4, 72));
			t.fileCreanTime = byteCoding(getValidByte(datas, 76, 32));
		}

		public int getFileLenght() {
			return bytesToIntLow(fileLenght);
		}

		public void setFileLenght(int fileLenght) {
			this.fileLenght = intToByteLow(fileLenght);
		}

		public String getFileName() {
			return new String(fileName).trim();
		}

		public void setFileName(String fileName) {
			if (fileName != null)
				this.fileName = joStringByte(fileName, 72);
		}

		public String getFileCreanTime() {
			return new String(fileCreanTime).trim();
		}

		public void setFileCreanTime(String fileCreanTime) {
			this.fileCreanTime = joStringByte(fileCreanTime, 32);
		}

		@Override
		public String toString() {
			return "FileMessageBean [fileLenght=" + Arrays.toString(fileLenght) + ", fileName="
					+ Arrays.toString(fileName) + ", fileCreanTime=" + Arrays.toString(fileCreanTime) + "]";
		}
	}

	@Override
	public String toString() {
		return "FileTransferBean [id=" + id + ", userID=" + Arrays.toString(userID) + ", tragerID="
				+ Arrays.toString(tragerID) + ", filetransferNum=" + Arrays.toString(filetransferNum)
				+ ", fileTransferType=" + Arrays.toString(fileTransferType) + ", fileSaveID="
				+ Arrays.toString(fileSaveID) + ", fileType=" + Arrays.toString(fileType) + ", transferType="
				+ Arrays.toString(transferType) + ", dataLenght=" + Arrays.toString(dataLenght) + ", data="
				+ Arrays.toString(data) + "]";
	}

}
