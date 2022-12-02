<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<h1>학생등록</h1>


<form name=frm method=post>
	<table>
		<tr>
			<td>학생번호</td>
			<td><input type=text name=scode value="${code}" readonly></td>
		</tr>
		<tr>
			<td>학생이름</td>
			<td><input type=text name=sname></td>
		</tr>
		<tr>
			<td>학생학과</td>
			<td><select name=dept>
					<option value=전산>컴퓨터정보공학과</option>
					<option value=전자>전자공학과</option>
					<option value=건축>건축토목공학과</option>
			</select></td>
		</tr>

		<tr>
			<td>학생학년</td>
			<td><input type=radio name=year value=1>1학년&nbsp;&nbsp;
				<input type=radio name=year value=2 checked>2학년&nbsp;&nbsp;
				<input type=radio name=year value=3>3학년&nbsp;&nbsp;</td>
		</tr>

		<tr>
			<td>생년월일</td>
			<td><input type=date name=birthday value="${birthday}"></td>
		</tr>

		<tr>
			<td>지도교수</td>
			<td><select name=advisor id=advisor></select></td>
			<!-- 열었따 바로 닫았으니 이건 핸들바 템플릿 장전한것. -->
		</tr>
	</table>
	<button id=buttons>학생등록</button>
	<button type=reset>취소</button>
</form>



<script id="tbl_Temp" type="text/x-handlebars-template">
	{{#each array}}
		<option value="{{pcode}}">{{pname}}({{dept}})</option>
	{{/each}}
</script>


<script>
changeDept();




function changeDept(){
	
	
		let dept=$(frm.dept).val();
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
		}).done(function(data){
			 let source=$("#tbl_Temp").html();
		        let temp=Handlebars.compile(source);
		        let html=temp(data);
		        $("#advisor").html(html);

		})
		
		
	}
	
	
	
	
	
	$(frm.dept).on("change",function(){
		changeDept();
		
		
})
	

	$(frm).on("submit", function() {
		e.preventDefault();
		let sname=$(frm.sname).val();
		
		
		if(sname==""){
			alert("교수이름을 입력하세요.")
			$(frm.sname).focus();
			return;
		}
		
		if(!confirm("새로운 교수를 등록하실래요?")){
			return;
		}
		frm.submit();
		
	})
</script>