<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
iframe {
	width: 400px;
	height: 200px;
	border: 1px;
	border-style: solid;
	border: 1px;
}
</style>
</head>
<body>
<%@ include file="../include/menu.jsp" %>
<form action="/upload/upload.do" method="post" enctype="multipart/form-data" target="iframe1">
	<input type="file" name="file">
	<input type="submit" value="업로드">
</form>
<iframe name="iframe1"></iframe>
</body>
</html>