package com.codehows.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.codehows.domain.Criteria;
import com.codehows.domain.ReplyVO;

public interface ReplyMapper {
	
	public int insert(ReplyVO vo);
	
	//Ư�� ��� �б�
	public ReplyVO read(Long rno);
	
	//Ư�� ��� ����
	public int delete (Long rbo);
	
	//Ư�� ��� ����
	public int update(ReplyVO reply);
	
	//��� ����¡ ó���� ���� �Խù� ��ȣ�� �Ķ���͸� ����
	public List<ReplyVO> getListWithPaging(
			@Param("cri") Criteria cri,
			@Param("bno") Long bno);
	
	public int getCountByBno(Long bno);

}

