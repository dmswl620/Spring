package com.codehows.mapper;

import org.apache.ibatis.annotations.Select;

public interface TimeMapper {
	@Select("select sysdate from dual") //���� �������� ���� getTime�� ��
	public String getTime();

	public String getTime2();
}
