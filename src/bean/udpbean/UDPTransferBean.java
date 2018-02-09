package bean.udpbean;

import bean.ObjectUtils;

/***
 * UDP��תʵ�����
 * 
 * @author wan
 *
 */
public class UDPTransferBean extends ObjectUtils<UDPTransferBean> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8953061129199499439L;

	private int id;// ���
	private byte[] userId;// �û�����Ψһ��ʶ
	private byte[] ip; // �û������ȡ IP
	private byte[] post;// �û������ȡ �˿ں�

	public UDPTransferBean() {
	}

	@Override
	public byte[] sealObject() {
		byte[] send = new byte[12];
		jointByte = 0;
		shiftByte(send, userId);
		shiftByte(send, ip);
		shiftByte(send, post);
		return send;
	}

	@Override
	public void disassemblObject(byte[] datas, UDPTransferBean t) {
		t.userId = getByte(datas, 0);
		t.ip = getByte(datas, 4);
		t.post = getByte(datas, 8);
	}

	public int getId() {
		return id;
	}

	public int getUserId() {
		return bytesToIntLow(userId);
	}

	public String getIp() {
		return resoleIpInt(ip);
	}

	public int getPost() {
		return bytesToIntLow(post);
	}

	public void setUserId(int userId) {
		this.userId = intToByteLow(userId);
	}

	public void setIp(String ip) {
		this.ip = intToByteLow(resoleIPString(ip));
	}

	public void setPost(int post) {
		this.post = intToByteLow(post);
	}

}
