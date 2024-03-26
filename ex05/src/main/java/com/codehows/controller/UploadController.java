package com.codehows.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.codehows.domain.AttachFileDTO;

import lombok.extern.log4j.Log4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@Log4j
public class UploadController {
	
	@GetMapping("/uploadForm")
	public void uploadForm( ) {
		
		log.info("upload form");
	}
	
	//이 메소드는 HTTP POST 요청을 처리하며, "/uploadFormAction" 엔드포인트에 매핑됩니다.
	@PostMapping("/uploadFormAction")
	public void uploadFormPost(MultipartFile[] uploadFile, Model model) {
		
		//업로드된 파일을 저장할 디렉토리 경로를 지정합니다.
		String uploadFolder = "C:\\upload";
		
		//업로드된 각 파일에 대해 반복합니다.
		for (MultipartFile multipartFile : uploadFile) {
			
			log.info("------------------------------------------------");
			log.info("Upload File Name: " +multipartFile.getOriginalFilename());
			log.info("Upload File Size: " +multipartFile.getSize());
			
			//업로드된 파일을 저장할 File 객체를 생성합니다. 
			//이 객체는 업로드 폴더 경로와 업로드된 파일의 원본 파일 이름을 사용하여 생성됩니다.
			File saveFile = new File(uploadFolder, multipartFile.getOriginalFilename());
			
			//MultipartFile의 내용을 지정된 파일로 전송하여 저장합니다. 
			//이 메소드를 사용하여 업로드된 파일을 지정된 경로에 저장할 수 있습니다. 
			//이 과정에서 예외가 발생하면 catch 블록에서 해당 예외를 처리합니다.
			try {
				multipartFile.transferTo(saveFile);
			} catch (Exception e) {
				log.error(e.getMessage());
			}//end catch
		}//end for
	}
	
	@GetMapping("/uploadAjax")
	public void uploadAjax() {
		
		log.info("upload ajax");
	}
	
	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = new Date();
		
		String str = sdf.format(date);
		
		return str.replace("-", File.separator);
	}
	
	@PostMapping(value = "/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<AttachFileDTO>> uploadAjaxPost(MultipartFile[] uploadFile) {
		
//		log.info("update ajax post................");
		List<AttachFileDTO> list = new ArrayList<>();
		String uploadFolder = "C:\\upload";
		
		String uploadFolderPath = getFolder();
		
		//make folder -----------------------------------------
		File uploadPath = new File(uploadFolder, uploadFolderPath);
//		log.info("upload path: " + uploadPath);
		
		if (uploadPath.exists() == false) {
			uploadPath.mkdirs();
		}
		// make yyyy/MM/dd foler
		
		for (MultipartFile multipartFile : uploadFile) {
			
			/*
			 * log.info("--------------------------------------");
			 * log.info("Upload File Name: " + multipartFile.getOriginalFilename());
			 * log.info("Upload File Size: " + multipartFile.getSize());
			 */
			
			AttachFileDTO attachDTO = new AttachFileDTO();
			
			String uploadFileName = multipartFile.getOriginalFilename();
			
			//IE has file path
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
			
			log.info("only file name: " + uploadFileName);
			attachDTO.setFileName(uploadFileName);
			
			//중복네임처리
			UUID uuid = UUID.randomUUID();
			
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			
			try {
				//File saveFile = new File(uploadFolder, uploadFileName);
				File saveFile = new File(uploadPath, uploadFileName);
				multipartFile.transferTo(saveFile);
				
				attachDTO.setUuid(uuid.toString());
				attachDTO.setUploadPath(uploadFolderPath);
				
				//check image type file
				if (checkImageType(saveFile)) {
					
					attachDTO.setImage(true);
					
					FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));
					Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);
					
					thumbnail.close();
				}
				
				// add to List
				list.add(attachDTO);
				
			} catch(Exception e) {
				e.printStackTrace();
			}//end catch
		}//end for
			return new ResponseEntity<>(list, HttpStatus.OK);
	}
		
	
	private boolean checkImageType(File file) {
		
		try {
			String contentType = Files.probeContentType(file.toPath());
			
			return contentType.startsWith("image");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@GetMapping("/display") //HTTP GET 요청을 처리. "/display"엔드포인트에 매핑됨
	@ResponseBody //컨트롤러 메소드가 HTTP 응답의 본문(body)으로 데이터를 직접 반환함을 나타냄
	
	//이 메소드는 클라이언트에게 파일을 전송하는 역할을 한다.
	//'fileName'이라는 이름의 매개변수를 통해 클라이언트로부터 파일 이름을 전달받음
	public ResponseEntity<byte[]> getFile(String fileName) {
		
		log.info("fileName: " + fileName);
		//클라이언트로부터 전달된 파일 이름을 사용하여 서버에 저장된 파일의 경로를 생성함
		File file = new File("c:\\upload\\" + fileName);
		
		log.info("file: "+ file);
		
		//ResponseEntity 객체를 초기화
		ResponseEntity<byte[]> result = null;
		
		try {
			//HTTP 응답 헤더를 생성함
			HttpHeaders header = new HttpHeaders();
			//파일의 MIME타입을 결정하여 Content-Type헤더에 추가함.
			//이렇게 함으로써 브라우저는 서버로부터 받은 파일의 타입을 인식할 수 있음
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			//파일의 내용을 바이트 배열로 읽어와서 ResponseEntity 객체에 담아 반환
			//이 때, 바이트 배열로 변환된 파일의 내용과 함께 헤더와 HTTP 상태 코드를 지정함
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//클라이언트에게 응답을 반환함. 파일의 내용과 함께 헤더와 상태코드가 포함된 ResponseEntity 객체가 반환됨
		return result;
	}
	
	@GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(@RequestHeader("User-Agent") 
			String userAgent, String fileName) {
		
		Resource resource = new FileSystemResource("c:\\upload\\" + fileName);
		
		if (resource.exists() == false) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		String resourceName = resource.getFilename();
		
		//remove UUID
		String resourceOriginalName = resourceName.substring(resourceName.indexOf("_") + 1);
		
		HttpHeaders headers = new HttpHeaders();
		
		try {
			
			String downloadName = null;
			
			if(userAgent.contains("Trident")) {
				
				log.info("IE browser");
				downloadName = URLEncoder.encode(resourceOriginalName,"UTF-8").replaceAll("\\+", " ");
				
			} else if(userAgent.contains("Edge")) {
				
				log.info("Edge browser");
				downloadName = URLEncoder.encode(resourceOriginalName, "UTF-8");
				
			} else {
				
				log.info("Chrome browser");
				downloadName = new String(resourceOriginalName.getBytes("UTF-8"), "ISO-8859-1");
			}
			
			log.info("downloadName: " + downloadName);
			
			headers.add("Content-Disposition", 
					"attachment; filename=" + downloadName);
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
		return new ResponseEntity<Resource>(resource,headers, HttpStatus.OK);
		
	}

}
