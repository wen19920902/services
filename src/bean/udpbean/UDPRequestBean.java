package bean.udpbean;

import java.util.Arrays;

import bean.ObjectUtils;

/***
 * 
 * @author wan UDP的请求实体对象
 *
 */
public class UDPRequestBean extends ObjectUtils<UDPRequestBean> {

	/**
	 * 系列号
	 */
	private static final long serialVersionUID = -203885847199381910L;

	private int id;// 编号
	private byte[] order = new byte[4];// 命令
	private byte[] userID = new byte[4];// 请求用户ID
	private byte[] targetID = new byte[4];// 请求操作目标ID
	private byte[] targetIP = new byte[4];// 请求操作目标IP地址
	private byte[] targetPost = new byte[4];// 请求操作目标端口号
	private byte[] wordType = new byte[4];// 请求消息类型
	private byte[] requstPlatForm = new byte[4];// 请求平台
	private byte[] date = new byte[4];// 请求日期
	private byte[] time = new byte[4];// 请求时间
	private byte[] result = new byte[4];// 请求结果
	private byte[] dataLeght = new byte[4];// 请求数据块有效长度
	private byte[] data = new byte[960];// 请求数据块

	@Override
	public byte[] sealObject() {
		jointByte = 0;
		byte[] send = new byte[1004];
		shiftByte(send, order);
		shiftByte(send, userID);
		shiftByte(send, targetID);
		shiftByte(send, targetIP);
		shiftByte(send, targetPost);
		shiftByte(send, wordType);
		shiftByte(send, requstPlatForm);
		shiftByte(send, date);
		shiftByte(send, time);
		shiftByte(send, result);
		shiftByte(send, dataLeght);
		shiftByte(send, data);
		return send;
	}

	@Override
	public void disassemblObject(byte[] datas, UDPRequestBean t) {
		t.order = getByte(datas, 0);
		t.userID = getByte(datas, 4);
		t.targetID = getByte(datas, 8);
		t.targetIP = getByte(datas, 12);
		t.targetPost = getByte(datas, 16);
		t.wordType = getByte(datas, 20);
		t.requstPlatForm = getByte(datas, 24);
		t.date = getByte(datas, 28);
		t.time = getByte(datas, 32);
		t.result = getByte(datas, 36);
		t.dataLeght = getByte(datas, 40);
		t.data = getValidByte(datas, 44, t.getDataLeght());
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getOrder() {
		return bytesToIntLow(order);
	}

	public void setOrder(int order) {
		this.order = intToByteLow(order);
	}

	public Integer getUserID() {
		return bytesToIntLow(userID);
	}

	public void setUserID(int userID) {
		this.userID = intToByteLow(userID);
	}

	public int getTargetID() {
		return bytesToIntLow(targetID);
	}

	public void setTargetID(int targetID) {
		this.targetID = intToByteLow(targetID);
	}

	public String getTargetIP() {
		return resoleIpInt(targetIP);
	}

	public void setTargetIP(String targetIP) {
		this.targetIP = intToByteLow(resoleIPString(targetIP));
	}

	public int getTargetPost() {
		return bytesToIntLow(targetPost);
	}

	public void setTargetPost(int targetPost) {
		this.targetPost = intToByteLow(targetPost);
	}

	public int getWordType() {
		return bytesToIntLow(wordType);
	}

	public void setWordType(int wordType) {
		this.wordType = intToByteLow(wordType);
	}

	public int getRequstPlatForm() {
		return bytesToIntLow(requstPlatForm);
	}

	public void setRequstPlatForm(int requstPlatForm) {
		this.requstPlatForm = intToByteLow(requstPlatForm);
	}

	public String getDate() {
		return parseDate(date, 0);
	}

	public void setDate(int date) {
		if (date == 0)
			this.date = getNowDate();
		else
			this.date = getSettingDate(date);
	}

	public String getTime() {
		return parseDate(time, 1);
	}

	public void setTime(int time) {
		if (time == 0)
			this.time = getNowTime();
		else
			this.time = getSettingTime(time);
	}

	public int getResult() {
		return bytesToIntLow(result);
	}

	public void setResult(int result) {
		this.result = intToByteLow(result);
	}

	public int getDataLeght() {
		return bytesToIntLow(dataLeght);
	}

	public void setDataLeght(int dataLeght) {
		this.dataLeght = intToByteLow(dataLeght);
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "UDPRequestBean [id=" + id + ", order=" + Arrays.toString(order) + ", userID=" + Arrays.toString(userID)
				+ ", targetID=" + Arrays.toString(targetID) + ", targetIP=" + Arrays.toString(targetIP)
				+ ", targetPost=" + Arrays.toString(targetPost) + ", wordType=" + Arrays.toString(wordType)
				+ ", requstPlatForm=" + Arrays.toString(requstPlatForm) + ", date=" + Arrays.toString(date) + ", time="
				+ Arrays.toString(time) + ", result=" + Arrays.toString(result) + ", dataLeght="
				+ Arrays.toString(dataLeght) + ", data=" + Arrays.toString(data) + "]";
	}

	/***
	 * UDP用户登录 注册 获取个人信息的数据块
	 * 
	 * @author wan
	 *
	 */
	public static class UdpUserDataBean extends ObjectUtils<UdpUserDataBean> {

		/**
		 * 系列号
		 */
		private static final long serialVersionUID = -7378410053301991558L;
		private byte[] userID = new byte[4];// 用户ID
		private byte[] userAcoount = new byte[52];// 用户账号
		private byte[] userPassword = new byte[52];// 用户密码
		private byte[] userNick = new byte[52];// 用户昵称
		private byte[] userCode = new byte[32];// 验证码
		private byte[] userIconID = new byte[4];// 用户头像ID
		private byte[] userType = new byte[4];// 用户类型
		private byte[] userState = new byte[4];// 用户状态
		private byte[] userClassID = new byte[4];// 用户班级ID
		private byte[] userClassName = new byte[52];// 用户班级名
		private byte[] schoolName = new byte[52];// 用户学校名

		public int getUserID() {
			return bytesToIntLow(userID);
		}

		public void setUserID(int userID) {
			this.userID = intToByteLow(userID);
		}

		public String getUserAcoount() {
			return new String(userAcoount).trim();
		}

		public void setUserAcoount(String userAcoount) {
			if (userAcoount != null)
				this.userAcoount = joStringByte(userAcoount, 52);
		}

		public String getUserNick() {
			return new String(userNick).trim();
		}

		public void setUserNick(String userNick) {
			if (userNick != null)
				this.userNick = joStringByte(userNick, 32);
		}

		public String getUserPassword() {
			return new String(userPassword).trim();
		}

		public void setUserPassword(String userPassword) {
			if (userPassword != null)
				this.userPassword = joStringByte(userPassword, 52);
		}

		public String getUserCode() {
			return new String(userCode).trim();
		}

		public void setUserCode(String userCode) {
			if (userCode != null)
				this.userCode = joStringByte(userCode, 32);
		}

		public int getUserIconID() {
			return bytesToIntLow(userIconID);
		}

		public void setUserIconID(int userIconID) {
			this.userIconID = intToByteLow(userIconID);
		}

		public int getUserType() {
			return bytesToIntLow(userType);
		}

		public void setUserType(int userType) {
			this.userType = intToByteLow(userType);
		}

		public int getUserState() {
			return bytesToIntLow(userState);
		}

		public void setUserState(int userState) {
			this.userState = intToByteLow(userState);
		}

		public int getUserClassID() {
			return bytesToIntLow(userClassID);
		}

		public void setUserClassID(int userClassID) {
			this.userClassID = intToByteLow(userClassID);
		}

		public String getUserClassName() {
			return new String(userClassName).trim();
		}

		public void setUserClassName(String userClassName) {
			if (userClassName != null)
				this.userClassName = joStringByte(userClassName, 52);
		}

		public String getSchoolName() {
			return new String(schoolName).trim();
		}

		public void setSchoolName(String schoolName) {
			if (schoolName != null)
				this.schoolName = joStringByte(schoolName, 52);
		}

		@Override
		public byte[] sealObject() {
			byte[] send = new byte[292];
			jointByte = 0;
			shiftByte(send, userID);
			shiftByte(send, byteCoding(userAcoount));
			shiftByte(send, byteCoding(userNick));
			shiftByte(send, byteCoding(userPassword));
			shiftByte(send, byteCoding(userCode));
			shiftByte(send, userIconID);
			shiftByte(send, userType);
			shiftByte(send, userState);
			shiftByte(send, userClassID);
			shiftByte(send, byteCoding(userClassName));
			shiftByte(send, byteCoding(schoolName));
			return send;
		}

		@Override
		public void disassemblObject(byte[] datas, UdpUserDataBean t) {
			t.userID = getByte(datas, 0);
			t.userAcoount = byteCoding(getValidByte(datas, 4, 52));
			t.userNick = byteCoding(getValidByte(datas, 56, 32));
			t.userPassword = byteCoding(getValidByte(datas, 88, 52));
			t.userCode = byteCoding(getValidByte(datas, 140, 32));
			t.userIconID = getByte(datas, 172);
			t.userType = getByte(datas, 176);
			t.userState = getByte(datas, 180);
			t.userClassID = getByte(datas, 184);
			t.userClassName = byteCoding(getValidByte(datas, 188, 52));
			t.schoolName = byteCoding(getValidByte(datas, 240, 52));
		}

		@Override
		public String toString() {
			return "UdpUserDataBean [userID=" + Arrays.toString(userID) + ", userAcoount="
					+ Arrays.toString(userAcoount) + ", userPassword=" + Arrays.toString(userPassword) + ", userNick="
					+ Arrays.toString(userNick) + ", userCode=" + Arrays.toString(userCode) + ", userIconID="
					+ Arrays.toString(userIconID) + ", userType=" + Arrays.toString(userType) + ", userState="
					+ Arrays.toString(userState) + ", userClassID=" + Arrays.toString(userClassID) + ", userClassName="
					+ Arrays.toString(userClassName) + ", schoolName=" + Arrays.toString(schoolName) + "]";
		}

	}

	/** UDP文件的数据块 */
	public static class UdpFileDataBean extends ObjectUtils<UdpFileDataBean> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		private byte[] fileSaveID = new byte[4];// 文件存储 ID
		private byte[] fileLenght = new byte[4];// 文件长度
		private byte[] fileName = new byte[72];// 文件名
		private byte[] fileCreanData = new byte[32];// 文件创建时间/修改时间

		public int getFileSaveID() {
			return bytesToIntLow(fileSaveID);
		}

		public void setFileSaveID(int fileSaveID) {
			this.fileSaveID = intToByteLow(fileSaveID);
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
			this.fileName = joStringByte(fileName, 72);
		}

		public String getFileCreanData() {
			return new String(fileCreanData).trim();
		}

		public void setFileCreanData(String fileCreanData) {
			this.fileCreanData = joStringByte(fileCreanData, 32);
		}

		@Override
		public byte[] sealObject() {
			byte[] send = new byte[112];
			jointByte = 0;
			shiftByte(send, fileSaveID);
			shiftByte(send, fileLenght);
			shiftByte(send, byteCoding(fileName));
			shiftByte(send, byteCoding(fileCreanData));
			return send;
		}

		@Override
		public void disassemblObject(byte[] datas, UdpFileDataBean t) {
			t.fileSaveID = getByte(datas, 0);
			t.fileLenght = getByte(datas, 4);
			t.fileName = getValidByte(datas, 8, 72);
			t.fileCreanData = getValidByte(datas, 80, 32);

		}

		@Override
		public String toString() {
			return "UdpFileDataBean [fileSaveID=" + Arrays.toString(fileSaveID) + ", fileLenght="
					+ Arrays.toString(fileLenght) + ", fileName=" + Arrays.toString(fileName) + ", fileCreanData="
					+ Arrays.toString(fileCreanData) + "]";
		}

	}
}
