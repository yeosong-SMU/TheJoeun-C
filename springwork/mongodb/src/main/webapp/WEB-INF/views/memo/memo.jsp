<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script>
$(function() {
	list();
	$("#btn_write").click(function() {
		insert();
	});
});

function list(){
	$.ajax({
		url: "/memo/list.do",
		success: function(txt){
			$("#div1").html(txt);
		}
	});
}

function insert() {
	const writer=$("#writer").val();
	const memo=$("#memo").val();
	const params={"writer":writer, "memo":memo};
	$.ajax({
		type: "post",
		data: params,
		url: "/memo/insert.do",
		success: function() {
			list();
			$("#writer").val("");
			$("#memo").val("");
		}
	});
}

function view(num) {
	$(location).attr("href", "/memo/view.do?_id=" + num);
}
</script>
</head>
<body>
<%@ include file="../include/menu.jsp" %>
<h2>한줄메모장</h2>
이름: <input type="text" id="writer">
메모: <input type="text" id="memo" size="50">
<input type="button" id="btn_write" value="확인">
<div id="div1"></div>
</body>
</html>