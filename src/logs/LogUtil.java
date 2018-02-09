package logs;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/***
 * 
 * @ClassName: LogUtil
 * @Description: 日志类
 * @author wan
 * @date 2016-8-31 上午11:31:05
 */
public class LogUtil {

	/** 日志前缀 */
	private static final String L = "---------";
	/** 日志对象 */
	private static Log logs;

	/**
	 * 
	 * @Title: pick
	 * @Description: 选择平台的输出
	 * @param @param num 输出日志分类 1 SERVLET 2 MYSQL 3 CONSOLE 4 APPLICATION 5 你选错了
	 * @return Log 本类
	 */
	public static LogUtil pick(int num) {
		logs = LogFactory.getLog(getName(num));
		return new LogUtil();
	}

	/**
	 * 
	 * @Title: getName
	 * @Description: 选取输出平台
	 * @param @param num 平台选项
	 * @return String 平台 名
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
			return "你选错了!!!";
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
