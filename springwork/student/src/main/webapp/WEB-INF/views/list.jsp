<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>학생명부</title>
</head>
<body>
<h2>학생명부</h2>

전공:
<select name="majorno" onchange="location.href='list.do?majorno=' + this.value">
	<option value="0" ${majorno == '0' ? 'selected' : ''}>전체학과</option>
	<c:forEach var="m" items="${list_major}">
		<option value="${m.majorno}" ${m.majorno == majorno ? 'selected' : ''}>${m.mname}</option>
	</c:forEach>
</select>

<br>
학생수 : ${items.size()}명
<button type="button" onclick="location.href='form.do'">추가</button>

<table border="1" width="500px">
	<tr align="center">
		<td>학번</td>
		<td>이름</td>
		<td>전공</td>
		<td>학년</td>
		<td>지도교수</td>
	</tr>
	<c:forEach var="dto" items="${items}">
		<tr align="center">
			<td>${dto.studno}</td>
			<td><a href="detail.do?studno=${dto.studno}">${dto.sname}</a></td>
			<td>${dto.mname}</td>
			<td>${dto.grade}</td>
			<td>${dto.pname}</td>
		</tr>
	</c:forEach>
</table>
</body>
</html>