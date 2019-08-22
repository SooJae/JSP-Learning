package util;

import java.security.MessageDigest;

public class SHA256 {

	public static String getSHA256(String input) {
		StringBuffer result = new StringBuffer();
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			//�ܼ��ϰ� SHA256�� ����ϸ� ��ŷ���� ������ �����Ƿ� salt���� �̿��Ѵ�.
			byte[] salt ="Hello! My name is Lee Soo Jae".getBytes();
			digest.reset();
			digest.update(salt);
			byte[] chars = digest.digest(input.getBytes("UTF-8"));
			for(int i=0; i<chars.length; i++) {
				String hex = Integer.toHexString(0xff & chars[i]);
				//���ڸ� ���� ��� 0�� �ٿ��� ���ڸ� ���� ���� 16���� ���·� ����ϰ� ����
				if(hex.length()==1) result.append("0");
				result.append(hex);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result.toString();
	}
	
}
