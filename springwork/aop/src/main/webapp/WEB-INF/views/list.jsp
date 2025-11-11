<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form name="form1" method="post" action="insert.do">
	sender
	<select name="sender">
		<c:forEach var="row" items="${map.user}">
			<option>${row.userid}</option>
		</c:forEach>
	</select>
	<br>
	receive_id
	<select name="receive_id">
		<c:forEach var="row" items="${map.user}">
			<option>${row.userid}</option>
		</c:forEach>
	</select>
	<br>
	<input type="text" name="message" placeholder="message">
	<input type="submit" value="저장 및 조회">
</form>
<table border="1" width="700px">
	<tr>
		<td>idx</td>
		<td>sender</td>
		<td>receive_id</td>
		<td>message</td>
		<td>open_date</td>
		<td>send_date</td>
	</tr>
	<c:forEach var="row" items="${map.message}">
		<tr>
			<td>${row.idx}</td>
			<td>${row.sender} (${row.sender_point})</td>
			<td>${row.receive_id} (${row.receive_point})</td>
			<td>
				<input type="button" value="읽음" onclick="location.href='read.do?userid=${row.receive_id}&idx=${row.idx}'"> ${row.message}
			</td>
			<td><fmt:formatDate value="${row.open_date}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			<td><fmt:formatDate value="${row.send_date}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
	</c:forEach>
</table>
</body>
</html>