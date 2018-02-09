package test;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.junit.jupiter.api.Test;

import bean.udpbean.UDPRequestBean;
import bean.udpbean.UDPRequestBean.UdpFileDataBean;
import sqls.impl.MessageImpl;

class MessageTest {

	@Test
	void test() throws UnsupportedEncodingException {

		UDPRequestBean ub = new UDPRequestBean();
		UdpFileDataBean uFileBean = new UdpFileDataBean();

		ub.setOrder(8);
		ub.setUserID(151);
		ub.setDate(0);
		ub.setTime(0);
		ub.setTargetID(152);
		uFileBean.setFileName("��һ�д���");
		uFileBean.setFileLenght(512454);
		uFileBean.setFileSaveID(5);
		uFileBean.setFileCreanData(uFileBean.timeString());
//		byte[] a = uFileBean.sealObject();
		byte[] b = "�㻹����".getBytes("GB2312");
		ub.setDataLeght(b.length);
		ub.setData(b);
		// System.out.println("����ļ���Ϣ:" +
		// MessageImpl.creanMessageImpl().insertMessage_File(ub));

		System.out.println("����ı���Ϣ" + MessageImpl.creanMessageImpl().insertMessage_Text(ub));

		List<UDPRequestBean> list = MessageImpl.creanMessageImpl().selectNoReadMessage(152);
		if (list != null)
			for (UDPRequestBean udpRequestBean : list) {
				System.out.println("δ�Ķ�����Ϣ:" + udpRequestBean.getUserID());
			}

		System.out.println("�޸��û�δ�Ķ�����Ϣ���:" + MessageImpl.creanMessageImpl().updateMessageReadState(152));

	}

}
