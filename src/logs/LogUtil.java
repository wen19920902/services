package logs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/***
 * 
 * @ClassName: LogUtil
 * @Description: ��־��
 * @author wan
 * @date 2016-8-31 ����11:31:05
 */
public class LogUtil {

	/** ��־ǰ׺ */
	private static final String L = "---------";
	/** ��־���� */
	private static Log logs;

	/**
	 * 
	 * @Title: pick
	 * @Description: ѡ��ƽ̨�����
	 * @param @param num �����־���� 1 SERVLET 2 MYSQL 3 CONSOLE 4 APPLICATION 5 ��ѡ����
	 * @return Log ����
	 */
	public static LogUtil pick(int num) {
		logs = LogFactory.getLog(getName(num));
		return new LogUtil();
	}

	/**
	 * 
	 * @Title: getName
	 * @Description: ѡȡ���ƽ̨
	 * @param @param num ƽ̨ѡ��
	 * @return String ƽ̨ ��
	 */
	private static String getName(int num) {
		switch (num) {
		case 1:
			return "SERVLET";
		case 2:
			return "MYSQL";
		case 3:
			return "SYSTEM_EXCEPTION";
		case 4:
			return "APPLICATION";
		default:
			return "��ѡ����!!!";
		}
	}

	public void i(Object message) {
		logs.info(L + message);
	}

	public void w(Object message) {
		logs.warn(L + message);
	}

	public void e(Object message) {
		logs.error(L + message);
	}

	public void f(Object message) {
		logs.fatal(L + message);
	}

	public void d(Object message) {
		logs.debug(L + message);
	}

}
