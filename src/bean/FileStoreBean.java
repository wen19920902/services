package bean;

import java.io.FileOutputStream;
import java.io.Serializable;

/***
 * 文件存储 实体对象
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
	private int fileSendType;// 文件 发送类型
	private int fileSavaID;// 文件存储ID
	private int serialNum;// 文件 发送序号
	private int fileUploadLenght;// 文件上传的长度
	private FileOutputStream fos;// 文件输出流

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
