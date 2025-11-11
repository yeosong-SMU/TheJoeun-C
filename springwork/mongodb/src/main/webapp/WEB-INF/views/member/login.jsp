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
	$("#btnLogin").click(function() {
		$("#form1").attr("action", "/member/login_check.do");
		$("#form1").submit();
	});
	$("#btnJoin").click(function() {
		$(location).attr("href", "/member/join.do");
	});
});
</script>
</head>
<body>
<%@ include file="../include/menu.jsp" %>
<h2>로그인하세요</h2>
<form id="form1" method="post">
	<table border="1" style="width:400px">
		<tr>
			<td>아이디</td>
			<td><input type="text" name="_id" id="_id"></td>
		</tr>
		<tr>
			<td>비밀번호</td>
			<td><input type="password" name="passwd" id="passwd"><br>
				<span style="color:red;">${map.message}</span>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<input type="button" id="btnLogin" value="로그인">
				<input type="button" id="btnJoin" value="회원가입">
			</td>
		</tr>
	</table>
</form>
</body>
</html>