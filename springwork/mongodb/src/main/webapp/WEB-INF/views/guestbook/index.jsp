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
});

function list() {
	$.ajax({
		url: "/guestbook/list.do",
		success: function(txt) {
			console.log(txt);
			$("#div1").html(txt);
		}
	});
}

function gb_write() {
	location.href="/guestbook/write.do";
}
</script>
</head>
<body>
<%@ include file="../include/menu.jsp" %>
<h2>방명록</h2>
<input type="button" value="글쓰기" onclick="gb_write()">
<div id="div1"></div>
</body>
</html>