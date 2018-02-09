package sqls.dao;

import java.util.List;

import bean.udpbean.UDPRequestBean;
import bean.udpbean.UDPRequestBean.UdpUserDataBean;

/***
 * �û�ҵ��ӿ�
 * 
 * @author wan
 *
 */
public interface UserProfessionalInterface {

	/***
	 * 
	 * @param account
	 *            �˺�
	 * @param password
	 *            ����
	 * @return ��¼���
	 */
	boolean loginCheck(String account, String password);

	/***
	 * 
	 * @param udpUserDataBean
	 *            ע������
	 * @return ע����
	 */
	boolean userError(UdpUserDataBean udpUserDataBean);

	/***
	 * 
	 * @param udpBean
	 *            ����Ŀ��
	 * @param ip
	 *            ���µĵ�ַ
	 * @param post
	 *            ���µĶ˿�
	 * @return �����û�״̬ ���
	 */
	boolean updateState(UDPRequestBean udpBean, String ip, int post);

	/***
	 * 
	 * @param userID
	 * @return ��ѯ״̬��IP��ַ�Ͷ˿�
	 */
	String selectStateIp_Post(int userID);

	/***
	 * 
	 * @param userId
	 *            ��ѯĿ��
	 * @return ��ѯ�û���Ϣ
	 */
	UdpUserDataBean selectPerson(int userId);

	/***
	 * 
	 * @param classID
	 *            ��ѯ Ŀ��
	 * @return ��ѯ�༶��Ϣ
	 */
	List<UdpUserDataBean> selectClassData(int classID);

	/***
	 * 
	 * @param userID
	 *            ��ѯID
	 * @return �����û�ID��ѯ�༶ID
	 */
	int selectUserClassID(int userID);

}
