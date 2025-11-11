<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script>
function delete_address(idx) {
	if(confirm("삭제하시겠습니까?")) {
		location.href = "delete.do?idx=" + idx;
	}
}
</script>
</head>
<body>
<form action="update.do?idx=${dto.idx}" method="post">
	<input type="text" name="name" value="${dto.name}" placeholder="Name" autofocus><br>
	<input type="text" name="address" value="${dto.address}" placeholder="Address"><br>
	<input type="text" name="phone" value="${dto.phone}" placeholder="Phone"><br>
	<button type="submit">저장</button>
	<button type="button" onclick="delete_address('${dto.idx}')">삭제</button>
	<button type="button" onclick="location.href='/'">목록</button>
</form>
</body>
</html>