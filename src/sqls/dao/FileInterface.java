package sqls.dao;

import bean.udpbean.UDPRequestBean;

/***
 * 文件操作的业务
 * 
 * @author wan
 *
 */
public interface FileInterface {

	/***
	 * 
	 * @param ub
	 *            存储目标
	 * @param path
	 *            存储文件路径
	 * @return 存储文件记录ID
	 */
	int insertFile(UDPRequestBean ub, String path);

	/***
	 * 
	 * @param fileSaveID
	 *            查询目标ID
	 * @return 查询文件存储目录
	 */
	String selectFilePath(int fileSaveID);

	/***
	 * 
	 * @param fileSaveID
	 *            查询目标ID
	 * @return 查询已上传文件的长度
	 */
	int selectFileUploadLenght(int fileSaveID);

	/***
	 * 
	 * @param fileSaveID
	 *            修改目标ID
	 * @param len
	 *            修改长度
	 * @return 修改已上传文件的长度
	 */
	boolean updateFileUploadLenght(int fileSaveID, int len);

	/***
	 * 
	 * @param fileSaveID
	 *            查询目标ID
	 * @return 查询文件已上传序号
	 */
	int selectFileSerial(int fileSaveID);

	/***
	 * 
	 * @param fileSaveID
	 *            修改目标ID
	 * @param serial
	 *            修改序号
	 * @return 文件已上传序号的结果
	 */
	boolean updateFileSerial(int fileSaveID, int serial);

	/***
	 * 
	 * @param fileSaveID
	 *            查询目标ID
	 * @return 查询文件长度
	 */
	int selectFileLenght(int fileSaveID);

	/***
	 * 
	 * @param fileSaveID
	 *            修改目标ID
	 * @param states
	 *            修改状态
	 * @return 修改文件状态
	 */
	boolean updateFileStatus(int fileSaveID, int states);

}
