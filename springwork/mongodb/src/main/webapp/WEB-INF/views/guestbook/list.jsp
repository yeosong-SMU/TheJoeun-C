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
<br>게시물수: ${map.count}건
<c:forEach var="dto" items="${map.list}">
	<table border="1" style="width:600px">
		<tr align="center">
			<td width="20%">이름</td>
			<td width="30%">${dto.name}</td>
			<td width="20%">날짜</td>
			<td width="30%"><fmt:formatDate value="${dto.post_date}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
		</tr>
		<tr>
			<td align="center">이메일</td>
			<td colspan="3">${dto.email}</td>
		</tr>
		<tr>
			<td colspan="4">
				<a href="/guestbook/edit.do?_id=${dto._id}">${dto.contents}</a>
			</td>
		</tr>
	</table>
</c:forEach>
</body>
</html>