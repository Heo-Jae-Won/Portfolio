<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<h1>강좌정보</h1>
<style>
.none{
color:red;
}

</style>

<form>
	<table>
		<tr>
			<td width=120>강좌번호</td>
			<td><input type=text name=lcode value="${couvo.lcode}"></td>
			<td width=150>강의실</td>
			<td><input type=text name=room value="${couvo.room}" size=20></td>
			<td width=130>강의실</td>
			<td><input type=text name=lname value="${couvo.lname}"></td>
			<td width=200>수강신청인원</td>
			<td><input type=text name=persons value="${couvo.persons}"></td>
		
		</tr>

		<tr>
			<td>강의이름</td>
			<td><input type=text name=lcode value="${couvo.lname}"></td>
			<td>최대수강인원</td>
			<td><input type=text name=capacity value="${couvo.capacity}"></td>
			<td>강의시수</td>
			<td><input type=text name=hours value="${couvo.hours}"></td>
			<td>담당교수</td>
			<td colspan=3><input type=text name=instrcutor
				value="${couvo.instructor}(${couvo.pname})"></td>
		</tr>



	</table>
</form>
<button type=submit id=update>선택수정</button>
<table id=tbl_slist></table>
<script id="tbl_slist_Temp" type="text/x-handlebars-template">

	<tr class=title>
		<td width=100><input type=checkbox id=all></td>
		<td>학생번호</td>
		<td width=100>학생이름</td>
		<td width=100>학생학과</td>
		<td width=100>학생학년</td>
		<td width=100>수강신청일</td>
		<td width=100>점수</td>
	</tr>
{{#each .}}
	<tr class=row>
		<td><input type=checkbox class=chk></td>
		<td class=scode>{{scode}}</td>
		<td>{{sname}}</td>
		<td>{{dept}}</td>
		<td>{{year}}</td>
		<td>{{edate}}</td>
		<td><input type=number class=grade value={{grade}}>
		</td>
	</tr>
{{/each}}
</script>

<script>
let lcode="${couvo.lcode}";

//
const getList=()=>$.ajax({
	type:"get",
	url:"/enroll/slist.json",
	data:{lcode},
	dataType:"json"
}).done((data)=>{
    let source=$("#tbl_slist_Temp").html();
    let temp=Handlebars.compile(source);
    let html=temp(data);
    $("#tbl_slist").html(html);
    if(data.length===0){
    	$("#tbl_slist").append("<tr class=none><td colspan=7>검색된 자료가 없습니다.</td></tr>")
    }

	
})
getList();


$("#update").on("click",()=>{
	let chk=$("#tbl_slist .row .chk:checked").length
	

	if(chk===0){
		alert("수정할 학생들을 선택하세요!");
		return;
	}
	if(!confirm(chk+"명의 점수를 수정하시겠습니까?")){ 
		return;
	}
	$("#tbl_slist .row .chk:checked").each(function(){
		let row=$(this).parent().parent();
		let scode=row.find(".scode").html();
		let grade=row.find(".grade").val();
	
		$.ajax({
			type:"post",
			data:{scode,grade,lcode},
			url:"/enroll/update",
		}).done(()=>{
			
			
		})
		alert(chk+"명 update에 성공하였습니다.")
	})
	
})

$("#tbl_slist").on("click",".title #all",function(){
	$(this).is(":checked") ? $("#tbl_slist .chk").prop("checked",true) : $("#tbl_slist .chk").prop("checked",false)
	
})

$("#tbl_slist").on("click",".chk",function(){
	let chk=$("#tbl_slist .chk:checked").length
	let all=$("#tbl_slist .chk").length
	
	all===chk ? $("#tbl_slist #all").prop("checked",true) : $("#tbl_slist #all").prop("checked",false)
	
})



</script>