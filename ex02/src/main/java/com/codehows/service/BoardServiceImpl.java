package com.codehows.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.codehows.domain.BoardVO;
import com.codehows.mapper.BoardMapper;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@AllArgsConstructor
public class BoardServiceImpl implements BoardService{
	
	//@Setter(onMethod_ = @Autowired)
	//spring 4.3 이상에서 자동 처리
	private BoardMapper mapper;

	public void register(BoardVO board) {
		
	}

	public BoardVO get(Long bno) {
		return null;
	}

	public boolean modify(BoardVO board) {
		return false;
	}

	public boolean remove(Long bno) {
		return false;
	}

	public List<BoardVO> getList() {
		return null;
	}
	
		
	
}
