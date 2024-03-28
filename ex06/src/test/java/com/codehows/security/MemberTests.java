package com.codehows.security;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/root-context.xml",
	"file:src/main/webapp/WEB-INF/spring/security-context.xml"})
@Log4j
public class MemberTests {
	//PasswordEncoder와 DataSource를 주입받기 위한 필드를 선언하고, 
	//Lombok의 @Setter어노테이션을 이용하여 setter 메서드 생성
	@Setter(onMethod_= @Autowired)
	private PasswordEncoder pwencoder;
	
	@Setter(onMethod_ = @Autowired)
	private DataSource ds;
	//회원 데이터를 데이터베이스에 삽입하는 테스트 진행
/*	@Test
	public void testInsertMember() {
		//회원 데이터를 삽입하는 SQL 쿼리 정의
		String sql = "insert into tbl_member(userid, userpw, username) values (?,?,?)";
		
		//100명의 회원 데이터를 생성하기 위한 반복문 시작
		for(int i = 0; i < 100; i++) {
			
			Connection con = null;				//데이터베이스 연결하기 위한 객체
			PreparedStatement pstmt = null;		//SQL쿼리를 실행하기 위한 객체
			
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(sql);
				
				//Password를 사용해 비밀번호 암호화하고, 그 값을 SQL 쿼리에 설정
				pstmt.setString(2,  pwencoder.encode("pw" + i));
				
				if(i < 80) {
					pstmt.setString(1, "user"+i);
					pstmt.setString(3, "일반사용자"+i);
				} else if (i < 90) {
					pstmt.setString(1, "manager"+i);
					pstmt.setString(3, "일반사용자"+i);
				} else {
					pstmt.setString(1,"admin"+i);
					pstmt.setString(3, "관리자"+i);
				}
			
				pstmt.executeUpdate();		//SQL쿼리를 실행하여 회원 데이터를 DB에 삽입
				//예외 처리 및 리소스 해제 수행
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				if(pstmt != null) { try {pstmt.close(); } catch(Exception e) {} }
				if(con != null) { try {con.close(); } catch(Exception e) {} }
			}
		}//end for
	}*/
	
	@Test
	public void testInsertAuth() {
		
		String sql = "insert into tbl_member_auth (userid, auth) values (?,?)";
		
		for(int i = 0; i < 100; i++) {
			Connection con = null;
			PreparedStatement pstmt = null;
			
			try {
				con = ds.getConnection();
				pstmt = con.prepareStatement(sql);
				//회원 권한 데이터의 사용자 아이디와 권한 설정
				if(i < 80) {
					pstmt.setString(1, "user"+i);
					pstmt.setString(2, "ROLE_USER");
				} else if (i < 90) {
					pstmt.setString(1, "manager"+i);
					pstmt.setString(2, "ROLE_MEMBER");
				} else {
					pstmt.setString(1,"admin"+i);
					pstmt.setString(2, "ROLE_ADMIN");
				}
			
				pstmt.executeUpdate();		//SQL쿼리를 실행하여 회원 데이터를 DB에 삽입
				
				//예외 처리 및 리소스 해제 수행
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				if(pstmt != null) { try {pstmt.close(); } catch(Exception e) {} }
				if(con != null) { try {con.close(); } catch(Exception e) {} }
				
			}
		}
	}
}
