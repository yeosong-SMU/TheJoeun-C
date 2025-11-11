<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<a href="/">Home</a> |
<a href="/upload/ajax_form">파일업로드</a> |
<a href="/board/list.do">게시판</a> |
<c:choose>
	<c:when test="${sessionScope.userid == null}">
		<a href="/member/login.do">로그인</a> |
	</c:when>
	<c:otherwise>
		${sessionScope.name}님이 로그인중입니다.
		<a href="/member/logout.do">로그아웃</a>
	</c:otherwise>
</c:choose>
<hr>