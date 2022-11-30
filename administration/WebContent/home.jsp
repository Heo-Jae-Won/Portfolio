<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>
	<title>허재원의 홈페이지</title>
	<link rel="stylesheet" href="/css/home.css">
</head>
<body>
	<div id="top">
		<img src="/images/집.jpg" width=800>
	</div>
	<div id="menu">
		<span class="item"><a href="/">학사관리시스템</a></span>
		<span class="item"><a href="/pro/list">교수관리</a></span>
		<span class="item"><a href="/stu/list">학생관리</a></span>
		<span class="item"><a href="/cou/list">수업관리</a></span>
	</div>
	<div id="middle">
		<jsp:include page="${pageName}"></jsp:include>
	</div>
	<div id="bottom">
		<h3>Copyright 2022. 인천일보아카데미 All rights reserved</h3>
	</div>
</body>
</html>