package sqls.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.udpbean.UDPRequestBean;
import bean.udpbean.UDPRequestBean.UdpUserDataBean;
import logs.LogUtil;
import sqls.bean.KeyValuesBean;
import sqls.dao.UserProfessionalInterface;
import sqls.utils.SqlDao;

/***
 * 用户业务实现
 * 
 * @author wan
 *
 */
public class UserProfessinalImpl extends SqlDao implements UserProfessionalInterface {

	private UserProfessinalImpl() {
	}

	public static UserProfessinalImpl creanUserImpl() {
		return Budile.uf;
	}

	private static class Budile {
		static UserProfessinalImpl uf = new UserProfessinalImpl();
	}

	@Override
	public boolean loginCheck(String account, String password) {
		String sql = "select id from tbl_user where name=? and password=?";
		Object[] params = { account, password };
		ResultSet rSet = traverse(sql, params);
		boolean isOk = false;
		try {
			isOk = rSet.next();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResult(rSet);
		}
		return isOk;
	}

	@Override
	public synchronized boolean userError(UdpUserDataBean udpUserDataBean) {
		return false;
	}

	@Override
	public boolean updateState(UDPRequestBean udpBean, String ip, int post) {
		int result = -1;
		UdpUserDataBean ub = selectPerson(udpBean.getUserID());
		int userClassID = ub.getUserClassID();
		int userType = ub.getUserType();
		synchronized (udpBean.getUserID()) {
			String sql = "insert into  tbl_user_login value(?,?,?,?,?,?,?,?,?,?,?,?)";
			Object[] params = { 0, udpBean.getUserID(), userClassID, udpBean.getOrder() == 3 ? 1 : 0, ip, post,
					udpBean.getTargetIP(), ip, udpBean.getTargetPost(), udpBean.getDate() + " " + udpBean.getTime(),
					userType, udpBean.getRequstPlatForm() };
			result = update(sql, params);
		}
		return result != -1;
	}

	@Override
	public String selectStateIp_Post(int userID) {
		String result = null;
		String sql = "select port,ip from tbl_user_login where user_id=? order by id desc limit 1";
		Object[] params = { userID };
		ResultSet rSet = traverse(sql, params);
		try {
			while (null != rSet && rSet.next()) {
				result = KeyValuesBean.key_values(rSet.getInt("port"), rSet.getString("ip"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResult(rSet);
		}
		return result;
	}

	@Override
	public UdpUserDataBean selectPerson(int userId) {
		UdpUserDataBean ub = new UdpUserDataBean();
		String sql = "select * from tbl_user where id=?";
		Object[] params = { userId };
		ResultSet rSet = traverse(sql, params);
		try {
			while (null != rSet && rSet.next()) {
				ub.setUserID(rSet.getInt("id"));
				ub.setUserClassID(rSet.getInt("class_id"));
				ub.setUserAcoount(rSet.getString("name"));
				ub.setUserNick(rSet.getString("nickname"));
				ub.setUserPassword(rSet.getString("password"));
				ub.setUserType(rSet.getInt("t_type"));
				ub.setUserCode(rSet.getString("code"));
				ub.setUserType(rSet.getInt("status"));
				ub.setUserClassName(UserClassImpl.creanUserClassImpl().selectClassName(ub.getUserClassID()));
				ub.setSchoolName(UserClassImpl.creanUserClassImpl().selectSchoolName(ub.getUserClassID()));
			}
		} catch (SQLException e) {
			LogUtil.pick(2).e(e.getMessage());
			// e.printStackTrace();
			return null;
		} finally {
			closeResult(rSet);
		}
		return ub;
	}

	@Override
	public List<UdpUserDataBean> selectClassData(int classID) {
		List<UdpUserDataBean> list = new ArrayList<>();
		UdpUserDataBean ub = null;
		String sql = "select * from tbl_user where class_id=?";
		Object[] params = { classID };
		ResultSet rSet = traverse(sql, params);
		try {
			while (null != rSet && rSet.next()) {
				ub = new UdpUserDataBean();
				ub.setUserID(rSet.getInt("id"));
				ub.setUserClassID(rSet.getInt("class_id"));
				ub.setUserAcoount(rSet.getString("name"));
				ub.setUserNick(rSet.getString("nickname"));
				ub.setUserPassword(rSet.getString("password"));
				ub.setUserType(rSet.getInt("t_type"));
				ub.setUserCode(rSet.getString("code"));
				ub.setUserType(rSet.getInt("status"));
				ub.setUserClassName(UserClassImpl.creanUserClassImpl().selectClassName(ub.getUserClassID()));
				ub.setSchoolName(UserClassImpl.creanUserClassImpl().selectSchoolName(ub.getUserClassID()));
				list.add(ub);
			}
		} catch (SQLException e) {
			LogUtil.pick(2).e(e.getMessage());
			// e.printStackTrace();
			return null;
		} finally {
			closeResult(rSet);
		}
		return list;
	}

	@Override
	public int selectUserClassID(int userID) {
		int classID = -1;
		String sql = "select class_id from tbl_user where id=?";
		Object[] params = { userID };
		ResultSet rSet = traverse(sql, params);
		try {
			while (null != rSet && rSet.next()) {
				classID = rSet.getInt("class_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResult(rSet);
		}
		return classID != -1 ? classID : 0;
	}

}
