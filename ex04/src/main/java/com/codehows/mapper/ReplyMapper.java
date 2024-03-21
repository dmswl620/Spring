package com.codehows.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.codehows.domain.Criteria;
import com.codehows.domain.ReplyVO;

public interface ReplyMapper {
	
	public int insert(ReplyVO vo);
	
	//특정 댓글 읽기
	public ReplyVO read(Long rno);
	
	//특정 댓글 삭제
	public int delete (Long rbo);
	
	//특정 댓글 수정
	public int update(ReplyVO reply);
	
	//댓글 페이징 처리를 위해 게시물 번호의 파라미터를 전달
	public List<ReplyVO> getListWithPaging(
			@Param("cri") Criteria cri,
			@Param("bno") Long bno);
	
	public int getCountByBno(Long bno);

}

