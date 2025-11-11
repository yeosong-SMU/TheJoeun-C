<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>회원가입</h2>
<form action="/user/insert.do" method="post">
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
			<td>이름</td>
			<td><input type="text" name="name"></td>
		</tr>
		<tr>
			<td>사용권한</td>
			<td>
				<select name="authority">
					<option value="ROLE_USER">일반사용자</option>
					<option value="ROLE_ADMIN">관리자</option>
				</select>
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<input type="submit" value="회원가입">
			</td>
		</tr>
	</table>
</form>
</body>
</html>