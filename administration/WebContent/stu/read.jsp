<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<h1>학생정보</h1>
<form name=frm method=post>
	<table>
		<tr>
			<td width=100>학생번호</td>
			<td width=100><input type=text name=scode value="${stuvo.scode}"
				readonly></td>
			<td width=100>학생이름</td>
			<td width=100><input type=text name=sname value="${stuvo.sname}"></td>

			<td width=100>학생학과</td>
			<td width=100><select name=dept>
					<option value=전산
						<c:out value="${stuvo.dept!='전산' ? 'disabled' : ''}"/>>
						컴퓨터정보공학과</option>
					<option value=전자
						<c:out value="${stuvo.dept!='전자' ? 'disabled' : ''}"/>>
						전자공학과</option>
					<option value=건축
						<c:out value="${stuvo.dept!='건축' ? 'disabled' : ''}"/>>
						건축토목공학과</option>
			</select></td>
		</tr>

		<tr>
			<td>학생학년</td>
			<td><input type=radio name=year ﻿value=1
				 <c:out value="${stuvo.year=='1' ? 'checked' : ''}"/>>1학년&nbsp;&nbsp;
				<input type=radio name=year
				value=2 <c:out value="${stuvo.year=='2' ? 'checked' : ''}"/>>2학년&nbsp;&nbsp;
				<input type=radio name=year
				value=3  <c:out value="${stuvo.year=='3' ? 'checked' : ''}"/>>3학년&nbsp;&nbsp;</td>
			<td>생년월일</td>
			<td><input type=date name=birthday value="${stuvo.birthday}"></td>
			<td>지도교수</td>
			<td><select name=advisor id=advisor></select></td>
		</tr>

	</table>
	<div class=buttons>
		<button>수정</button>
		<button type=reset>취소</button>
	</div>
</form>

<script id="tbl_Temp" type="text/x-handlebars-template">
	{{#each array}}
		<option value="{{pcode}}" {{selected pcode}}>{{pname}}({{dept}})</option>
	{{/each}}
	
	</script>


<h2>수강신청</h2>
<div>
	수강신청 과목: <select id=alist></select>
	<button id=register>수강신청</button>
</div>
<script id="alist_Temp" type="text/x-handlebars-template">
{{#each .}}
	<option value="{{lcode}}">{{lcode}}:{{lname}}:{{pname}}:(<b>{{persons}}</b>)/{{capacity}}</option>
{{/each}}
	
</script>

<table id=tbl_ecou></table>
<script id="tbl_ecou_Temp" type="text/x-handlebars-template">
	<tr class=title>
			<td width=100>강좌번호.</td>
			<td width=300>강좌이름</td>
			<td width=200>담당교수</td>
			<td width=150>강의실</td>
			<td width=1000>강의시수</td>
			<td width=1000>수강인원</td>
			<td width=1000>수강신청일</td>
			<td width=1000>수강취소</td>
		</tr>
	{{#each .}}
	<tr class=row>
			<td width=100>{{lcode}}</td>
			<td width=300>{{lname}}</td>
			<td width=200>{{pname}}</td>
			<td width=150>{{room}}</td>
			<td width=1000>{{hours}}</td>
			<td width=1000>{{persons}}/({{capacity}})</td>
			<td width=1000>{{edate}}</td>
			<td><button lcode="{{lcode}}">수강취소</button></td>
	</tr>
	{{/each}}



</script>

<script>
	let scode = ${stuvo.scode};

	getenList();
	getaList();
	
	$(frm).on("submit",(e)=>{
		e.preventDefault();
		
		if(!confirm("수정하시겠습니까?")){
			return;
		}
		frm.action="/stu/update";
		frm.submit();
		
		
	})
	
	//수강취소버튼을 클릭한 경우
	$("#tbl_ecou").on("click",".row button",function(){
		let lcode=$(this).attr("lcode")
		if(!confirm(lcode+"강좌를 수강취소하시겠습니까?")){
			return;
		}
		$.ajax({
			type : "post",
			url : "/enroll/delete",
			data : {
				lcode : lcode,
				scode : scode
			}
		}).done(function() {
			alert("수강신청이 취소되었습니다.");
			getenList();
			getaList();

		})
		
		
		
	})

	//수강신청버튼을 클릭한 경우
	$("#register").on("click", function() {
		let lcode = $("#alist").val();

		$.ajax({
			type : "get",
			url : "/enroll/check",
			data : {
				lcode : lcode,
				scode : scode
			},
			dataType : "json"
		//중복체크
		}).done(function(data) {
			if (data.check == 1) {
				alert("이미 수강신청한 수업입니다.")
			} else {
				if (!confirm(lcode + "강좌를 수강하시겠습니까?")) {
					return;
				}

				$.ajax({
					type : "post",
					url : "/enroll/insert",
					data : {
						lcode : lcode,
						scode : scode
					}
				}).done(function() {
					alert("수강신청이 완료되었습니다.");
					getenList();
					getaList();

				})

			}

		})

	})

	//수강신청할 강의목록 select box
	function getaList() {
		$.ajax({

			type : "get",
			url : "/enroll/alist.json",
			dataType : "json",
		}).done(function(data) {
			let source = $("#alist_Temp").html();
			let temp = Handlebars.compile(source);
			let html = temp(data);
			$("#alist").html(html);

		})

	}

	//수강신청한 강의목록 table
	function getenList() {
		$.ajax({

			type : "get",
			url : "/enroll/ecoulist.json",
			dataType : "json",
			data : {scode : scode}
		}).done(function(data) {
			
			let source = $("#tbl_ecou_Temp").html();
			let temp = Handlebars.compile(source);
			let html = temp(data);
			$("#tbl_ecou").html(html);
			
			if(data.length==0){
				 $("#tbl_ecou").append("<tr><td colspan=6 class='none'>검색된 자료가 없습니다!</td></tr>");
			}
				
			
		

		})

	}
</script>






<script>
	let advisor = ${stuvo.advisor};
	let dept = $(frm.dept).val();

	Handlebars.registerHelper("selected", function(pcode) {
		advisor === pcode ? "selected" : "";

	})

	$.ajax({

		type : "get",
		url : "/pro/list.json",
		dataType : "json",
		data : {
			key : "dept",
			word : dept,
			per : 100,
			order : "pname",
			desc : "",
			page : 1
		}
	}).done(function(data) {
		let source = $("#tbl_Temp").html();
		let temp = Handlebars.compile(source);
		let html = temp(data);
		$("#advisor").html(html);

	})
</script>











