package test;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import sqls.impl.UserClassImpl;

class UserClassTest {

	@Test
	void test() {
		Map<Integer, String> map = UserClassImpl.creanUserClassImpl().selectSubject();
		for (Integer subjectID : map.keySet()) {
			System.out.println("��Ŀ �б�:" + subjectID + "===" + map.get(subjectID));
		}

		List<String> list = UserClassImpl.creanUserClassImpl().selectSchoolClass(5);
		for (String string : list) {
			System.out.println("ѧУ�༶�б� :" + string);
		}

		// System.out.println("���ѧУ���:"+UserClassImpl.creanUserClassImpl().insertSchool("�Ӷ���ѧ",
		// "454545"));
		// System.out.println("��Ӱ༶���:"+UserClassImpl.creanUserClassImpl().insertClass(5,
		// "����(2)��"));
		// System.out.println("��ӿ�Ŀ���:"+UserClassImpl.creanUserClassImpl().insertSubject("����"));
	}

}
