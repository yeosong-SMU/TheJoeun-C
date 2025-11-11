<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="../include/header.jsp" %>
<script type="text/babel">
function Select() {
	const [value, setValue] = React.useState(0);
	const onChange = (e) => setValue((item) => item + parseInt(e.target.value));
	const items = [1, 2, 3, 4, 5];
	return (
		<section>
			<h1>Value: {value}</h1>
			<select onChange={onChange}>
				{items.map((item) => (
					<option key={item} value={item}>
						{item}
					</option>
				))}
			</select>
		</section>
	);
}
function App() {
	return <Select />;
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