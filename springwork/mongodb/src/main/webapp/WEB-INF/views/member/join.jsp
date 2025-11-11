<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<%@ include file="../include/menu.jsp" %>
<form method="post" action="/member/insert.do">
	아이디: <input name="_id"><br>
	비번: <input type="password" name="passwd"><br>
	이름: <input name="name"><br>
	<input type="submit" value="확인">
</form>
</body>
</html>