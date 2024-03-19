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
	
	//�׽�Ʈ ���� �ش� ��ȣ�� �Խù��� �����ϴ��� �ݵ�� Ȯ���� ��
	private Long[] bnoArr = { 114703L, 114702L ,27L ,26L ,25L ,22L ,21L ,20L ,8L };
	
	@Setter(onMethod_ = @Autowired)
	private ReplyMapper mapper;
	
/*
	//�ܷ�Ű ��� ���
	@Test
	public void testCreate() {
		IntStream.rangeClosed(1, 9).forEach(i -> {
			ReplyVO vo = new ReplyVO();
			
			//�Խù� ��ȣ
			vo.setBno(bnoArr[i % 5]);
			vo.setReply("��� �׽�Ʈ" + i);
			vo.setReplyer("replyer" + i);
			
			mapper.insert(vo);
		});
	}
	
	//testMapper()�� ���� RealyMapper Ÿ���� ��ü�� ���������� ����� �����ѱ� Ȯ��
	@Test
	public void testMapper() {
		log.info(mapper);
	}
*/	
/*	
	//5�� ����� ���������� ��ȸ�Ǵ��� Ȯ��. ��ȸ(read)
	@Test
	public void testRead() {
		
		Long targetRno = 5L;
		
		ReplyVO vo = mapper.read(targetRno);
		
		log.info(vo);
	}
*/	
/*
    //����(delete) 
	@Test
	public void testDelete() {
		Long targetRno = 1L;
		mapper.delete(targetRno);
	}
*/	
/*	
	//����(update)
	@Test
	public void testUpdate() {
		Long targetRno = 5L;
		ReplyVO vo = mapper.read(targetRno);
		vo.setReply("Update Reply ");
		int count = mapper.update(vo);
		log.info("UPDATE COUNT: " + count);
	}
*/
	//Ư�� �Խù��� ��� ������
	//���� �����ͺ��̽��� �߰��Ǿ� �ִ� ��۵��� �Խù� ��ȣ�� Ȯ����
	@Test
	public void testList() {
		Criteria cri = new Criteria();
		//114703L
		List<ReplyVO> replies = mapper.getListWithPaging(cri, bnoArr[0]);
		replies.forEach(reply -> log.info(reply));
	}
	
}
