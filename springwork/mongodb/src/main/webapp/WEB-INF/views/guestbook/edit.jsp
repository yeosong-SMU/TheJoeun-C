<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
function edit() {
	document.form1.action="/guestbook/update.do";
	document.form1.submit();
}

function del() {
	if(confirm("삭제하시겠습니까?")) {
		document.form1.action="/guestbook/delete.do";
		document.form1.submit();
	}
}
</script>
</head>
<body>
<%@ include file="../include/menu.jsp" %>
<form name="form1" method="post">
	<table border="1" style="width:500px">
		<tr>
			<td>이름</td>
			<td><input type="text" name="name" value="${dto.name}"></td>
		</tr>
		<tr>
			<td>이메일</td>
			<td><input type="text" name="email" value="${dto.email}"></td>
		</tr>
		<tr>
			<td colspan="2"><textarea rows="5" cols="55" name="contents">${dto.contents}</textarea></td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="hidden" name="_id" value="${dto._id}">
				<input type="button" value="수정" onclick="edit()">
				<input type="button" value="삭제" onclick="del()">
				<input type="button" value="목록" onclick="location.href='/guestbook/'">
			</td>
		</tr>
	</table>
</form>
</body>
</html>