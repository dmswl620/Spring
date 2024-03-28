package com.codehows.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.log4j.Log4j;

@Log4j
public class CustomNoOpPasswordEncoder implements PasswordEncoder {
	
	//encode �޼��� ����. �� �޼���� ���� rawPassword�� �״�� ��ȯ�ϴµ�,
	//�����δ� ��й�ȣ�� ��ȣȭ�ϴ� ��ſ� �״�� �����Ѵ�.
	public String encode(CharSequence rawPassword) {
		log.warn("before encode: " + rawPassword);
		return rawPassword.toString(); //���� rawPassword�� ���ڿ��� ��ȯ�Ͽ� ��ȯ��
	}
	// ����ڰ� �Է��� ��й�ȣ�� ����� ��й�ȣ�� ��ġ�ϴ��� Ȯ��
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		log.warn("matches: " + rawPassword + ":" + encodedPassword);
		//����ڰ� �Է��� ��й�ȣ�� ����� ��й�ȣ�� ���ڿ��� ��ȯ�Ͽ� ���ϰ�, ����� ��ȯ��
		return rawPassword.toString().equals(encodedPassword);
	}

}
