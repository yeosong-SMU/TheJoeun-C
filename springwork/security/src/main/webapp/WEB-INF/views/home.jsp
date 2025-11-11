<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Home</title>
</head>
<body>
<h2>${message}</h2>
<sec:authorize access="hasRole('ROLE_ADMIN')">
	<a href="/admin/">관리자 페이지</a>
	<br>
</sec:authorize>

<sec:authorize access="isAnonymous()">
	<a href="/user/login.do">로그인</a>
</sec:authorize>

<sec:authorize access="isAuthenticated()">
	<a href="/user/logout.do">로그아웃</a>
</sec:authorize>
</body>
</html>