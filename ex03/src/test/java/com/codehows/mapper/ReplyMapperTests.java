package com.codehows.mapper;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.codehows.domain.Criteria;
import com.codehows.domain.ReplyVO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class ReplyMapperTests {
	
	//테스트 전에 해당 번호의 게시물이 존재하는지 반드시 확인할 것
	private Long[] bnoArr = { 114703L, 114702L ,27L ,26L ,25L ,22L ,21L ,20L ,8L };
	
	@Setter(onMethod_ = @Autowired)
	private ReplyMapper mapper;
	
/*
	//외래키 사용 등록
	@Test
	public void testCreate() {
		IntStream.rangeClosed(1, 9).forEach(i -> {
			ReplyVO vo = new ReplyVO();
			
			//게시물 번호
			vo.setBno(bnoArr[i % 5]);
			vo.setReply("댓글 테스트" + i);
			vo.setReplyer("replyer" + i);
			
			mapper.insert(vo);
		});
	}
	
	//testMapper()를 통해 RealyMapper 타입의 객체가 정상적으로 사용이 가능한기 확인
	@Test
	public void testMapper() {
		log.info(mapper);
	}
*/	
/*	
	//5번 댓글이 정상적으로 조회되는지 확인. 조회(read)
	@Test
	public void testRead() {
		
		Long targetRno = 5L;
		
		ReplyVO vo = mapper.read(targetRno);
		
		log.info(vo);
	}
*/	
/*
    //삭제(delete) 
	@Test
	public void testDelete() {
		Long targetRno = 1L;
		mapper.delete(targetRno);
	}
*/	
/*	
	//수정(update)
	@Test
	public void testUpdate() {
		Long targetRno = 5L;
		ReplyVO vo = mapper.read(targetRno);
		vo.setReply("Update Reply ");
		int count = mapper.update(vo);
		log.info("UPDATE COUNT: " + count);
	}
*/
	//특정 게시물의 댓글 가져옴
	//현재 데이터베이스에 추가되어 있는 댓글들의 게시물 번호로 확인함
	@Test
	public void testList() {
		Criteria cri = new Criteria();
		//114703L
		List<ReplyVO> replies = mapper.getListWithPaging(cri, bnoArr[0]);
		replies.forEach(reply -> log.info(reply));
	}
	
}
