package com.codehows.domain;

import lombok.Data;

@Data
public class AttachFileDTO {

	private String fileName;	//���� ������ �̸�
	private String uploadPath;	//���ε� ���
	private String uuid;		//UUID��
	private boolean image;		//�̹��� ����
}
