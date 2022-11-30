<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>공지사항</title>

<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/handlebars.js/3.0.1/handlebars.js"></script>
</head>
<style>
body {
	background: #eee;
}

#list {
	margin-top: 15px;
	margin-left: 500px;
	overflow: hidden;
	width:700px;
}

#left {
	float: left;
}

#right {
	float: right;
}

table {
	border-collapse: collapse;
	margin: 0px auto;
	margin-top: 30px;
}

td {
	padding: 10px 10px;
}

table .title {
	color: white;
}

table .row:hover {
	color: green;
	cursor: pointer;
}

button {
	margin-left: 15px;
	margin-top: 70px;
	margin-bottom: 30px;
	padding: 10px 10px;
	background: green;
	color: white;
	border-radius: 5px;
	border: none;
	cursor: pointer;
}

button:disabled {
	background: gray;
}

a{
    text-decoration:none;
	cursor: pointer;
	color:black;
	margin-left:60px;

}

a:hover{
    text-decoration:none;
	cursor: pointer;
	color:red;
}
#right #btnDelete{
margin-right:20px;
}
</style>
<body>



	<h1>공지사항</h1>

	<div id="list">
		<div id="right">
			<button type="button" class="insert_btn" id="insert"
				onClick="location.href='/notice/insert'">등록</button>
			<button style='margin-left:60px' type="button" id="btnDelete">삭제</button>
		</div>
		<div id="left">
			&nbsp;&nbsp; <a href="/">
			홈페이지
			</a> <a href="/pboard/list?page=1&num=6&searchType=&keyword=">
			상품 목록
			</a> <a href="/event/list?page=1&num=6&searchType=&keyword=">
			이벤트 목록
			</a>
		</div>
	</div>

	<table id="tbl" border=1>
		<thead>
			<tr>
				<th><input type="checkbox" name="chkAll" id="chkAll" width=50 /></th>
				<th width=300>제목</th>
				<th width=300>내용</th>
				<th width=100>작성자</th>
				<th width=100>작성일자</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${list}" var="vo">
				<tr onclick="location.href='/notice/read/${vo.ncode}'" class=row>
					<td  onclick="event.cancelBubble=true" ><input type="checkbox" name="chk" class="chk"
						ncode="${vo.ncode}" width=50 /></td>
					<td>${vo.ntitle}</td>
					<td>${vo.ncontent}</td>
					<td>${vo.nwriter}</td>
					<td>${vo.regDate}</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<div id="list">
		<form name="frm">
			<button id="prev" <c:out value="${page==1?'disabled':''}"/>>이전</button>
			<input type="text" value="${page}" size=2 name="page"> <span>${page}/${last}</span>
			<button id="next" <c:out value="${page==last?'disabled':''}"/>>다음</button>
			<input type="hidden" name=num value="${num}" /> <select
				name="searchType">
				<option value='제목'>제목</option>
				<option value='내용'>내용</option>
				<option value='제목과 내용'>제목과 내용</option>
				<option value='작성자'>작성자</option>
			</select> <input name='keyword' placeholder='검색어를 입력하세요'>
		</form>
	</div>

</body>
<script>
	var page = parseInt($(frm.page).val()) || 1;
	var last = $
	{
		last
	};
	var searchType = $(frm.searchType).val();
	var keyword = $(frm.keyword).val();
	var num = 6;

	$(frm.keyword).on("keydown", function(e) {
		if (e.keyCode === 13) {
			page = 1;
			frm.submit();
		}

	})

	$("#next").on("click", function() {
		page++;

		page = $(frm.page).val(page);

		frm.submit();

	});

	$("#prev").on("click", function() {
		page--;

		page = $(frm.page).val(page);

		frm.submit();
	});

	$("#tbl").on(
			"click",
			"#chkAll",
			function() {
				return $(this).is(":checked") ? $("#tbl .chk").prop("checked",
						true) : $("#tbl .chk").prop("checked", false)
			})

	$("#tbl").on(
			"click",
			".row .chk",
			function() {
				let
				all = $("#tbl .chk").length;
				let
				chk = $("#tbl .chk:checked").length;

				all === chk ? $("#tbl #chkAll").prop("checked", true) : $(
						"#tbl #chkAll").prop("checked", false)
			})

	$("#btnDelete").on("click", function() {
		let
		chk = $("#tbl .chk:checked").length;
		if (chk == 0) {
			alert("삭제할 항목을 선택하세요.")
			return;
		}

		$("#tbl .row .chk:checked").each(function() {
			var row = $(this).parent().parent();
			var ncode = row.find(".chk").attr("ncode")

			if (!confirm(ncode + "번 글을(를) 삭제하시겠습니까?")) {
				return;
			}

			$.ajax({
				type : "post",
				url : "/api/notice/delete?ncode=" + ncode,
			}).done(function() {
				console.log('삭제가 성공하였습니다.')
			}).fail(function(e) {
				console.log('삭제가 실패하였습니다. 원인은 아래와 같습니다.' + e);
			})

		})
	})
</script>
</html>