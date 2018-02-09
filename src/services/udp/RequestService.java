package services.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import services.utils.ObjectUtils;
import tasks.DispatchTask;

/***
 * ������������
 * 
 * @author Administrator
 *
 */
public class RequestService extends ObjectUtils implements Runnable {

	private RequestService(DatagramSocket ds) {
		super(ds);
	}

	/***
	 * 
	 * @param ds
	 *            ����Ŀ��
	 * @return ����һ������UDP ���ص�ǰ�����˿�
	 */
	public static int createServie(DatagramSocket ds) {
		RequestService rs = new RequestService(ds);
		UdpThreadPool.excute(rs);
		return rs.post;
	}

	@Override
	public void run() {
		byte[] csbyts = new byte[1024];// �����û����͵�����
		byte[] heanByte = new byte[4];// �����û����͵�����
		try {
			while (true) {
				DatagramPacket reviceP = new DatagramPacket(csbyts, csbyts.length);
				ds.receive(reviceP);
				System.arraycopy(heanByte, 0, reviceP.getData(), 0, heanByte.length);
				switch (bytesToIntLow(heanByte)) {
				case 1:// �û���¼
					DispatchTask.crean(DispatchTask.LAND).put(reviceP);
					break;
				case 2:// �û�ע��
					DispatchTask.crean(DispatchTask.ERROR).put(reviceP);
					break;
				case 3:// ���߱���
					DispatchTask.crean(DispatchTask.ONLINE).put(reviceP);
					break;
				case 4:// ����p2p����Ŀ��
					DispatchTask.crean(DispatchTask.BEGP2P).put(reviceP);
					break;
				case 5:// ������Ϣ(�洢)
					DispatchTask.crean(DispatchTask.SENDWORD).put(reviceP);
					break;
				case 6:// ������Ϣ(�洢��ת��)
					DispatchTask.crean(DispatchTask.TSENDWORD).put(reviceP);
					break;
				case 7:// �����ļ�(�洢)
					DispatchTask.crean(DispatchTask.SENDFILE).put(reviceP);
					break;
				case 8:// �����ļ�(�洢��ת��)
					DispatchTask.crean(DispatchTask.TSENDFILE).put(reviceP);
					break;
				case 9:// �����ȡ�༶��Ϣ
					DispatchTask.crean(DispatchTask.CLASSDATA).put(reviceP);
					break;
				case 10:// �����ȡ������Ϣ
					DispatchTask.crean(DispatchTask.PERSONALDATA).put(reviceP);
					break;
				case 11:// �豸��¼
					DispatchTask.crean(DispatchTask.FLAND).put(reviceP);
					break;
				case 12:// �豸ע��
					DispatchTask.crean(DispatchTask.FERROR).put(reviceP);
					break;
				case 13:// �ϴ��豸�����ļ�
					DispatchTask.crean(DispatchTask.FSENDSCREEN).put(reviceP);
					break;
				case 14:// �ϴ��豸ʱ��
					DispatchTask.crean(DispatchTask.FSENDMIN).put(reviceP);
					break;
				case 15:// �����豸
					DispatchTask.crean(DispatchTask.FCONTROL).put(reviceP);
					break;

				default:
					System.out.println("�����ʲô?");
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != ds)
				ds.close();
		}
	}

}
