package bean;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/***
 * 父类工具
 * 
 * @author wan
 *
 * @param <T>
 */
public abstract class ObjectUtils<T> implements Serializable {

	/**
	 * 系列号
	 */
	private static final long serialVersionUID = 1L;

	protected int jointByte;// 拼接字节数组对象的位置

	protected static final String CODING = "GB2312";// 统一编码

	/**
	 * 将一个字节数组转移到另一个字节数组中
	 *
	 * @param src
	 *            转移结果字节数组
	 * @param goal
	 *            转移目标字节数组
	 */
	protected void shiftByte(byte[] src, byte[] goal) {
		if (goal.length != 0) {
			System.arraycopy(goal, 0, src, jointByte, goal.length);
			jointByte += goal.length;
		}
	}

	/**
	 * 将int数值转换为占四个字节的byte数组，本方法适用于(低位在前，高位在后)的顺序。 和bytesToIntLow（）配套使用
	 *
	 * @param value
	 *            要转换的int值
	 * @return byte数组
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
	 * byte数组中取int数值，本方法适用于(低位在前，高位在后)的顺序，和和intToByteLow（）配套使用
	 *
	 * @param src
	 *            byte数组
	 * @return int数值
	 */
	protected int bytesToIntLow(byte[] src) {
		// 从数组的第offset位开始
		int offset = 0;
		int value;
		value = ((src[offset] & 0xFF) | ((src[offset + 1] & 0xFF) << 8) | ((src[offset + 2] & 0xFF) << 16)
				| ((src[offset + 3] & 0xFF) << 24));
		return value;
	}

	/**
	 * 按位置和长度截取有效数据
	 *
	 * @param d
	 *            处理的数据
	 * @param num
	 *            截取的位置起始点
	 * @param dataSize
	 *            截取的长度
	 * @return 截取的位置数据
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
	 * 按位置截取数据
	 *
	 * @param d
	 *            处理的数据
	 * @param num
	 *            截取的位置起始点
	 * @return 截取的位置数据
	 */
	protected byte[] getByte(byte[] d, int num) {
		return getValidByte(d, num, 4);
	}

	/**
	 * 将目标字符串转为设置大小的字节数组
	 *
	 * @param src
	 *            目标字符串
	 * @param size
	 *            设置目标长度
	 * @return 转换后数组
	 */
	protected byte[] joStringByte(String src, int size) {
		// Log.e("===JointByte", "长度: " + src.length() + "==" + src.getBytes().length +
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
	 *            拼位的目标
	 * @param size
	 *            拼位的大小
	 * @return 字节数组的拼位
	 */
	protected byte[] joByteByte(byte[] src, int size) {
		byte[] goal = new byte[size];
		System.arraycopy(src, 0, goal, 0, src.length);
		return goal;
	}

	/***
	 * 
	 * @param src
	 *            编码目标
	 * @return 字节数组编码
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
	 * 将一个IP地址解析成一个整形
	 *
	 * @param ip
	 *            解析目标
	 * @return 解析结果
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
	 * 合并字节数组成IP地址
	 *
	 * @param b
	 *            合并 目标
	 * @return IP地址
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
	 * 任意进制数转为十进制数
	 *
	 * @param a
	 *            转换的目标
	 * @param b
	 *            转换的进制
	 * @return 十进制
	 */
	private String toD(String a, int b) {
		int r = 0;
		for (int i = 0; i < a.length(); i++) {
			r = (int) (r + formatting(a.substring(i, i + 1)) * Math.pow(b, a.length() - i - 1));
		}
		return String.valueOf(r);
	}

	/**
	 * 将十六进制中的字母转为对应的数字
	 *
	 * @param a
	 *            十六进制
	 * @return 转换十进制数
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
	 * @return 返回当前日期
	 */
	public byte[] getNowDate() {
		Calendar c = new GregorianCalendar();
		return intToByteLow(
				c.get(Calendar.YEAR) * 10000 + (c.get(Calendar.MONTH) + 1) * 100 + c.get(Calendar.DAY_OF_MONTH));
	}

	/***
	 * 
	 * @param times
	 *            设定的目标
	 * @return 返回设定的日期
	 */
	public byte[] getSettingDate(Integer times) {
		return intToByteLow(times.intValue());
	}

	/***
	 * 
	 * @return 获取当前时间
	 */
	public byte[] getNowTime() {
		Calendar c = new GregorianCalendar();
		return intToByteLow(
				c.get(Calendar.HOUR_OF_DAY) * 10000 + c.get(Calendar.MINUTE) * 100 + c.get(Calendar.SECOND));
	}

	/***
	 * 
	 * @param times
	 *            设定目标
	 * @return 获取设定的时间
	 */
	public byte[] getSettingTime(Integer times) {
		return intToByteLow(times);
	}

	/**
	 * 解析字节数组成时间格式
	 *
	 * @param date
	 *            解析目标
	 * @param dataType
	 *            获取的时间类型 (0为日期 1为时间)
	 * @return 时间字符串
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
	 * @return 获取当前时间的时间字符串
	 */
	public String timeString() {
		Format format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date());
	}

	/***
	 * 
	 * @param strTime
	 *            转换目标
	 * @param type
	 *            转换类型
	 * @return 字符串转时间格式
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
	 * 解析对象转字节数组
	 */
	public abstract byte[] sealObject();

	/**
	 * 将字节数组解析成对象
	 *
	 * @param datas
	 *            要被操作的字节数组
	 * @param t
	 *            解析到目标对象
	 */
	public abstract void disassemblObject(byte[] datas, T t);

}
