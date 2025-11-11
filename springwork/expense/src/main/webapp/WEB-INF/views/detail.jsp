<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
	function delete_expense(idx) {
		if(confirm("삭제하시겠습니까?")) {
			location.href = "delete.do?idx=" + idx;
		}
	}
</script>
</head>
<body>
<%
String[] category = {"식비", "교통비", "쇼핑", "문화생활", "기타"};
%>
<form action="update.do?idx=${dto.idx}" method="post">
	분류 :
	<select name="category">
		<c:forEach var="cat" items="<%=category%>">
			<option value="${cat}" ${cat == dto.category ? 'selected' : ''}>${cat}</option>
		</c:forEach>
	</select>
	<br>
	설명 : <input name="description" value="${dto.description}"><br>
	금액 : <input type="number" name="amount" value="${dto.amount}"><br>
	일자 : <input type="date" name="reg_date" value="${dto.reg_date}"><br>
	<button>수정</button>
	<button type="button" onclick="delete_expense('${dto.idx}')">삭제</button>
	<button type="button" onclick="location.href='list.do'">목록</button>
</form>
</body>
</html>