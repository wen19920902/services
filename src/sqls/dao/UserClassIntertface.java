package sqls.dao;

import java.util.List;
import java.util.Map;

/***
 * 用户班级信息业务
 * 
 * @author wan
 *
 */
public interface UserClassIntertface {

	/***
	 * 
	 * @param schoolName
	 *            学校名
	 * @param areaCode
	 *            学校地区号
	 * @return 添加学校结果
	 */
	boolean insertSchool(String schoolName, String areaCode);

	/***
	 * 
	 * @param schoolID
	 *            添加班级的学校ID
	 * @param className
	 *            班级名
	 * @return 添加班级结果
	 */
	boolean insertClass(int schoolID, String className);

	/***
	 * 
	 * @param subjectName
	 *            添加 目标
	 * @return 添加科目结果
	 */
	boolean insertSubject(String subjectName);

	/***
	 * 
	 * @param className
	 *            班级名
	 * @return 查询班级ID
	 */
	int selectClassID(String className);

	/***
	 * 
	 * @param classID
	 *            班级ID
	 * @return 查询班级名
	 */
	String selectClassName(int classID);

	/***
	 * 
	 * @param classID
	 *            查询 目标
	 * @return 查询班级所属的学校ID
	 */
	int selectSchoolId(int classID);

	/***
	 * 
	 * @param classID
	 *            班级ID
	 * @return 查询班级所属的学校名
	 */
	String selectSchoolName(int classID);
	
	/***
	 * 
	 * @param classID
	 * @return 查询班级所属学校ID
	 */
	int selectClassSchoolID(int classID);

	/***
	 * 
	 * @param schoolID
	 *            学校ID
	 * @return 查询学校所有的班级和班级名
	 */
	List<String> selectSchoolClass(int schoolID);

	/***
	 * 
	 * @return 获取学校所有科目
	 */
	Map<Integer, String> selectSubject();

	/***
	 * 
	 * @param teacherID
	 *            查询目标
	 * @param classID
	 *            查询条件
	 * @return 查询老师在所查班级教的科目
	 */
	int selectTeacherSubjectID(int teacherID, int classID);

}
