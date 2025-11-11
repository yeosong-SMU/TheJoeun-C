<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>학생 정보 수정</title>
<script>
	function delete_student(studno) {
		if(confirm("삭제하시겠습니까?")) {
			location.href = "delete.do?studno=" + studno;
		}
	}
</script>
</head>
<body>

<h2>학생 정보 수정</h2>

<form action="update.do?studno=${dto.studno}" method="post">
	학번: <input name="studno" value="${dto.studno}" disabled><br>
	이름: <input name="sname" value="${dto.sname}"><br>
	
	전공: 
	<select name="majorno">
		<c:forEach var="m" items="${list_major}">
			<option value="${m.majorno}" <c:if test="${m.majorno == dto.majorno}">selected</c:if> >${m.mname}</option>
		</c:forEach>
	</select>
	<br>
	
	학년: <input name="grade" value="${dto.grade}"><br>
	
	지도교수: 
	<select name="profno">
		<c:forEach var="p" items="${list_prof}">
			<option value="${p.profno}" <c:if test="${p.profno == dto.profno}">selected</c:if> >${p.pname}</option>
		</c:forEach>
	</select>
	<br>
	
	<button type="submit">수정</button>
	<button type="button" onclick="delete_student('${dto.studno}')">삭제</button>
	<button type="button" onclick="location.href='list.do'">목록</button>
</form>
</body>
</html>