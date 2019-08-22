package util;

import java.security.MessageDigest;

public class SHA256 {

	public static String getSHA256(String input) {
		StringBuffer result = new StringBuffer();
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			//단순하게 SHA256을 사용하면 해킹당할 위험이 있으므로 salt값을 이용한다.
			byte[] salt ="Hello! My name is Lee Soo Jae".getBytes();
			digest.reset();
			digest.update(salt);
			byte[] chars = digest.digest(input.getBytes("UTF-8"));
			for(int i=0; i<chars.length; i++) {
				String hex = Integer.toHexString(0xff & chars[i]);
				//한자리 수인 경우 0을 붙여서 두자리 값을 갖는 16진수 형태로 출력하게 만듬
				if(hex.length()==1) result.append("0");
				result.append(hex);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}
	
}
