package com.codehows.domain;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Criteria {
	
	private int pageNum;
	private int amount;
	
	private String type;
	private String keyword;
	
	public Criteria() {		// pageNum와 amount 값을 같이 전달하는 용도. 
		this(1,10);			// 생성자를 통해서 기본값을 1페이지, 10개로 지정해서 처리한다.
	}
	
	public Criteria(int pageNum, int amount) {
		this.pageNum = pageNum;
		this.amount = amount;
	}
	
	// 검색 조건을 T, W, C : 배열로 만들어서 처리
	public String[] getTypeArr() {
		
		return type == null? new String[] {}: type.split("");
	}
	
	// 
	public String getListLink() {
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("")
				.queryParam("pageNum", this.pageNum)
				.queryParam("amount", this.getAmount())
				.queryParam("type", this.getType())
				.queryParam("keyword", this.getKeyword());
		
		return builder.toUriString();
	}
}
