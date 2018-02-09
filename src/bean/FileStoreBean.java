package bean;

import java.io.FileOutputStream;
import java.io.Serializable;

/***
 * �ļ��洢 ʵ�����
 * 
 * @author wan
 *
 */
public class FileStoreBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -989048897393073663L;

	private int id;
	private int fileSendType;// �ļ� ��������
	private int fileSavaID;// �ļ��洢ID
	private int serialNum;// �ļ� �������
	private int fileUploadLenght;// �ļ��ϴ��ĳ���
	private FileOutputStream fos;// �ļ������

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getFileSendType() {
		return fileSendType;
	}

	public void setFileSendType(int fileSendType) {
		this.fileSendType = fileSendType;
	}

	public int getFileSavaID() {
		return fileSavaID;
	}

	public void setFileSavaID(int fileSavaID) {
		this.fileSavaID = fileSavaID;
	}

	public int getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(int serialNum) {
		this.serialNum = serialNum;
	}

	public int getFileUploadLenght() {
		return fileUploadLenght;
	}

	public void setFileUploadLenght(int fileUploadLenght) {
		this.fileUploadLenght = fileUploadLenght;
	}

	public FileOutputStream getFos() {
		return fos;
	}

	public void setFos(FileOutputStream fos) {
		this.fos = fos;
	}

}
