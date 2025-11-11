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
<h2>가계부</h2>
<form action="insert.do" method="post">
	분류 : 
	<select name="category">
		<option value="식비">식비</option>
		<option value="교통비">교통비</option>
		<option value="쇼핑">쇼핑</option>
		<option value="문화생활">문화생활</option>
		<option value="기타">기타</option>
	</select>
	설명 : <input name="description">
	<br>
	금액 : <input type="number" name="amount">
	일자 : <input type="date" name="reg_date" required>
	<button type="submit">추가</button>
</form>

<table border="1">
	<tr>
		<th>날짜</th>
		<th>설명</th>
		<th>금액</th>
		<th>카테고리</th>
	</tr>
	<c:forEach var="dto" items="${items}">
		<tr align="center">
			<td>${dto.reg_date}</td>
			<td><a href="detail.do?idx=${dto.idx}">${dto.description}</a></td>
			<td>${dto.amount}</td>
			<td>${dto.category}</td>
		</tr>
	</c:forEach>
</table>
</body>
</html>