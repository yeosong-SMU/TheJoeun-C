<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="../include/header.jsp" %>
<script type="text/babel">
function App() {
	let name = '김철수';
	let message = '안녕하세요';
	return (
		<div align="center">
			<h2>{name}님</h2>
			<h2>{message}</h2>
		</div>
	);
}
ReactDOM.render(
	<App />,
	document.getElementById("root")
);
</script>
</head>
<body>
<div id="root"></div>
</body>
</html>