package sqls.utils;

import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/***
 * C3P�����ݿ����ӳز���
 * 
 * @author wan
 *
 */
public class C3PConnect {

	/***
	 * 
	 * @return ��ȡ���ݿ������
	 * @throws SQLException
	 *             ��ȡ�쳣
	 */
	public static Connection connect() throws SQLException {
		return Budile.ds.getConnection();
	}

	private static final class Budile {

		// static final String DRIVERNAME = "com.mysql.jdbc.Driver";// ���ݿ�������������
		// static final String SQLIP = "192.168.18.70";// ���ݿ����ӵ�ַ 211.159.185.52
		// static final int SQLPOST = 3306;// ���ݿ����Ӷ˿�
		// static final String SQLNAME = "fangcheng";// ���ݿ���//fangcheng
		// static final String account = "root";// ���ݿ������û���
		// static final String password = "root";// ���ݿ���������

		static ComboPooledDataSource ds = new ComboPooledDataSource("mysql");

		// static ComboPooledDataSource getcs() {
		// ComboPooledDataSource ds = null;
		// try {
		// ds = new ComboPooledDataSource();
		// ds.setDriverClass(DRIVERNAME);
		// ds.setJdbcUrl("jdbc:mysql://" + SQLIP + ":" + SQLPOST + "/" + SQLNAME +
		// "?useSSL=true");// �汾����
		// ds.setUser(account);
		// ds.setPassword(password);
		// ds.setMaxPoolSize(100);// ���ӳ��б��������������
		// ds.setMinPoolSize(10);// ���ӳ��б�������С������
		// ds.setAcquireIncrement(10);// һ���Դ��������ӵ���Ŀ
		// ds.setInitialPoolSize(10);// ��ʼ����
		// ds.setMaxIdleTime(6000);// ������ʱ��
		// ds.setMaxStatements(0);// Ԥ��������
		// /**
		// * ��ȡ����ʧ�ܽ����������еȴ����ӳ�����ȡ���ӵ��߳��׳��쳣����������Դ����Ч
		// * �����������´ε���getConnection()��ʱ��������Ի�ȡ���ӡ������Ϊtrue����ô�ڳ��� ��ȡ����ʧ�ܺ������Դ�������ѶϿ������ùر�
		// */
		// ds.setBreakAfterAcquireFailure(true);
		// } catch (PropertyVetoException e) {
		// e.printStackTrace();
		// }
		// return ds;
		// }
	}

}
