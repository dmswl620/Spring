package com.codehows.mapper;

import org.apache.ibatis.annotations.Select;

public interface TimeMapper {
	@Select("select sysdate from dual") //여기 쿼리문이 밑의 getTime에 들어감
	public String getTime();

	public String getTime2();
}
