package test;

import java.util.List;

import org.junit.jupiter.api.Test;

import bean.udpbean.UDPRequestBean.UdpUserDataBean;
import sqls.impl.UserProfessinalImpl;

class UserPeofessinalTest {

	@Test
	void test() {
		System.out.println("��¼���:" + UserProfessinalImpl.creanUserImpl().loginCheck("311106", "111111"));

		// UDPRequestBean uBean = new UDPRequestBean();
		// uBean.setOrder(3);
		// uBean.setUserID(100);
		// uBean.setTargetID(96);
		// uBean.setTargetIP("192.168.1.5");
		// uBean.setTargetPost(35421);
		// uBean.setWordType(2);
		// uBean.setRequstPlatForm(1);
		// uBean.setDate(0);
		// uBean.setTime(0);
		// uBean.setDataLeght(0);
		// uBean.setData(new byte[0]);
		//
		// System.out.println("���߱�����¼:" +
		// UserProfessinalImpl.creanUserImpl().updateState(uBean, "211.158.178.59",
		// 12345));

		List<UdpUserDataBean> list = UserProfessinalImpl.creanUserImpl().selectClassData(3);
		for (UdpUserDataBean udpUserDataBean : list) {
			System.out.println("�༶��Ա:" + udpUserDataBean.toString());
		}

		System.out.println("�û��༶ID:" + UserProfessinalImpl.creanUserImpl().selectUserClassID(90));

		System.out.println("ǩ���˿�:" + UserProfessinalImpl.creanUserImpl().selectStateIp_Post(90));
	}

}
