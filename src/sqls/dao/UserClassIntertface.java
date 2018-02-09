package sqls.dao;

import java.util.List;
import java.util.Map;

/***
 * �û��༶��Ϣҵ��
 * 
 * @author wan
 *
 */
public interface UserClassIntertface {

	/***
	 * 
	 * @param schoolName
	 *            ѧУ��
	 * @param areaCode
	 *            ѧУ������
	 * @return ���ѧУ���
	 */
	boolean insertSchool(String schoolName, String areaCode);

	/***
	 * 
	 * @param schoolID
	 *            ��Ӱ༶��ѧУID
	 * @param className
	 *            �༶��
	 * @return ��Ӱ༶���
	 */
	boolean insertClass(int schoolID, String className);

	/***
	 * 
	 * @param subjectName
	 *            ��� Ŀ��
	 * @return ��ӿ�Ŀ���
	 */
	boolean insertSubject(String subjectName);

	/***
	 * 
	 * @param className
	 *            �༶��
	 * @return ��ѯ�༶ID
	 */
	int selectClassID(String className);

	/***
	 * 
	 * @param classID
	 *            �༶ID
	 * @return ��ѯ�༶��
	 */
	String selectClassName(int classID);

	/***
	 * 
	 * @param classID
	 *            ��ѯ Ŀ��
	 * @return ��ѯ�༶������ѧУID
	 */
	int selectSchoolId(int classID);

	/***
	 * 
	 * @param classID
	 *            �༶ID
	 * @return ��ѯ�༶������ѧУ��
	 */
	String selectSchoolName(int classID);
	
	/***
	 * 
	 * @param classID
	 * @return ��ѯ�༶����ѧУID
	 */
	int selectClassSchoolID(int classID);

	/***
	 * 
	 * @param schoolID
	 *            ѧУID
	 * @return ��ѯѧУ���еİ༶�Ͱ༶��
	 */
	List<String> selectSchoolClass(int schoolID);

	/***
	 * 
	 * @return ��ȡѧУ���п�Ŀ
	 */
	Map<Integer, String> selectSubject();

	/***
	 * 
	 * @param teacherID
	 *            ��ѯĿ��
	 * @param classID
	 *            ��ѯ����
	 * @return ��ѯ��ʦ������༶�̵Ŀ�Ŀ
	 */
	int selectTeacherSubjectID(int teacherID, int classID);

}
