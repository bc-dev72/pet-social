package util.thread;

import java.util.HashMap;
import java.util.HashSet;

public class UpdaterProtection {
	
	private static HashMap<String, HashSet<String>> accessed = new HashMap<>();
	

	public static synchronized boolean requestRelease(String type, String postId, boolean release) {
		if(accessed.get(type) == null)
			accessed.put(type, new HashSet<>());
		
		if(!release) {
			if(accessed.get(type).contains(postId))
				return false;
			accessed.get(type).add(postId);
			return true;
		} else {
			accessed.get(type).remove(postId);
			return true;
		}
	}

}
