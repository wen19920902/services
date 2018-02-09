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
 * 数据操作Dao层
 * 
 * @author wan
 *
 */
public class SqlDao {

	/***
	 * 
	 * @Description: 遍历数据库返回一个遍历结果
	 * @param sql
	 *            遍历数据库的Sql语句
	 * @param parms
	 *            遍历条件
	 * @return ResultSet 遍历的结果
	 */
	protected ResultSet traverse(String sql, Object... params) {
		ResultSet rs = null;
		try {
			PreparedStatement ps = C3PConnect.connect().prepareStatement(sql);// 生成描述器
			if (params != null) {
				for (int i = 0; i < params.length; i++) {// 设置占位符的值
					ps.setObject((i + 1), params[i]);
				}
			}
			rs = ps.executeQuery();// 结果集
		} catch (SQLException e) {
			LogUtil.pick(2).e(e);
			e.printStackTrace();
		}
		return rs;
	}

	/***
	 * 
	 * @Description:对数据库的 增 删 改 操作的方法
	 * @param sql
	 *            数据库的Sql语句
	 * @param params
	 *            条件
	 * @return int 执行结果标识
	 */
	protected int update(String sql, Object... params) {
		Connection conn = null;
		PreparedStatement ps = null;// 描述器
		int effectedNum = -1;
		try {
			conn = C3PConnect.connect();
			ps = conn.prepareStatement(sql);
			/** 设置占位符的值 */
			if (params != null) {
				for (int i = 0; i < params.length; i++) {
					ps.setObject((i + 1), params[i]);
				}
			}
			/** 执行更新 */
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
	 * @Description:一系列事务的处理
	 * @param sqls
	 *            数据库的Sql语句集合
	 * @param params
	 *            条件 集合
	 * @return int 结果
	 */
	protected int doTransaction(List<String> sqls, List<Object[]> params) {
		Connection conn = null;
		try {
			conn = C3PConnect.connect();
			// 开始事物
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
			/** 提交事务 */
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
	 *            关闭结果集
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
	 *            关闭预编译描述对象
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
	 *            关闭数据库链接对象
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
	 *            读取目标
	 * @return 读取Resultset 数据库结果集返回 集合
	 * @throws SQLException
	 */
	protected List<Map<String, Object>> convertList(ResultSet rs) throws SQLException {
		List<Map<String, Object>> list = new ArrayList<>();
		ResultSetMetaData md = rs.getMetaData();// 获取键名
		int columnCount = md.getColumnCount();// 获取行的数量
		while (rs.next()) {
			Map<String, Object> rowData = new HashMap<>();// 声明Map
			for (int i = 1; i <= columnCount; i++) {
				rowData.put(md.getColumnName(i), rs.getObject(i));// 获取键名及值
			}
			list.add(rowData);
		}
		return list;
	}

	/***
	 * 
	 * @param strTime
	 *            转换目标
	 * @param type
	 *            转换类型
	 * @return 字符串转时间格式
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
