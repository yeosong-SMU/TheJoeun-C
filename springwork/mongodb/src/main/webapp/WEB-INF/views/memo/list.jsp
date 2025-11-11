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
<table border="1" style="width:700px">
	<tr>
		<td>No</td>
		<td>이름</td>
		<td>메모</td>
		<td>날짜</td>
	</tr>
	<c:forEach var="row" items="${map.items}" varStatus="status">
	<tr>
		<td>${status.count}</td>
		<td>${row.writer}</td>
		<td><a href="#" onclick="view('${row._id}')">${row.memo}</a></td>
		<td>
			<fmt:formatDate value="${row.post_date}" pattern="yyyy-MM-dd HH:mm:ss" />
		</td>
	</tr>
	</c:forEach>
</table>
</body>
</html>