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
	
	public Criteria() {		// pageNum�� amount ���� ���� �����ϴ� �뵵. 
		this(1,10);			// �����ڸ� ���ؼ� �⺻���� 1������, 10���� �����ؼ� ó���Ѵ�.
	}
	
	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
}
