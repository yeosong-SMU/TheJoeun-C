<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<a href="/">메인화면</a> |
<a href="#">설화 지도</a> |
<a href="#">보따리 상점</a> |
<a href="#">기부하기</a> |

<div style="text-align:right;">
	<c:choose>
		<c:when test="${sessionScope.userid == null}">
			<a href="/member/login.do">로그인</a> |
		</c:when>
		<c:otherwise>
			${sessionScope.name}님이 로그인중입니다.
			<a href="/member/logout.do">로그아웃</a> |
		</c:otherwise>
	</c:choose>
</div>
<hr>