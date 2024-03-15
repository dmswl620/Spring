package com.codehows.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.codehows.domain.BoardVO;
import com.codehows.domain.Criteria;

public interface BoardMapper {
	
	//@Select("select * from tbl_board where bno > 0")
	
	public List<BoardVO> getList();
	
	public List<BoardVO> getListWithPaging(Criteria cri);
	
	public void insert(BoardVO board);	//PK값을 알 필요가 없을 경우
	
	public void insertSelectKey(BoardVO board);
	
	public BoardVO read(Long bno);
	
	public int delete(Long bno);
	
	public int update(BoardVO board);

}
