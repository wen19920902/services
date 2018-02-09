package sqls.dao;

import java.util.List;

import bean.udpbean.UDPRequestBean;
import bean.udpbean.UDPRequestBean.UdpUserDataBean;

/***
 * 用户业务接口
 * 
 * @author wan
 *
 */
public interface UserProfessionalInterface {

	/***
	 * 
	 * @param account
	 *            账号
	 * @param password
	 *            密码
	 * @return 登录结果
	 */
	boolean loginCheck(String account, String password);

	/***
	 * 
	 * @param udpUserDataBean
	 *            注册数据
	 * @return 注册结果
	 */
	boolean userError(UdpUserDataBean udpUserDataBean);

	/***
	 * 
	 * @param udpBean
	 *            更新目标
	 * @param ip
	 *            更新的地址
	 * @param post
	 *            更新的端口
	 * @return 更新用户状态 结果
	 */
	boolean updateState(UDPRequestBean udpBean, String ip, int post);

	/***
	 * 
	 * @param userID
	 * @return 查询状态的IP地址和端口
	 */
	String selectStateIp_Post(int userID);

	/***
	 * 
	 * @param userId
	 *            查询目标
	 * @return 查询用户信息
	 */
	UdpUserDataBean selectPerson(int userId);

	/***
	 * 
	 * @param classID
	 *            查询 目标
	 * @return 查询班级信息
	 */
	List<UdpUserDataBean> selectClassData(int classID);

	/***
	 * 
	 * @param userID
	 *            查询ID
	 * @return 根据用户ID查询班级ID
	 */
	int selectUserClassID(int userID);

}
