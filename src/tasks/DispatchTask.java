package tasks;

import interfaces.QueueObject;

/***
 * 任务的调度
 * 
 * @author wan
 *
 */
public class DispatchTask {

	public static final int LAND = 1;// 实例登录业务处理对象的标识
	public static final int ERROR = 2;// 实例注册业务处理对象的标识
	public static final int ONLINE = 3;// 实例在线报道业务处理对象的标识
	public static final int BEGP2P = 4;// 实例请求P2p连接业务处理对象的标识
	public static final int SENDWORD = 5;// 实例存储文本业务处理对象的标识
	public static final int TSENDWORD = 6;// 实例转发文本业务处理对象的标识
	public static final int SENDFILE = 7;// 实例存储文件业务处理对象的标识
	public static final int TSENDFILE = 8;;// 实例转发文件业务处理对象的标识
	public static final int CLASSDATA = 9;// 实例班级信息业务处理对象的标识
	public static final int PERSONALDATA = 10;// 实例个人信息业务处理对象的标识
	public static final int FLAND = 11;// 实例设备登录业务处理对象的标识
	public static final int FERROR = 12;// 实例设备注册业务处理对象的标识
	public static final int FSENDSCREEN = 13;// 实例上传设备截屏文件业务处理对象的标识
	public static final int FSENDMIN = 14;// 实例上传设备时长业务处理对象的标识
	public static final int FCONTROL = 15;// 实例操作设备业务处理对象的标识

	public static QueueObject crean(int type) {
		switch (type) {
		case LAND:
			return LandTask.creation();
		case ERROR:
			return ErrorTask.creation();
		case ONLINE:
			return OnLineTask.creation();
		case BEGP2P:
			return BegP2PConnectTask.creation();
		case SENDWORD:
			return SendWordTask.creation();
		case TSENDWORD:
			return TranspondWordTask.creation();
		case SENDFILE:
			return SendFileTask.creation();
		case TSENDFILE:
			return TranspondFileTask.creation();
		case CLASSDATA:
			return ClassDataTask.creation();
		case PERSONALDATA:
			return PersonalDataTask.creation();
		case FLAND:
			return FacilityLandTask.creation();
		case FERROR:
			return FacilityErrorTask.creation();
		case FSENDSCREEN:
			return FacilityScreenTask.creation();
		case FSENDMIN:
			return FacilityMinTask.creation();
		case FCONTROL:
			return FacilityControlTask.creation();

		default:
			return null;
		}
	}

}
