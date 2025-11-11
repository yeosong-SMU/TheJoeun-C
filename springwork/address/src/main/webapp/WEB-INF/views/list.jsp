<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>주소록</h2>
<button type="button" onclick="location.href='form.do'">추가</button>
<table border="1" width="500px">
	<tr align="center">
		<td>No</td>
		<td>이름</td>
		<td>주소</td>
		<td>전화</td>
	</tr>
	<c:forEach var="dto" items="${items}">
		<tr align="center">
			<td>${dto.idx}</td>
			<td><a href="detail.do?idx=${dto.idx}">${dto.name}</a></td>
			<td>${dto.address}</td>
			<td>${dto.phone}</td>
		</tr>
	</c:forEach>
</table>
</body>
</html>