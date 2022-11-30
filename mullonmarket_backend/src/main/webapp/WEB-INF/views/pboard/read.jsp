<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>상품 내용 </title>
	
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
</head>
<style>
body {
	background: #eee;
}
</style>
<body>	
	<h1>상품 내용</h1>
	<h3>제목 ㅡ ${vo.ptitle}</h3>
	<p>내용 ㅡ ${vo.pcontent}</p>
	<hr>
	<button id="list">목록으로</button>
</body>
<script>
	$("#list").on("click", function(){
		location.href="/pboard/list?page=1&num=6&searchType=&keyword=";
	});
</script>	
</html>