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
	let arr = [2, 4, 6, 8];
	let new_arr1 = [];
	let new_arr2 = [];

	for(var i = 0; i < arr.length; i++) {
		new_arr1.push(arr[i])
	}
	const a = JSON.stringify(new_arr1);

	arr.forEach((value) => {
		new_arr2.push(value)
	})
	const b=JSON.stringify(new_arr2);

	return (
		<div align="center">
			<h2>array</h2>
			<h2>{a}</h2>
			<h2>{b}</h2>
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