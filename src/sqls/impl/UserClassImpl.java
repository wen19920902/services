package sqls.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sqls.bean.KeyValuesBean;
import sqls.dao.UserClassIntertface;
import sqls.utils.SqlDao;

/***
 * 户班级信息业务的实现
 * 
 * @author wan
 *
 */
public class UserClassImpl extends SqlDao implements UserClassIntertface {

	private UserClassImpl() {
	}

	public static UserClassImpl creanUserClassImpl() {
		return Budile.ui;
	}

	private static class Budile {
		static UserClassImpl ui = new UserClassImpl();
	}

	@Override
	public synchronized boolean insertSchool(String schoolName, String areaCode) {
		String sql = "insert into tbl_school values(?,?,?)";
		Object[] params = { 0, schoolName, areaCode };
		return update(sql, params) != -1;
	}

	@Override
	public synchronized boolean insertClass(int schoolID, String className) {
		String sql = "insert into tbl_class values(?,?,?)";
		Object[] params = { 0, className, schoolID };
		return update(sql, params) != -1;
	}

	@Override
	public synchronized boolean insertSubject(String subjectName) {
		String sql = "insert into tbl_subject values(?,?)";
		Object[] params = { 0, subjectName };
		return update(sql, params) != -1;
	}

	@Override
	public int selectClassID(String className) {
		int classID = -1;
		String sql = "select id from tbl_class where class_name=?";
		Object[] params = { className };
		ResultSet rSet = traverse(sql, params);
		try {
			while (null != rSet && rSet.next()) {
				classID = rSet.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResult(rSet);
		}
		return classID != -1 ? classID : 0;
	}

	@Override
	public String selectClassName(int classID) {
		String className = null;
		String sql = "select class_name from tbl_class where id=?";
		Object[] params = { classID };
		ResultSet rSet = traverse(sql, params);
		try {
			while (null != rSet && rSet.next()) {
				className = rSet.getString("class_name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResult(rSet);
		}
		return className != null ? className : null;
	}

	@Override
	public int selectSchoolId(int classID) {
		int schoolID = -1;
		String sql = "select id from tbl_school where school_name=?";
		Object[] params = { classID };
		ResultSet rSet = traverse(sql, params);
		try {
			while (null != rSet && rSet.next()) {
				schoolID = rSet.getInt("id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResult(rSet);
		}
		return schoolID != -1 ? schoolID : 0;
	}

	@Override
	public List<String> selectSchoolClass(int schoolID) {
		List<String> list = new ArrayList<>();
		String sql = "select * from tbl_class where school_id=?";
		Object[] params = { schoolID };
		ResultSet rSet = traverse(sql, params);
		try {
			while (null != rSet && rSet.next()) {
				list.add(KeyValuesBean.key_values(rSet.getInt("id"), rSet.getString("class_name")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResult(rSet);
		}
		return list.size() > 0 ? list : null;
	}

	@Override
	public Map<Integer, String> selectSubject() {
		Map<Integer, String> map = new HashMap<>();
		String sql = "select * from tbl_subject";
		ResultSet rSet = traverse(sql);
		try {
			while (null != rSet && rSet.next()) {
				map.put(rSet.getInt("id"), rSet.getString("subject"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResult(rSet);
		}
		return map.size() > 0 ? map : null;
	}

	@Override
	public int selectTeacherSubjectID(int teacherID, int classID) {
		int subjectID = -1;
		String sql = "select subject_id from tbl_teacher_subject where user_id=? and class_id=?";
		Object[] params = { teacherID, classID };
		ResultSet rSet = traverse(sql, params);
		try {
			while (null != rSet && rSet.next()) {
				subjectID = rSet.getInt("subject_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResult(rSet);
		}
		return subjectID != -1 ? subjectID : 0;
	}

	@Override
	public String selectSchoolName(int classID) {
		String schoolName = null;
		String sql = "select school_name from tbl_school where id=?";
		Object[] params = { selectClassSchoolID(classID) };
		ResultSet rSet = traverse(sql, params);
		try {
			while (null != rSet && rSet.next()) {
				schoolName = rSet.getString("school_name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResult(rSet);
		}
		return schoolName != null ? schoolName : null;
	}

	@Override
	public int selectClassSchoolID(int classID) {
		int schoolID = -1;
		String sql = "select school_id from tbl_class where id=?";
		Object[] params = { classID };
		ResultSet rSet = traverse(sql, params);
		try {
			while (null != rSet && rSet.next()) {
				schoolID = rSet.getInt("school_id");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResult(rSet);
		}
		return schoolID != -1 ? schoolID : 0;
	}

}
