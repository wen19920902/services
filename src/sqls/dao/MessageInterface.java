package sqls.dao;

import java.util.List;

import bean.udpbean.UDPRequestBean;

/***
 * 消息的业务
 * 
 * @author wan
 *
 */
public interface MessageInterface {

	/***
	 * 
	 * @param ub
	 *            添加目标
	 * 
	 * @return 添加文件消息结果
	 */
	boolean insertMessage_File(UDPRequestBean ub);

	/***
	 * 
	 * @param ub
	 *            添加目标
	 * @return 添加文本消息结果
	 */
	boolean insertMessage_Text(UDPRequestBean ub);

	/***
	 * 
	 * @param userID
	 *            查询目标ID
	 * @return 查询目标用户阅读的消息记录
	 */
	List<UDPRequestBean> selectNoReadMessage(int userID);

	/***
	 * 
	 * @param userID
	 *            更新目标ID
	 * @return 更新用户消息阅读状态
	 */
	boolean updateMessageReadState(int userID);

}
