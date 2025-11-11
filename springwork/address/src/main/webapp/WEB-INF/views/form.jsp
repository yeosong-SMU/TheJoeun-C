<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>주소록</h2>
<form action="insert.do" method="post">
	<input type="text" name="name" placeholder="Name" autofocus><br>
	<input type="text" name="address" placeholder="Address"><br>
	<input type="text" name="phone" placeholder="Phone"><br>
	<button type="submit">저장</button>
</form>
</body>
</html>