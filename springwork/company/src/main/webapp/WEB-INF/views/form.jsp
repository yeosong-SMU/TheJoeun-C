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
<h2>사원등록</h2>
<form action="insert.do" method="post">
	<input type="text" name="empno" placeholder="사번" autofocus><br>
	<input type="text" name="ename" placeholder="이름"><br>
	<input type="text" name="job" placeholder="직급"><br>
	<input type="date" name="hiredate" placeholder="입사일자"><br>
	<input type="number" name="sal" placeholder="급여"><br>
	<select name="deptno">
		<option value="10">교육팀</option>
		<option value="20">홍보팀</option>
		<option value="30">기획팀</option>
		<option value="40">전산팀</option>
	</select>
	<button type="submit">저장</button>
</form>
</body>
</html>