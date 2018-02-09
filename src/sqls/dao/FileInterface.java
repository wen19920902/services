package sqls.dao;

import bean.udpbean.UDPRequestBean;

/***
 * �ļ�������ҵ��
 * 
 * @author wan
 *
 */
public interface FileInterface {

	/***
	 * 
	 * @param ub
	 *            �洢Ŀ��
	 * @param path
	 *            �洢�ļ�·��
	 * @return �洢�ļ���¼ID
	 */
	int insertFile(UDPRequestBean ub, String path);

	/***
	 * 
	 * @param fileSaveID
	 *            ��ѯĿ��ID
	 * @return ��ѯ�ļ��洢Ŀ¼
	 */
	String selectFilePath(int fileSaveID);

	/***
	 * 
	 * @param fileSaveID
	 *            ��ѯĿ��ID
	 * @return ��ѯ���ϴ��ļ��ĳ���
	 */
	int selectFileUploadLenght(int fileSaveID);

	/***
	 * 
	 * @param fileSaveID
	 *            �޸�Ŀ��ID
	 * @param len
	 *            �޸ĳ���
	 * @return �޸����ϴ��ļ��ĳ���
	 */
	boolean updateFileUploadLenght(int fileSaveID, int len);

	/***
	 * 
	 * @param fileSaveID
	 *            ��ѯĿ��ID
	 * @return ��ѯ�ļ����ϴ����
	 */
	int selectFileSerial(int fileSaveID);

	/***
	 * 
	 * @param fileSaveID
	 *            �޸�Ŀ��ID
	 * @param serial
	 *            �޸����
	 * @return �ļ����ϴ���ŵĽ��
	 */
	boolean updateFileSerial(int fileSaveID, int serial);

	/***
	 * 
	 * @param fileSaveID
	 *            ��ѯĿ��ID
	 * @return ��ѯ�ļ�����
	 */
	int selectFileLenght(int fileSaveID);

	/***
	 * 
	 * @param fileSaveID
	 *            �޸�Ŀ��ID
	 * @param states
	 *            �޸�״̬
	 * @return �޸��ļ�״̬
	 */
	boolean updateFileStatus(int fileSaveID, int states);

}
