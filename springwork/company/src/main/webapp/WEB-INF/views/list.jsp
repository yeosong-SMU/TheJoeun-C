<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원명부</title>
</head>
<body>
<h2>사원명부</h2>
<button type="button" onclick="location.href='form.do'">추가</button>
<table border="1" width="500px">
	<tr align="center">
		<th>사번</th>
		<th>이름</th>
		<th>직급</th>
		<th>입사일자</th>
		<th>급여</th>
		<th>부서명</th>
	</tr>
	<c:forEach var="dto" items="${items}">
		<tr align="center">
			<td>${dto.empno}</td>
			<td><a href="detail.do?empno=${dto.empno}">${dto.ename}</a></td>
			<td>${dto.job}</td>
			<td>${dto.hiredate}</td>
			<td>${dto.sal}</td>
			<td>${dto.dname}</td>
		</tr>
	</c:forEach>
</table>
</body>
</html>