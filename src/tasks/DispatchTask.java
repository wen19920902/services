package tasks;

import interfaces.QueueObject;

/***
 * ����ĵ���
 * 
 * @author wan
 *
 */
public class DispatchTask {

	public static final int LAND = 1;// ʵ����¼ҵ�������ı�ʶ
	public static final int ERROR = 2;// ʵ��ע��ҵ�������ı�ʶ
	public static final int ONLINE = 3;// ʵ�����߱���ҵ�������ı�ʶ
	public static final int BEGP2P = 4;// ʵ������P2p����ҵ�������ı�ʶ
	public static final int SENDWORD = 5;// ʵ���洢�ı�ҵ�������ı�ʶ
	public static final int TSENDWORD = 6;// ʵ��ת���ı�ҵ�������ı�ʶ
	public static final int SENDFILE = 7;// ʵ���洢�ļ�ҵ�������ı�ʶ
	public static final int TSENDFILE = 8;;// ʵ��ת���ļ�ҵ�������ı�ʶ
	public static final int CLASSDATA = 9;// ʵ���༶��Ϣҵ�������ı�ʶ
	public static final int PERSONALDATA = 10;// ʵ��������Ϣҵ�������ı�ʶ
	public static final int FLAND = 11;// ʵ���豸��¼ҵ�������ı�ʶ
	public static final int FERROR = 12;// ʵ���豸ע��ҵ�������ı�ʶ
	public static final int FSENDSCREEN = 13;// ʵ���ϴ��豸�����ļ�ҵ�������ı�ʶ
	public static final int FSENDMIN = 14;// ʵ���ϴ��豸ʱ��ҵ�������ı�ʶ
	public static final int FCONTROL = 15;// ʵ�������豸ҵ�������ı�ʶ

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
