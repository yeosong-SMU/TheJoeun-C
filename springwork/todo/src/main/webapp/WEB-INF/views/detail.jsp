<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script>
	function update_todo() {
		document.form1.action="/update.do";
		document.form1.submit();
	}
	function delete_todo() {
		if(confirm("삭제하시겠습니까?")) {
			document.form1.action="/delete.do";
			document.form1.submit();
		}
	}
</script>
</head>
<body>
<h2 class="text-center">할일 등록</h2>
<form name="form1" method="post">
	<input type="text" name="title" placeholder="제목" value="${dto.title}" required="required">
	<br>
	<textarea rows="5" cols="60" name="description" required="required" placeholder="내용">${dto.description}</textarea>
	<br>
	상태 : 
	<input type="radio" name="done" value="0" <c:if test="${dto.done == 0}">checked</c:if>> 진행
	<input type="radio" name="done" value="1" <c:if test="${dto.done == 1}">checked</c:if>> 완료
	<br>
	우선순위 : 
	<input type="radio" name="priority" value="1" <c:if test="${dto.priority == 1}">checked</c:if>> High
	<input type="radio" name="priority" value="2" <c:if test="${dto.priority == 2}">checked</c:if>> Medium
	<input type="radio" name="priority" value="3" <c:if test="${dto.priority == 3}">checked</c:if>> Low
	<br>
	마감일자 : 
	<input type="date" name="target_date" value="${dto.target_date}" required="required">
	<br>
	<input type="hidden" name="idx" value="${dto.idx}">
	<input type="button" value="수정" onclick="update_todo()">
	<input type="button" value="삭제" onclick="delete_todo()">
	<input type="button" value="목록" onclick="location.href='list.do'">
</form>
</body>
</html>