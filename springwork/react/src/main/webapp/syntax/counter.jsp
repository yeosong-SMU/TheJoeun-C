<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="../include/header.jsp" %>
<script type="text/babel">
function Counter({start}) {
	let [count, setCount] = React.useState(start);
	function increase() {
		setCount(count + 1);
	}

	function decrease() {
		setCount(count - 1);
	}
	return (
		<div align="center">
			<h1>카운트 : {count}</h1>
			<button onClick={decrease}>-</button>
			<button onClick={() => setCount(0)}>Reset</button>
			<button onClick={increase}>+</button>
		</div>
	);
}
function App() {
	return (
		<div>
			<Counter start={0} />
			<Counter start={123} />
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