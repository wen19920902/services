package sqls.dao;

import java.util.List;

import bean.udpbean.UDPRequestBean;

/***
 * ��Ϣ��ҵ��
 * 
 * @author wan
 *
 */
public interface MessageInterface {

	/***
	 * 
	 * @param ub
	 *            ���Ŀ��
	 * 
	 * @return ����ļ���Ϣ���
	 */
	boolean insertMessage_File(UDPRequestBean ub);

	/***
	 * 
	 * @param ub
	 *            ���Ŀ��
	 * @return ����ı���Ϣ���
	 */
	boolean insertMessage_Text(UDPRequestBean ub);

	/***
	 * 
	 * @param userID
	 *            ��ѯĿ��ID
	 * @return ��ѯĿ���û��Ķ�����Ϣ��¼
	 */
	List<UDPRequestBean> selectNoReadMessage(int userID);

	/***
	 * 
	 * @param userID
	 *            ����Ŀ��ID
	 * @return �����û���Ϣ�Ķ�״̬
	 */
	boolean updateMessageReadState(int userID);

}
