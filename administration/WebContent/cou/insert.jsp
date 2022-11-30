<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<h1>강좌등록</h1>


<form name=frm method=post>
	<table>
		<tr>
			<td>강좌번호</td>
			<td><input type=text name=lcode value="${code}" readonly></td>
		</tr>
		<tr>
			<td>강의이름</td>
			<td><input type=text name=lname size=50></td>
		</tr>
		<tr>
			<td>담당교수</td>
			<td><select name=instructor id=plist></select></td>

		</tr>

		<tr>
			<td>강좌시수</td>
			<td><input type=radio name=hours value=1>1학점&nbsp;&nbsp;
				<input type=radio name=hours value=2 checked>2학점&nbsp;&nbsp;
				<input type=radio name=hours value=3>3학점&nbsp;&nbsp;</td>
		</tr>

		<tr>
			<td>강의실</td>
			<td><input type=text name=room size=5> 호</td>
		</tr>

		<tr>
			<td>수강최대인원</td>
			<td><input type=number name=capacity size=3> 명</td>
		</tr>
	</table>
	<div class=buttons>
		<button id=buttons>등록</button>
		<button type=reset>취소</button>
	</div>
</form>

<script id="tbl_Temp" type="text/x-handlebars-template">
	{{#each array}}
		<option value="{{pcode}}"}}>{{pname}}({{dept}})</option>
	{{/each}}
	
</script>


<script>

$.ajax({
   type:"get",
   url:"/pro/list.json",
   dataType:"json",
   data:{key:"pcode",word:"",per:100,order:"pcode",
            desc:"asc",page:1},
}).done(function(data){
      console.log(JSON.stringify(data));
      var temp=Handlebars.compile($("#tbl_Temp").html());
      $("#plist").html(temp(data));
      
      $(frm).on("submit", function() {
    		e.preventDefault();
    		

    		if (!confirm("새로운 강의를 등록하실래요?")) {
    			return;
    		}
    		frm.submit();

	})
})
    
     

</script>