package sqls.impl;

import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.udpbean.UDPRequestBean;
import bean.udpbean.UDPRequestBean.UdpFileDataBean;
import sqls.dao.MessageInterface;
import sqls.utils.SqlDao;

/***
 * 消息业务 实现
 * 
 * @author wan
 *
 */
public class MessageImpl extends SqlDao implements MessageInterface {

	private MessageImpl() {
	}

	public static MessageImpl creanMessageImpl() {
		return Budile.mi;
	}

	private static class Budile {
		static MessageImpl mi = new MessageImpl();
	}

	@Override
	public boolean insertMessage_File(UDPRequestBean ub) {
		UdpFileDataBean uFileBean = new UdpFileDataBean();
		uFileBean.disassemblObject(ub.getData(), uFileBean);

		String sql = "insert into tbl_message value(?,?,?,?,?,?,?,?,?)";
		Object[] params = { 0, uFileBean.getFileName(), ub.getUserID(), ub.getDate() + " " + ub.getTime(),
				ub.getOrder() == 7 ? 1 : 0, ub.getTargetID(), uFileBean.getFileName(),
				UserProfessinalImpl.creanUserImpl().selectUserClassID(ub.getUserID()), uFileBean.getFileSaveID() };
		return update(sql, params) != -1;
	}

	@Override
	public boolean insertMessage_Text(UDPRequestBean ub) {
		String sql = "insert into tbl_message value(?,?,?,?,?,?,?,?,?)";
		Object[] params = { 0, new String(ub.getData()).trim(), ub.getUserID(), ub.getDate() + " " + ub.getTime(),
				ub.getOrder() == 5 ? 1 : 0, ub.getTargetID(), null,
				UserProfessinalImpl.creanUserImpl().selectUserClassID(ub.getUserID()), 0 };
		return update(sql, params) != -1;
	}

	@Override
	public List<UDPRequestBean> selectNoReadMessage(int userID) {
		List<UDPRequestBean> list = new ArrayList<>();
		UDPRequestBean uBean = null;
		UdpFileDataBean uFileDataBean = null;
		String sql = "select * from tbl_message where target_id=? and t_type=0";
		Object[] params = { userID };
		ResultSet rSet = traverse(sql, params);
		try {
			while (null != rSet && rSet.next()) {
				uBean = new UDPRequestBean();
				uFileDataBean = new UdpFileDataBean();
				uBean.setUserID(rSet.getInt("user_id"));
				uBean.setTargetID(rSet.getInt("target_id"));
				String[] d = rSet.getString("create_time").split(" ");
				uBean.setDate(stringTime(d[0], 0));
				uBean.setTime(stringTime(d[1], 1));
				uFileDataBean.setFileSaveID(rSet.getInt("file_id"));
				if (uFileDataBean.getFileSaveID() != 0) {
					uFileDataBean.setFileName(rSet.getString("pictures"));
					uBean.setData(uFileDataBean.sealObject());
				} else
					uBean.setData(rSet.getString("content").getBytes("GB2312"));
				list.add(uBean);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			closeResult(rSet);
		}
		return list.size() > 0 ? list : null;
	}

	@Override
	public boolean updateMessageReadState(int userID) {
		String sql = "update tbl_message set t_type=1 where target_id=?";
		Object[] params = { userID };
		return update(sql, params) != -1;
	}

}
