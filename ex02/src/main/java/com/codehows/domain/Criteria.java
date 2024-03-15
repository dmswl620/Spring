package com.codehows.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {
	
	private int pageNum;
	private int amount;
	
	public Criteria() {		// pageNum와 amount 값을 같이 전달하는 용도. 
		this(1,10);			// 생성자를 통해서 기본값을 1페이지, 10개로 지정해서 처리한다.
	}
	
	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
}
