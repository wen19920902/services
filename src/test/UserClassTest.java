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
			System.out.println("科目 列表:" + subjectID + "===" + map.get(subjectID));
		}

		List<String> list = UserClassImpl.creanUserClassImpl().selectSchoolClass(5);
		for (String string : list) {
			System.out.println("学校班级列表 :" + string);
		}

		// System.out.println("添加学校结果:"+UserClassImpl.creanUserClassImpl().insertSchool("河东中学",
		// "454545"));
		// System.out.println("添加班级结果:"+UserClassImpl.creanUserClassImpl().insertClass(5,
		// "初七(2)班"));
		// System.out.println("添加科目结果:"+UserClassImpl.creanUserClassImpl().insertSubject("体育"));
	}

}
