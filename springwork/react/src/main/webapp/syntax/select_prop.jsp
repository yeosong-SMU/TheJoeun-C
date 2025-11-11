<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="../include/header.jsp" %>
<script type="text/babel">
function Select(props) {
	return (
		<select>
			{props.items.map((item) => (
				<option key={item}>{item}</option>
			))}
		</select>
	);
}
function App() {
	const items = ["apple", "peach", "melon"];
	return <Select items={items} />;
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