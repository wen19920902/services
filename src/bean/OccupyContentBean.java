package bean;

import java.util.List;
import java.util.Map;

/***
 * ���ӷ�������ͳ��ʵ�����
 * 
 * @author Administrator
 *
 */
public class OccupyContentBean {

	private int id;
	private int postCount;// �����Ķ˿�����
	private int ipUseTime;// ipʹ�ô��� --��ÿ̨�������������߳�����
	private Map<Integer, Integer> postContent;// �˿�ʹ�ô���
	private Map<Integer, List<Integer>> connectMap;// �����û��Ķ˿ں� �û���Ψһ��ʶ�ļ��� �ļ���

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPostCount() {
		return postCount;
	}

	public void setPostCount(int postCount) {
		this.postCount = postCount;
	}

	public int getIpUseTime() {
		return ipUseTime;
	}

	public void setIpUseTime(int ipUseTime) {
		this.ipUseTime = ipUseTime;
	}

	public Map<Integer, Integer> getPostContent() {
		return postContent;
	}

	public void setPostContent(Map<Integer, Integer> postContent) {
		this.postContent = postContent;
	}

	public Map<Integer, List<Integer>> getConnectMap() {
		return connectMap;
	}

	public void setConnectMap(Map<Integer, List<Integer>> connectMap) {
		this.connectMap = connectMap;
	}

}
