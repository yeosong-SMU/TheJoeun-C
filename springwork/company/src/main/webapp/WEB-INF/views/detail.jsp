<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>사원정보 수정</title>
<script>
	function delete_emp(empno) {
		if(confirm("삭제하시겠습니까?")) {
			location.href = "delete.do?empno=" + empno;
		}
	}
</script>
</head>
<body>
<form action="update.do?empno=${dto.empno}" method="post">
	사번: <input type="text" name="empno" value="${dto.empno}" disabled><br>
	이름: <input type="text" name="ename" value="${dto.ename}"><br>
	직급: <input type="text" name="job" value="${dto.job}"><br>
	입사일자: <input type="date" name="hiredate" value="${dto.hiredate}"><br>
	급여: <input type="number" name="sal" value="${dto.sal}"><br>
	부서: 
	<select name="deptno">
		<option value="10" <c:if test="${dto.deptno == 10}">selected</c:if> >교육팀</option>
		<option value="20" <c:if test="${dto.deptno == 20}">selected</c:if> >홍보팀</option>
		<option value="30" <c:if test="${dto.deptno == 30}">selected</c:if> >기획팀</option>
		<option value="40" <c:if test="${dto.deptno == 40}">selected</c:if> >전산팀</option>
	</select>
	<br><br>
	
	<button type="submit">수정</button>
	<button type="button" onclick="delete_emp('${dto.empno}')">삭제</button>
	<button type="button" onclick="location.href='list.do'">목록</button>
</form>
</body>
</html>