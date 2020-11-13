package util.thread;

import java.util.HashMap;

public class DataCache {
	
	public static String START_FOLLOWERS = "FOLLOWERS-";
	
	private static HashMap<String, Object> dataMap = new HashMap<>();
	
	public static synchronized void setData(String key, Object object) {
		dataMap.put(key, object);
	}
	
	public static Object getData(String key) {
		return dataMap.get(key);
	}
}
