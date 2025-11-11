<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>학생 등록</title>
</head>
<body>
<h2>학생등록</h2>

<form action="insert.do" method="post">
	학번: <input name="studno"><br>
	이름: <input name="sname"><br>
	학년: <input name="grade"><br>
	
	전공: 
	<select name="majorno">
		<c:forEach var="m" items="${list_major}">
			<option value="${m.majorno}">${m.mname}</option>
		</c:forEach>
	</select>
	<br>
	
	지도교수: 
	<select name="profno">
		<c:forEach var="p" items="${list_prof}">
			<option value="${p.profno}">${p.pname}</option>
		</c:forEach>
	</select>
	<br>
	
	<button>저장</button>
</form>
</body>
</html>