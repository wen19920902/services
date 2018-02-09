package sqls.utils;

import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/***
 * C3P的数据库连接池操作
 * 
 * @author wan
 *
 */
public class C3PConnect {

	/***
	 * 
	 * @return 获取数据库的连接
	 * @throws SQLException
	 *             获取异常
	 */
	public static Connection connect() throws SQLException {
		return Budile.ds.getConnection();
	}

	private static final class Budile {

		// static final String DRIVERNAME = "com.mysql.jdbc.Driver";// 数据库连接驱动包名
		// static final String SQLIP = "192.168.18.70";// 数据库连接地址 211.159.185.52
		// static final int SQLPOST = 3306;// 数据库连接端口
		// static final String SQLNAME = "fangcheng";// 数据库名//fangcheng
		// static final String account = "root";// 数据库连接用户名
		// static final String password = "root";// 数据库连接密码

		static ComboPooledDataSource ds = new ComboPooledDataSource("mysql");

		// static ComboPooledDataSource getcs() {
		// ComboPooledDataSource ds = null;
		// try {
		// ds = new ComboPooledDataSource();
		// ds.setDriverClass(DRIVERNAME);
		// ds.setJdbcUrl("jdbc:mysql://" + SQLIP + ":" + SQLPOST + "/" + SQLNAME +
		// "?useSSL=true");// 版本兼容
		// ds.setUser(account);
		// ds.setPassword(password);
		// ds.setMaxPoolSize(100);// 连接池中保留的最大连接数
		// ds.setMinPoolSize(10);// 连接池中保留的最小连接数
		// ds.setAcquireIncrement(10);// 一次性创建新连接的数目
		// ds.setInitialPoolSize(10);// 初始创建
		// ds.setMaxIdleTime(6000);// 最大空闲时间
		// ds.setMaxStatements(0);// 预缓存数量
		// /**
		// * 获取连接失败将会引起所有等待连接池来获取连接的线程抛出异常。但是数据源仍有效
		// * 保留，并在下次调用getConnection()的时候继续尝试获取连接。如果设为true，那么在尝试 获取连接失败后该数据源将申明已断开并永久关闭
		// */
		// ds.setBreakAfterAcquireFailure(true);
		// } catch (PropertyVetoException e) {
		// e.printStackTrace();
		// }
		// return ds;
		// }
	}

}
