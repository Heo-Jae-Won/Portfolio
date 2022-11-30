<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<h1>교수정보</h1>
<form name=frm method=post>
	<button type=button id=delete>삭제</button>
	<table>
		<tr>
			<td width=100>교수번호</td>
			<td width=100><input type=text name=pcode value="${provo.pcode}"
				readonly></td>
			<td width=100>교수이름</td>
			<td width=100><input type=text name=pname value="${provo.pname}"></td>

			<td width=100>교수학과</td>
			<td width=100><select name=dept>
					<option value=전산
						<c:out value="${provo.dept=='전산' ? 'selected' : ''}"/>>
						컴퓨터정보공학과</option>
					<option value=전자
						<c:out value="${provo.dept=='전자' ? 'selected' : ''}"/>>
						전자공학과</option>
					<option value=건축
						<c:out value="${provo.dept=='건축' ? 'selected' : ''}"/>>
						건축토목공학과</option>
			</select></td>
		</tr>

		<tr>
			<td>교수직급</td>
			<td><input type=radio name="title" value=정교수
				<c:out value="${provo.title=='정교수' ? 'checked' : ''}"/>>정교수&nbsp;&nbsp;
				<input type=radio name="title" value=부교수
				<c:out value="${provo.title=='부교수' ? 'checked' : ''}"/>>부교수&nbsp;&nbsp;
				<input type=radio name="title" value=조교수
				<c:out value="${provo.title=='조교수' ? 'checked' : ''}"/>>조교수&nbsp;&nbsp;
			</td>

			<td>임용일자</td>
			<td><input type=date name=hiredate value="${provo.hiredate}"></td>
			<td>급여</td>
			<td><input type=number name=salary value="${provo.salary}"></td>
		</tr>

	</table>
	<div class=buttons>
		<button>수정</button>
		<button type=reset>취소</button>
	</div>
</form>
<h1>담당과목</h1>

<table id=tbl_Course></table>
<script id="tbl_Course_Temp" type="text/x-handlebars-template">
	<tr class=title>
		<td width=100>강좌번호</td>
		<td width=100>강좌이름</td>
		<td width=100>강의실</td>
		<td width=100>강의시수</td>
		<td width=100>수강인원</td>
		<td width=100>강좌정보</td>
	</tr>
{{#each .}}
	<tr class=row>
		<td>{{lcode}}</td>
		<td>{{lname}}</td>
		<td>{{room}}</td>
		<td>{{hours}}</td>
		<td>{{persons}}<b>({{capacity}})</b></td>
		<td><button onclick="location.href='/cou/read?lcode={{lcode}}'">강좌정보</button></td>
	</tr>
{{/each}}
</script>
<h1>담당학생</h1>
<table id=tbl_Student></table>
<script id="tbl_Student_Temp" type="text/x-handlebars-template">
	<tr class=title>
		<td width=100>학생번호</td>
		<td width=100>학생이름</td>
		<td width=100>학과</td>
		<td width=100>학년</td>
		<td width=100>생년월일</td>
		<td width=100>담당교수</td>
		<td>학생정보</td>
	</tr>
{{#each .}}
	<tr class=row>
		<td>{{scode}}</td>
		<td>{{sname}}</td>
		<td>{{dept}}</td>
		<td>{{year}}</td>
		<td>{{birthday}}</td>
		<td>{{advisor}}</td>
		<td><button onclick="location.href='/stu/read?scode={{scode}}'">학생정보</button></td>
	
	</tr>
{{/each}}
</script>

<script>
let pcode=${provo.pcode};


$(frm).on("submit",(e)=>{
	e.preventDefault();
	
	if(!confirm("수정하시겠습니까?")){
		return;
	}
	frm.action="/pro/update";
	frm.submit();
	
	
})

$.ajax({
	type:"get",
	url:"/pro/clist.json",
	data:{pcode:pcode},
	dataType:"json"
}).done(function(data){
        let source=$("#tbl_Course_Temp").html();
        let temp=Handlebars.compile(source);
        let html=temp(data);
        $("#tbl_Course").html(html);
      
		if(data.length==0){
			 $("#tbl_Course").append("<tr><td colspan=7 class='none'>맡고 있는 수업이 없습니다!</td></tr>");
		}
})

$.ajax({
	type:"get",
	url:"/pro/slist.json",
	data:{pcode:pcode},
	dataType:"json"
}).done(function(data){
        let source=$("#tbl_Student_Temp").html();
        let temp=Handlebars.compile(source);
        let html=temp(data);
        $("#tbl_Student").html(html);
      
		if(data.length==0){
			 $("#tbl_Student").append("<tr><td colspan=7 class='none'>담당 학생이 없습니다!</td></tr>");
		}
})

$("#delete").on("click",()=>{
	$.ajax({
		type:"get",
		url:"/pro/count",
		data:{pcode},
		dataType:"json"
	}).done((data)=>{
		if(data.countAdvisor>0){
			alert("지도하는 학생이 있습니다.");
			return;
		}
		if(data.countInstructor>0){
			alert("강좌를 맡고 있습니다.");
			return;
		}
		if(!confirm("정말 삭제하시겠습니까?")){
			return;
		}
		
			$.ajax({
				type:"post",
				url:"/pro/delete",
				data:{pcode:pcode}
			}).done(()=>{
				location.href='/pro/list'
			})
	
	})
	})





</script>







