package util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

// ������ �����ִ� Authenticator�� ��ӹ޴´�.
public class Gmail extends Authenticator {

	
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication("����","��й�ȣ");
	}
}


