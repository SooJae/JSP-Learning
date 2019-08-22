package util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

// 인증을 도와주는 Authenticator를 상속받는다.
public class Gmail extends Authenticator {

	
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication("메일","비밀번호");
	}
}


