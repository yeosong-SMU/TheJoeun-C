<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
	function memo_view(idx) {
		location.href = "/view/" + idx;
	}
</script>
</head>
<body>
	<form method="post" action="/insert.do">
		이름: 
		<input name="writer" size="10">
		메모: 
		<input name="memo" size="40">
		<input type="submit" value="확인">
	</form>
	<table border="1" style="width: 500px;">
		<tr>
			<th>번호</th>
			<th>이름</th>
			<th>메모</th>
			<th>날짜</th>
		</tr>
		<c:forEach var="row" items="${list}">
		<tr>
			<td>${row.idx}</td>
			<td>${row.writer}</td>
			<td><a href="#" onclick="memo_view('${row.idx}')">${row.memo}</a></td>
			<td><fmt:formatDate value="${row.post_date}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
		</tr>
		</c:forEach>
	</table>
</body>
</html>