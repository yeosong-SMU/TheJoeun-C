<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="../include/header.jsp" %>
<script type="text/babel">
function Item() {
	const items = [
		{name: '사과', price: 5000},
		{name: '포도', price: 4000},
		{name: '망고', price: 3000},
	];
	return (
		<div align="center">
			{items.map(item =>
			<div>
				<h2>상품이름: {item.name}</h2>
				<h2>상품가격: {item.price}</h2>
			</div>
			)}
		</div>
	);
}
function App() {
	return (
		<div align="center">
			<h2>상품목록</h2>
			<Item></Item>
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