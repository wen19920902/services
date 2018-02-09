package sqls.bean;

/***
 * 键值对的对象存储
 * 
 * @author wan
 *
 */
public class KeyValuesBean {

	public static String key_values(int key, String values) {
		if (values == null)
			return null;
		return key + ":" + values;
	}

	public static int getKey(String json) {
		if (json == null)
			return 0;
		return Integer.valueOf(json.split(":")[0]);
	}

	public static String getValues(String json) {
		if (json == null)
			return null;
		return json.split(":")[1];
	}

}
