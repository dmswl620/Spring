<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>

<style>

.uploadResult{
	width:100%;
	background-color: gray;
}

.uploadResult ul{
	display:flex;
	flex-flow: row;
	justify-content: center;
	align-items: center;
}

.uploadResult ul li {
	list-style: none;
	padding: 10px;
}

.uploadResult ul li img {
	width: 20px;
}
</style>

<body>

<h1>Upload with Ajax</h1>

<div class='uploadDiv'>
	<input type="file" name='uploadFile' multiple>
</div>

<div class='uploadResult'>
	<ul>
	
	</ul>
</div>

<button id='uploadBtn'>Upload</button>

<script
	src="https://code.jquery.com/jquery-3.3.1.min.js"
	integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=" 
	crossorigin="anonymous">
</script>

<script>
    //문서가 준비되면 실행되는 jQuery 함수입니다. 이 함수 내에는 스크립트가 실행됩니다.
	$(document).ready(function() { 
		
		//파일 확장자를 검사하기 위한 정규 표현식입니다. 
		//이 정규 표현식은 "exe", "sh", "zip", "alz"로 끝나는 파일을 걸러냅니다.
		var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");	//정규표현식
		var maxSize = 5242880; //5MB byte크기
		
		//파일 확장자 및 크기를 확인하는 함수입니다. 
		//파일 크기가 maxSize보다 크거나 허용되지 않는 확장자일 경우 알림을 표시하고 false를 반환합니다.
		function checkExtension(fileName, fileSize) {
			
			if(fileSize >= maxSize) {
				alert("파일 사이즈 초과");
				return false;
			}
			
			if(regex.test(fileName)){
				alert("해당 종류의 파일은 업로드할 수 없습니다.");
				return false;
			}
			return true;
		}
		
		//업로드 영역을 복제하여 나중에 초기화할 때 사용됩니다.
		var cloneObj = $(".uploadDiv").clone();
		
		//업로드 버튼 클릭 이벤트 핸들러입니다. 파일을 선택하고 업로드 버튼을 클릭했을 때 실행됩니다.
		$("#uploadBtn").on("click", function(e){
			
			// FormData 객체를 생성합니다. 
			//FormData는 HTML form 요소의 데이터를 간편하게 수집하여 AJAX를 통해 서버로 전송할 수 있는 방법을 제공합니다.
			var formData = new FormData();
			//HTML에서 name 속성이 "uploadFile"인 input 요소를 jQuery 선택자를 사용하여 찾습니다. 
			//이 부분은 클라이언트에서 사용자가 선택한 파일을 가져오기 위해 사용됩니다.
			var inputFile = $("input[name='uploadFile']");
			//업로드할 파일 목록을 가져옵니다.
			var files = inputFile[0].files;
			
			console.log(files);
			
			//add File Data to formData
			//file 데이터를 FormData에 담기
			//파일 목록을 순회하면서 파일을 FormData에 추가하고 확장자 및 크기를 확인합니다.
			for(var i = 0; i < files.length; i++) {
				
				if(!checkExtension(files[i].name, files[i].size)) {
					return false;
				}
				
				formData.append("uploadFile", files[i]);
			}
			
			//업로드 결과를 표시할 HTML 요소의 jQuery 선택자를 변수에 저장합니다. 
			//이 코드는 클래스가 "uploadResult"인 요소 내의 <ul>을 선택합니다.
			var uploadResult = $(".uploadResult ul");
			
			//업로드된 파일 정보를 받아서 화면에 표시하는 함수
			function showUploadedFile(uploadResultArr) {
				//업로드된 파일을 나타내는 HTML 문자열을 저장할 변수 선언. 빈 문자열로 초기화.
				var str = "";
				//업로드된 파일 정보 배열을 jQuery의 'each'함수를 사용하여 반복함
				//'i'는 인덱스를, 'obg'는 각 파일 정보를 나타낸다.
				$(uploadResultArr).each(function(i, obj){
					//업로드된 파일이 이미지가 아닌 경우 확인
					if (!obj.image) {
						//이미지 아니면 기본 아이콘과 파일이름을 '<li>'태그에 추가
						//즉, 이미지가 아닌 파일은 아이콘과 함께 표시됨
						str += "<li><img src='/resources/img/attach.png'>"+ obj.fileName +"</li>";
					
					} else { //이미지이면
					//	str += "<li>" + obj.fileName + "</li>";
				
					//서버에 이미지를 요청하기위한 경로 생성. 이 경로는 파일의 저장 경로와 파일 이름을 조합하여 생성됨
					var fileCallPath = encodeURIComponent( obj.uploadPath+ "/s_"+obj.uuid+"_"+obj.fileName);
					//이미지 파일을 표시하는 '<img>'태그를 생성하고, 이미지 경로를 'src'속성에 추가한다.
					//이때 이미지요청은 '/display'엔드포인트로 전송되고, 이미지 파일의 경로가 쿼리 매개변수로 전달됨
					str+= "<li><img src='/display?fileName="+fileCallPath+"'></li>";
				}
				});
				//생성된 HTML문자열을 uploadResult에 추가함. uploadResult는 화면에 파일 목록을 표시하는 HTML요소이다.
				//따라서 이 함수를 호출하면 화면에 업로드된 파일 목록이 추가된다.
				uploadResult.append(str);
			}
			
			//url, processData, contentType, data, type, dataType 등의 옵션을 설정하여 AJAX 요청을 구성합니다. 
			//요청이 성공하면 서버에서 반환된 파일 정보를 사용하여 화면에 업로드된 파일을 표시하고, 업로드 영역을 초기화합니다.
			$.ajax({
				url: '/uploadAjaxAction',
					processData: false,
					contentType: false,
					data: formData,
					type: 'POST',
					dataType:'json',
					success: function(result) {
						console.log(result);
						//파일이름 보여짐
						showUploadedFile(result);
						
						// 파일을 전송한 뒤 초기화된 부분을 덮어쓰기 -> 초기 상태로 변경
						$(".uploadDiv").html(cloneObj.html());
					}
			}); //$.ajax
		});
	});
</script>







</body>
</html>