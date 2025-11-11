<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script>
$(function() {
	$("#btnAdd").click(function() {
		location.href="/shop/product/write.do";
	});
});
</script>
</head>
<body>
<%@ include file="../include/menu.jsp" %>
<h2>상품목록</h2>
<c:if test="${sessionScope.admin_userid != null}">
	<button type="button" id="btnAdd">상품등록</button>
</c:if>
<table border="1" width="500px">
	<tr>
		<th>상품ID</th>
		<th> </th>
		<th>상품명</th>
		<th>가격</th>
	</tr>
	<c:forEach var="row" items="${list}">
	<tr>
		<td>${row.product_code}</td>
		<td align="center"><img src="/resources/images/${row.filename}" width="100px" height="100px"></td>
		<td><a href="/shop/product/detail/${row.product_code}">${row.product_name}</a>
			<c:if test="${sessionScope.admin_userid != null}">
				<br>
				<a href="/shop/product/edit/${row.product_code}">[편집]</a>
			</c:if>
		</td>
		<td><fmt:formatNumber value="${row.price}" pattern="#,###" />
	</tr>
	</c:forEach>
</table>
</body>
</html>