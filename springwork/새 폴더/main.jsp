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
<%@ include file="include/menu.jsp" %>
<c:if test="${sessionScope.userid != null}">
	<h2>
		${sessionScope.name} ( ${sessionScope.userid} )님의 방문을 환영합니다.
	</h2>
</c:if>
<c:otherwise>
	<h2>
		환영합니다. 로그인 후 이용가능합니다.
	</h2>
</c:otherwise>
</body>
</html>