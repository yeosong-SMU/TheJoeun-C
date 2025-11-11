<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
function join() {
	location.href="/user/join.do";
}
</script>
</head>
<body>
<h2>로그인 하세요</h2>
<span style="color:red">${message}</span>
<form action="/user/login_check.do" method="post">
	<table>
		<tr>
			<td>아이디</td>
			<td><input type="text" name="userid"></td>
		</tr>
		<tr>
			<td>비밀번호</td>
			<td><input type="password" name="passwd"></td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<input type="submit" value="로그인">
				<input type="button" value="회원가입" onclick="join()">
			</td>
		</tr>
	</table>
</form>
</body>
</html>