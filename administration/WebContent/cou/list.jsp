<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<div style="padding-left:20px;">
   <form name="frm">
   	<a href="/cou/insert"><button type=button>강의등록</button></a>
      <select name="key" id="key">
         <option value="lcode">강좌번호</option>
         <option value="lname">강좌이름</option>
         <option value="pname">담당교수이름</option>
      </select>
      <input type="text" name="word" placeholder="검색어">
      <select name="per" id="per">
         <option value="2">2행</option>
         <option value="3" selected>3행</option>
         <option value="5">5행</option>
         <option value="10">10행</option>
      </select>
      <select name="order" id="order">
         <option value="lcode">강좌번호</option>
         <option value="lname">강좌이름</option>
         <option value="pname">교수이름</option>
      </select>
      <select name="desc" id="desc">
         <option value="asc">오름차순</option>
         <option value="desc">내림차순</option>
      </select>
      <button>검색</button>
      검색수:<span id="total"></span>
   </form>
</div>
<table id="tbl"></table>
<script id="temp" type="text/x-handlebars-template">
   <tr class="title">
      <td width=100>강좌번호</td>
      <td width=100>강좌이름</td>
      <td width=100>담당교수</td>
      <td width=100>강의실</td>
      <td width=200>수강인원</td>
      <td width=200>시수</td>
   </tr>
   {{#each array}}
   <tr onclick="location.href='/cou/read?lcode={{lcode}}'" class="row">
      <td>{{lcode}}</td>
      <td>{{lname}}</td>
      <td>{{pname}}({{dept}})</td>
      <td>{{room}}</td>
      <td>{{persons}}/{{capacity}}</td>
      <td>{{hours}}</td>
   </tr>
   {{/each}}
</script>
<div class="buttons">
   <button id="prev">이전</button>
   <span id="page">1/10</span>
   <button id="next">다음</button>
</div>

<script>
   let page=1;
   getList();
   
   $(frm).on("submit", function(e){
      e.preventDefault();
      page=1;
      getList();
   });
   
   $("#key, #per, #order, #desc").on("change", function(){
      page=1;
      getList();
   });
   
   $("#next").on("click", function(){
      page++;
      getList();
   });
   
   $("#prev").on("click", function(){
      page--;
      getList();
   });
   
   function getList(){
      var key=$(frm.key).val();
      var word=$(frm.word).val();
      var per=$(frm.per).val();
      var order=$(frm.order).val();
      var desc=$(frm.desc).val();
      $.ajax({
         type:"get",
         url:"/cou/list.json",
         dataType:"json",
         data:{key:key,word:word,per:per,order:order,
                  desc:desc,page:page},
      }).done(function(data){
            console.log(JSON.stringify(data));
            var temp=Handlebars.compile($("#temp").html());
            $("#tbl").html(temp(data));
            $("#total").html(data.total);
            
            if(data.total==0){
               $("#tbl").append("<tr><td colspan=6 class='none'>검색된 자료가 없습니다!</td></tr>");
               $(".buttons").hide();
            }else{
               $("#page").html(page + "/" + data.last);
               
             (page==1) ? $("#prev").attr("disabled", true) :  $("#prev").attr("disabled", false);
            (page==data.last) ? $("#next").attr("disabled", true) : $("#next").attr("disabled", false);
            
               $(".buttons").show();
            }
         })
      };
  
</script>