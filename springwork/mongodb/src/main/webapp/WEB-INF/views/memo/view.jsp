<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script>
$(function(){
	$("#btn_update").click(function() {
		$("#form1").attr("action", "/memo/update.do");
		$("#form1").submit();
	});
	
	$("#btn_delete").click(function() {
		if(confirm("삭제하시겠습니까?")) {
			$("#form1").attr("action", "/memo/delete.do");
			$("#form1").submit();
		}
	});
});
</script>
</head>
<body>
<%@ include file="../include/menu.jsp" %>
<h2>메모장</h2>
<form id="form1" name="form1" method="post">
	<input type="hidden" name="_id" value="${dto._id}">
	이름: <input type="text" name="writer" value="${dto.writer}"><br>
	메모: <input type="text" name="memo" value="${dto.memo}" size="50">
	<br>
	<input type="button" id="btn_update" value="수정">
	<input type="button" id="btn_delete" value="삭제">
	<input type="button" id="btn_list" value="목록" onclick="location.href='/memo/'">
</form>
</body>
</html>