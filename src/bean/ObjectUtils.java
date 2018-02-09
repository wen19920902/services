package bean;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/***
 * ���๤��
 * 
 * @author wan
 *
 * @param <T>
 */
public abstract class ObjectUtils<T> implements Serializable {

	/**
	 * ϵ�к�
	 */
	private static final long serialVersionUID = 1L;

	protected int jointByte;// ƴ���ֽ���������λ��

	protected static final String CODING = "GB2312";// ͳһ����

	/**
	 * ��һ���ֽ�����ת�Ƶ���һ���ֽ�������
	 *
	 * @param src
	 *            ת�ƽ���ֽ�����
	 * @param goal
	 *            ת��Ŀ���ֽ�����
	 */
	protected void shiftByte(byte[] src, byte[] goal) {
		if (goal.length != 0) {
			System.arraycopy(goal, 0, src, jointByte, goal.length);
			jointByte += goal.length;
		}
	}

	/**
	 * ��int��ֵת��Ϊռ�ĸ��ֽڵ�byte���飬������������(��λ��ǰ����λ�ں�)��˳�� ��bytesToIntLow��������ʹ��
	 *
	 * @param value
	 *            Ҫת����intֵ
	 * @return byte����
	 */
	protected byte[] intToByteLow(int value) {
		byte[] src = new byte[4];
		src[3] = (byte) ((value >> 24) & 0xFF);
		src[2] = (byte) ((value >> 16) & 0xFF);
		src[1] = (byte) ((value >> 8) & 0xFF);
		src[0] = (byte) (value & 0xFF);
		return src;
	}

	/**
	 * byte������ȡint��ֵ��������������(��λ��ǰ����λ�ں�)��˳�򣬺ͺ�intToByteLow��������ʹ��
	 *
	 * @param src
	 *            byte����
	 * @return int��ֵ
	 */
	protected int bytesToIntLow(byte[] src) {
		// ������ĵ�offsetλ��ʼ
		int offset = 0;
		int value;
		value = ((src[offset] & 0xFF) | ((src[offset + 1] & 0xFF) << 8) | ((src[offset + 2] & 0xFF) << 16)
				| ((src[offset + 3] & 0xFF) << 24));
		return value;
	}

	/**
	 * ��λ�úͳ��Ƚ�ȡ��Ч����
	 *
	 * @param d
	 *            ���������
	 * @param num
	 *            ��ȡ��λ����ʼ��
	 * @param dataSize
	 *            ��ȡ�ĳ���
	 * @return ��ȡ��λ������
	 */
	protected byte[] getValidByte(byte[] d, int num, int dataSize) {
		byte[] b = null;
		if (d.length - num >= dataSize) {
			b = new byte[dataSize];
		} else
			b = new byte[d.length - num];
		System.arraycopy(d, num, b, 0, b.length);
		return b;
	}

	/**
	 * ��λ�ý�ȡ����
	 *
	 * @param d
	 *            ���������
	 * @param num
	 *            ��ȡ��λ����ʼ��
	 * @return ��ȡ��λ������
	 */
	protected byte[] getByte(byte[] d, int num) {
		return getValidByte(d, num, 4);
	}

	/**
	 * ��Ŀ���ַ���תΪ���ô�С���ֽ�����
	 *
	 * @param src
	 *            Ŀ���ַ���
	 * @param size
	 *            ����Ŀ�곤��
	 * @return ת��������
	 */
	protected byte[] joStringByte(String src, int size) {
		// Log.e("===JointByte", "����: " + src.length() + "==" + src.getBytes().length +
		// "===" + src);
		byte[] goal = new byte[size];
		byte[] use = src.getBytes();
		if (size < use.length)
			System.arraycopy(use, 0, goal, 0, size);
		else
			System.arraycopy(use, 0, goal, 0, use.length);
		return goal;
	}

	/***
	 * 
	 * @param src
	 *            ƴλ��Ŀ��
	 * @param size
	 *            ƴλ�Ĵ�С
	 * @return �ֽ������ƴλ
	 */
	protected byte[] joByteByte(byte[] src, int size) {
		byte[] goal = new byte[size];
		System.arraycopy(src, 0, goal, 0, src.length);
		return goal;
	}

	/***
	 * 
	 * @param src
	 *            ����Ŀ��
	 * @return �ֽ��������
	 */
	protected byte[] byteCoding(byte[] src) {
		try {
			return new String(src).getBytes(CODING);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return src;
	}

	/**
	 * ��һ��IP��ַ������һ������
	 *
	 * @param ip
	 *            ����Ŀ��
	 * @return �������
	 */
	protected int resoleIPString(String ip) {
		if (ip == null)
			return 0;
		String[] temp = ip.split("\\.");
		int i = Integer.parseInt(temp[0]);
		int i1 = (Integer.parseInt(temp[1]) * 256);
		int i2 = (Integer.parseInt(temp[2]) * 256 * 256);
		int i3 = (Integer.parseInt(temp[3]) * 256 * 256 * 256);
		return i + i1 + i2 + i3;
	}

	/**
	 * �ϲ��ֽ������IP��ַ
	 *
	 * @param b
	 *            �ϲ� Ŀ��
	 * @return IP��ַ
	 */
	protected String resoleIpInt(byte[] b) {
		StringBuffer sb = new StringBuffer();
		// Log.e(TAG, "aqbsfsdfsfsf: " + bytesToIntLow(b));
		sb.append(toD(Integer.toBinaryString((b[0] & 0xFF) + 0x100).substring(1), 2));
		sb.append(".");
		sb.append(toD(Integer.toBinaryString((b[1] & 0xFF) + 0x100).substring(1), 2));
		sb.append(".");
		sb.append(toD(Integer.toBinaryString((b[2] & 0xFF) + 0x100).substring(1), 2));
		sb.append(".");
		sb.append(toD(Integer.toBinaryString((b[3] & 0xFF) + 0x100).substring(1), 2));
		return sb.toString();
	}

	/**
	 * ���������תΪʮ������
	 *
	 * @param a
	 *            ת����Ŀ��
	 * @param b
	 *            ת���Ľ���
	 * @return ʮ����
	 */
	private String toD(String a, int b) {
		int r = 0;
		for (int i = 0; i < a.length(); i++) {
			r = (int) (r + formatting(a.substring(i, i + 1)) * Math.pow(b, a.length() - i - 1));
		}
		return String.valueOf(r);
	}

	/**
	 * ��ʮ�������е���ĸתΪ��Ӧ������
	 *
	 * @param a
	 *            ʮ������
	 * @return ת��ʮ������
	 */
	private int formatting(String a) {
		int i = 0;
		for (int u = 0; u < 10; u++)
			if (a.equals(String.valueOf(u)))
				i = u;
		if (a.equals("a"))
			i = 10;
		if (a.equals("b"))
			i = 11;
		if (a.equals("c"))
			i = 12;
		if (a.equals("d"))
			i = 13;
		if (a.equals("e"))
			i = 14;
		if (a.equals("f"))
			i = 15;
		return i;
	}

	/***
	 * 
	 * @return ���ص�ǰ����
	 */
	public byte[] getNowDate() {
		Calendar c = new GregorianCalendar();
		return intToByteLow(
				c.get(Calendar.YEAR) * 10000 + (c.get(Calendar.MONTH) + 1) * 100 + c.get(Calendar.DAY_OF_MONTH));
	}

	/***
	 * 
	 * @param times
	 *            �趨��Ŀ��
	 * @return �����趨������
	 */
	public byte[] getSettingDate(Integer times) {
		return intToByteLow(times.intValue());
	}

	/***
	 * 
	 * @return ��ȡ��ǰʱ��
	 */
	public byte[] getNowTime() {
		Calendar c = new GregorianCalendar();
		return intToByteLow(
				c.get(Calendar.HOUR_OF_DAY) * 10000 + c.get(Calendar.MINUTE) * 100 + c.get(Calendar.SECOND));
	}

	/***
	 * 
	 * @param times
	 *            �趨Ŀ��
	 * @return ��ȡ�趨��ʱ��
	 */
	public byte[] getSettingTime(Integer times) {
		return intToByteLow(times);
	}

	/**
	 * �����ֽ������ʱ���ʽ
	 *
	 * @param date
	 *            ����Ŀ��
	 * @param dataType
	 *            ��ȡ��ʱ������ (0Ϊ���� 1Ϊʱ��)
	 * @return ʱ���ַ���
	 */
	public String parseDate(byte[] date, int dataType) {
		int src = bytesToIntLow(date);
		int one = (src / 10000);
		int two = ((src % 10000) / 100);
		int three = ((src % 10000) % 100);
		if (dataType == 0)
			return String.format("%s-%s-%s", one < 10 ? String.format("%02d", one) : one + "",
					two < 10 ? String.format("%02d", two) : two + "",
					three < 10 ? String.format("%02d", three) : three + "");
		else
			return String.format("%s:%s:%s", one < 10 ? String.format("%02d", one) : one + "",
					two < 10 ? String.format("%02d", two) : two + "",
					three < 10 ? String.format("%02d", three) : three + "");
	}

	/***
	 * 
	 * @return ��ȡ��ǰʱ���ʱ���ַ���
	 */
	public String timeString() {
		Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date());
	}

	/***
	 * 
	 * @param strTime
	 *            ת��Ŀ��
	 * @param type
	 *            ת������
	 * @return �ַ���תʱ���ʽ
	 */
	public int stringTime(String strTime, int type) {
		String[] p = null;
		if (type == 0) {
			p = strTime.split("-");
		} else
			p = strTime.split(":");

		return Integer.valueOf(p[0]) * 10000 + Integer.valueOf(p[1]) * 100 + Integer.valueOf(p[2]);
	}

	/**
	 * ��������ת�ֽ�����
	 */
	public abstract byte[] sealObject();

	/**
	 * ���ֽ���������ɶ���
	 *
	 * @param datas
	 *            Ҫ���������ֽ�����
	 * @param t
	 *            ������Ŀ�����
	 */
	public abstract void disassemblObject(byte[] datas, T t);

}
