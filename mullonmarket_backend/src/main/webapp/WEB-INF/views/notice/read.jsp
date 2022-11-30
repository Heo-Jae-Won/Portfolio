<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>공지사항</title>

<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
</head>
<style>
body {
	background: #eee;
}

textarea{
resize: none;
}

#button1{
margin-top:30px;
}
</style>
<body>
	<h2 style="text-align: center">공지사항</h2>

	<form style="text-align: center" name="frm" method="POST"
		action="/api/notice/update">
		<div>
			제목 ㅡ <input name="ntitle" value="${vo.ntitle}" id="ntitle" size=80
				placeholder="제목을 입력해주세요.">
		</div>
		
		<div style="margin-top: 15px">
			<textarea  cols=100 rows=10 name="ncontent" id="ncontent"
				placeholder="내용을 입력해주세요.">"${vo.ncontent}"</textarea>
		</div>
		<input type="hidden" name=nwriter value="${vo.nwriter}" /> <input
			type="hidden" name=ncode value="${vo.ncode}" />
		<div id="button1">
			<button type="button" id="btnSave">수정하기</button>
			<button style='margin-left:70px' type="button" id="back">뒤로가기</button>
		</div>
	</form>
</body>
<script>
	$("#back").on("click", function() {
		location.href = "/notice/list?page=1&num=6&searchType=&keyword=";
	});

	$("#btnSave").on("click", function() {
		var ntitle = $("#ntitle").val();
		var ncontent = $("#ncontent").val();
		if (ntitle == "") {
			alert("제목을 입력하세요!")
			ntitle.focus();
			return;
		}
		if (ncontent == "") {
			alert("내용을 입력하세요!")
			ncontent.focus();
			return;
		}
		//입력한 데이터를 전송
		frm.submit();
		alert("수정이 완료되었습니다.")
	});
	s
</script>
</html>