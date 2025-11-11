<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="../include/header.jsp" %>
<script type="text/babel">
function Item(props) {
	return (
		<ol>
			{props.list.map(({ product_name, description }) => (
				<li key={product_name}>
					{product_name}<br />
					{description}
				</li>
			))}
		</ol>
	);
}
function App() {
	const items = [
		{product_name: "apple", description: "신선한 사과예요."},
		{product_name: "orange", description: "맛있는 오렌지예요."},
		{product_name: "grape", description: "달콤한 포도예요."},
	];
	return <Item list={items} />;
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