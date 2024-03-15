package com.codehows.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.codehows.domain.BoardVO;
import com.codehows.domain.Criteria;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
//Java Config
//@ContextConfiguration(classes = {com.codehows.config.RootConfig.class})
@Log4j
public class BoardMapperTest {

	@Setter(onMethod_ = @Autowired)
	private BoardMapper mapper;

	/*
	 * @Test public void testGetList() { mapper.getList().forEach(board ->
	 * log.info(board)); }
	 */
	/*
	 * @Test public void testInsert() {
	 * 
	 * BoardVO board = new BoardVO(); 
	 * board.setTitle("���� �ۼ��ϴ� ��");
	 * board.setContent("���� �ۼ��ϴ� ����"); 
	 * board.setWriter("newbie");
	 * 
	 * mapper.insert(board);
	 * 
	 * log.info(board); }
	 */

	/*
	 * @Test public void testInsertSelectKey() {
	 * 
	 * BoardVO board = new BoardVO(); board.setTitle("���� �ۼ��ϴ� �� select key");
	 * board.setContent("���� �ۼ��ϴ� ���� select key"); board.setWriter("newbie");
	 * 
	 * mapper.insert(board);
	 * 
	 * log.info(board); }
	 */
	
	/*
	 * @Test public void testRead() { //�����ϴ� �Խù� ��ȣ�� �׽�Ʈ BoardVO board =
	 * mapper.read(15L);
	 * 
	 * log.info(board); }
	 */
	
	/*
	 * @Test public void testDelete() { log.info("DELETE COUNT: " +
	 * mapper.delete(17L)); }
	 */
	
/*	@Test
	public void testUpdate() {
		BoardVO board = new BoardVO();
		//������ �����ϴ� ��ȣ���� Ȯ���� ��
		board.setBno(8L);
		board.setTitle("������ ����");
		board.setContent("������ ����");
		board.setWriter("user00");
		
		int count = mapper.update(board);
		log.info("UPDATE COUNT: " + count);
	}*/
	
	@Test
	public void testPaging() {
		
		Criteria cri = new Criteria();
		//2���� 3������
		cri.setPageNum(3);
		cri.setAmount(3);
		
		List<BoardVO> list = mapper.getListWithPaging(cri);
		
		list.forEach(board -> log.info(board));
	}
	
	

}
