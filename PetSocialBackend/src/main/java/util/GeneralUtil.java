package util;

public class GeneralUtil {
	
    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
    
	public static String cleanAuthHeader(String token) {
		if(token == null)
    		token = "";
    	if(token.startsWith("Bearer"))
    		token = token.replaceFirst("Bearer", "").trim();
    	else 
    		token = "";
    	return token;
	}

}
