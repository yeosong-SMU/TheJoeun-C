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
<form method="post" action="/guestbook/insert.do">
	<table border="1" style="width:500px">
		<tr>
			<td>이름</td>
			<td><input type="text" name="name"></td>
		</tr>
		<tr>
			<td>이메일</td>
			<td><input type="text" name="email"></td>
		</tr>
		<tr>
			<td colspan="2"><textarea rows="5" cols="55" name="contents"></textarea></td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="submit" value="확인">
			</td>
		</tr>
	</table>
</form>
</body>
</html>