package com.codehows.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.log4j.Log4j;

@Log4j
public class CustomNoOpPasswordEncoder implements PasswordEncoder {
	
	//encode 메서드 정의. 이 메서드는 받은 rawPassword를 그대로 반환하는데,
	//실제로는 비밀번호를 암호화하는 대신에 그대로 저장한다.
	public String encode(CharSequence rawPassword) {
		log.warn("before encode: " + rawPassword);
		return rawPassword.toString(); //받은 rawPassword를 문자열로 변환하여 반환함
	}
	// 사용자가 입력한 비밀번호와 저장된 비밀번호가 일치하는지 확인
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		log.warn("matches: " + rawPassword + ":" + encodedPassword);
		//사용자가 입력한 비밀번호와 저장된 비밀번호를 문자열로 변환하여 비교하고, 결과를 반환함
		return rawPassword.toString().equals(encodedPassword);
	}

}
