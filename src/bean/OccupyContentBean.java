package bean;

import java.util.List;
import java.util.Map;

/***
 * 连接服务人数统计实体对象
 * 
 * @author Administrator
 *
 */
public class OccupyContentBean {

	private int id;
	private int postCount;// 开启的端口数量
	private int ipUseTime;// ip使用次数 --即每台服务器监听的线程数量
	private Map<Integer, Integer> postContent;// 端口使用次数
	private Map<Integer, List<Integer>> connectMap;// 请求用户的端口和 用户的唯一标识的集合 的集合

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
