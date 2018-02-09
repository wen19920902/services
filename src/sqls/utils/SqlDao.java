package sqls.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import logs.LogUtil;

/***
 * ���ݲ���Dao��
 * 
 * @author wan
 *
 */
public class SqlDao {

	/***
	 * 
	 * @Description: �������ݿⷵ��һ���������
	 * @param sql
	 *            �������ݿ��Sql���
	 * @param parms
	 *            ��������
	 * @return ResultSet �����Ľ��
	 */
	protected ResultSet traverse(String sql, Object... params) {
		ResultSet rs = null;
		try {
			PreparedStatement ps = C3PConnect.connect().prepareStatement(sql);// ����������
			if (params != null) {
				for (int i = 0; i < params.length; i++) {// ����ռλ����ֵ
					ps.setObject((i + 1), params[i]);
				}
			}
			rs = ps.executeQuery();// �����
		} catch (SQLException e) {
			LogUtil.pick(2).e(e);
			e.printStackTrace();
		}
		return rs;
	}

	/***
	 * 
	 * @Description:�����ݿ�� �� ɾ �� �����ķ���
	 * @param sql
	 *            ���ݿ��Sql���
	 * @param params
	 *            ����
	 * @return int ִ�н����ʶ
	 */
	protected int update(String sql, Object... params) {
		Connection conn = null;
		PreparedStatement ps = null;// ������
		int effectedNum = -1;
		try {
			conn = C3PConnect.connect();
			ps = conn.prepareStatement(sql);
			/** ����ռλ����ֵ */
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					ps.setObject((i + 1), params[i]);
				}
			}
			/** ִ�и��� */
			effectedNum = ps.executeUpdate();
		} catch (SQLException e) {
			LogUtil.pick(2).e(e);
			e.printStackTrace();
		} finally {
			closeState(ps);
		}
		return effectedNum;
	}

	/**
	 * 
	 * @Description:һϵ������Ĵ���
	 * @param sqls
	 *            ���ݿ��Sql��伯��
	 * @param params
	 *            ���� ����
	 * @return int ���
	 */
	protected int doTransaction(List<String> sqls, List<Object[]> params) {
		Connection conn = null;
		try {
			conn = C3PConnect.connect();
			// ��ʼ����
			conn.setAutoCommit(false);

			int size = sqls.size();
			for (int i = 0; i < size; i++) {
				PreparedStatement statement = conn.prepareStatement(sqls.get(i));
				Object[] values = params.get(i);
				if (values != null) {
					int length = values.length;
					for (int j = 0; j < length; j++) {
						statement.setObject((j + 1), values[j]);
					}
				}
				statement.execute();
				statement.close();
			}
			/** �ύ���� */
			conn.commit();
			if (null != conn)
				conn.close();
			return 1;
		} catch (Exception e) {
			LogUtil.pick(2).e(e);
			e.printStackTrace();
		} finally {
			if (null != conn)
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return -1;
	}

	/***
	 * 
	 * @param rs
	 *            �رս����
	 */
	protected void closeResult(ResultSet rs) {
		try {
			if (rs != null) {
				PreparedStatement ps = (PreparedStatement) rs.getStatement();
				Connection conn = ps.getConnection();
				rs.close();
				if (null != ps)
					ps.close();
				if (null != conn)
					conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/***
	 * 
	 * @param state
	 *            �ر�Ԥ������������
	 */
	protected void closeState(PreparedStatement state) {
		try {
			if (null != state) {
				Connection connection = state.getConnection();
				state.close();
				if (null != connection)
					connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/***
	 * 
	 * @param conn
	 *            �ر����ݿ����Ӷ���
	 */
	protected void closeConn(Connection conn) {
		try {
			if (null != conn)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/***
	 * 
	 * @param rs
	 *            ��ȡĿ��
	 * @return ��ȡResultset ���ݿ��������� ����
	 * @throws SQLException
	 */
	protected List<Map<String, Object>> convertList(ResultSet rs) throws SQLException {
		List<Map<String, Object>> list = new ArrayList<>();
		ResultSetMetaData md = rs.getMetaData();// ��ȡ����
		int columnCount = md.getColumnCount();// ��ȡ�е�����
		while (rs.next()) {
			Map<String, Object> rowData = new HashMap<>();// ����Map
			for (int i = 1; i <= columnCount; i++) {
				rowData.put(md.getColumnName(i), rs.getObject(i));// ��ȡ������ֵ
			}
			list.add(rowData);
		}
		return list;
	}

	/***
	 * 
	 * @param strTime
	 *            ת��Ŀ��
	 * @param type
	 *            ת������
	 * @return �ַ���תʱ���ʽ
	 */
	protected int stringTime(String strTime, int type) {
		String[] p = null;
		if (type == 0) {
			p = strTime.split("-");
		} else
			p = strTime.split(":");

		return Integer.valueOf(p[0]) * 10000 + Integer.valueOf(p[1]) * 100 + Integer.valueOf(p[2]);
	}

}
