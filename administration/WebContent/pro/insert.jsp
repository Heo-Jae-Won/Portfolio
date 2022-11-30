<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<h1>교수등록</h1>


<form name=frm method=post>
	<table>
		<tr>
			<td>교수번호</td>
			<td><input type=text name=pcode value="${code}" readonly></td>
		</tr>
		<tr>
			<td>교수이름</td>
			<td><input type=text name=pname></td>
		</tr>
		<tr>
			<td>교수학과</td>
			<td><select name=dept>
					<option value=전산>컴퓨터정보공학과</option>
					<option value=전자>전자공학과</option>
					<option value=건축>건축토목공학과</option>
			</select></td>
		</tr>

		<tr>
			<td>교수직급</td>
			<td><input type=radio name=title value=정교수>정교수&nbsp;&nbsp;
				<input type=radio name=title value=부교수 checked>부교수&nbsp;&nbsp;
				<input type=radio name=title value=조교수>조교수&nbsp;&nbsp;</td>
		</tr>

		<tr>
			<td>임용일자</td>
			<td><input type=date name=hiredate value="${now}"></td>
		</tr>

		<tr>
			<td>급여</td>
			<td><input type=number name=salary></td>
		</tr>
	</table>
	<button id=buttons>등록</button>
	<button type=reset>취소</button>
</form>

<script>
	$(frm).on("submit", function() {
		e.preventDefault();
		let pname=$(frm.pname).val();
		
		
		if(pname==""){
			alert("교수이름을 입력하세요.")
			$(frm.pname).focus();
			return;
		}
		
		if(!confirm("새로운 교수를 등록하실래요?")){
			return;
		}
		frm.submit();
		
		
		
		
	})
</script>