package sqls.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import bean.udpbean.UDPRequestBean;
import bean.udpbean.UDPRequestBean.UdpFileDataBean;
import sqls.dao.FileInterface;
import sqls.utils.SqlDao;

/***
 * 文件业务 实现
 * 
 * @author wan
 *
 */
public class FileImpl extends SqlDao implements FileInterface {

	private FileImpl() {
	}

	public static FileImpl creanFileImpl() {
		return Budile.fi;
	}

	private static class Budile {
		static FileImpl fi = new FileImpl();
	}

	@Override
	public int insertFile(UDPRequestBean ub, String path) {
		UdpFileDataBean uf = new UdpFileDataBean();
		uf.disassemblObject(ub.getData(), uf);
		String sql = "insert into tbl_file values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] params = { 0, uf.getFileName(), uf.getFileCreanData(), ub.getDate() + " " + ub.getTime(), path,
				ub.getUserID(), UserProfessinalImpl.creanUserImpl().selectUserClassID(ub.getUserID()), ub.getTargetID(),
				ub.getWordType(), uf.getFileLenght(), 0, 0, 0 };
		return update(sql, params);
	}

	@Override
	public String selectFilePath(int fileSaveID) {
		String path = null;
		String sql = "select file_url from tbl_file where id=?";
		Object[] params = { fileSaveID };
		ResultSet rSet = traverse(sql, params);
		try {
			while (null != rSet && rSet.next()) {
				path = rSet.getString("file_url");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResult(rSet);
		}
		return path != null ? path : null;
	}

	@Override
	public int selectFileUploadLenght(int fileSaveID) {
		int lenght = -1;
		String sql = "select upload_len from tbl_file where id=?";
		Object[] params = { fileSaveID };
		ResultSet rSet = traverse(sql, params);
		try {
			while (null != rSet && rSet.next()) {
				lenght = rSet.getInt("upload_len");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResult(rSet);
		}
		return lenght != -1 ? lenght : 0;
	}

	@Override
	public int selectFileSerial(int fileSaveID) {
		int serial = -1;
		String sql = "select rate from tbl_file where id=?";
		Object[] params = { fileSaveID };
		ResultSet rSet = traverse(sql, params);
		try {
			while (null != rSet && rSet.next()) {
				serial = rSet.getInt("rate");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResult(rSet);
		}
		return serial != -1 ? serial : 0;
	}

	@Override
	public int selectFileLenght(int fileSaveID) {
		int fileLenght = -1;
		String sql = "select file_len from tbl_file where id=?";
		Object[] params = { fileSaveID };
		ResultSet rSet = traverse(sql, params);
		try {
			while (null != rSet && rSet.next()) {
				fileLenght = rSet.getInt("file_len");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			closeResult(rSet);
		}
		return fileLenght != -1 ? fileLenght : 0;
	}

	@Override
	public boolean updateFileStatus(int fileSaveID, int states) {
		String sql = "update tbl_file set status=? where id=?";
		Object[] params = { states, fileSaveID };
		return update(sql, params) != -1;
	}

	@Override
	public boolean updateFileUploadLenght(int fileSaveID, int len) {
		String sql = "update tbl_file set upload_len=? where id=?";
		Object[] params = { len, fileSaveID };
		return update(sql, params) != -1;
	}

	@Override
	public boolean updateFileSerial(int fileSaveID, int serial) {
		String sql = "update tbl_file set rate=? where id=?";
		Object[] params = { serial, fileSaveID };
		return update(sql, params) != -1;
	}

}
