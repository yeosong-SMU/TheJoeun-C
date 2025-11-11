<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>ToDoList</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script>
	function delete_todo(idx) {
		if(confirm("삭제하시겠습니까?")) {
			location.href = "delete.do?idx=" + idx;
		}
	}
	function change_status(idx, done) {
		status = done == "1" ? "0" : "1";
		$.ajax({
			type : "post",
			url : "/change_status.do",
			data : {
				"idx" : idx,
				"done" : status
			},
			success : function(txt) {
				if(txt == "success") {
					location.reload();
				}
			}
		});
	}
</script>
</head>
<body>
<h2>TODO List</h2>
<hr>
<form name="form1" method="post">
	제목:
	<input type="text" name="title" value="${map.title}">
	<input type="submit" value="조회">
	<button type="button" onclick="location.href='/form.do'">할 일 등록</button>
</form>
등록된 자료수: ${map.count}
<br>
<br>
<c:if test="${map.count > 0}">
	<table border="0" width="700px">
		<tr align="center">
			<th width="50%">제목</th>
			<th width="20%">등록일자</th>
			<th width="20%">마감일자</th>
			<th width="10%">우선순위</th>
		</tr>
	</table>
	<table border="1" width="700px">
		<c:forEach var="row" items="${map.list}" varStatus="vs">
			<c:if test="${vs.count % 2 == 0}">
				<c:set var="color" value="#cceedd" />
			</c:if>
			<c:if test="${vs.count % 2 == 1}">
				<c:set var="color" value="white" />
			</c:if>
			<tr btcolor="${color}">
				<td width="50%">
					<c:choose>
						<c:when test="${row.done == '1'}">
							<input type="checkbox" name="done" value="1" onclick="change_status('${row.idx}', '${row.done}')" checked>
							<strike>${row.title}</strike>
						</c:when>
						<c:otherwise>
							<input type="checkbox" name=done" value="0" onclick="change_status('${row.idx}', '${row.done}')">${row.title}
						</c:otherwise>
					</c:choose>
				</td>
				<td width="20%" align="center">${row.reg_date}</td>
				<td width="20%" align="center">${row.target_date}</td>
				<td width="20%" align="center">
					<c:choose>
						<c:when test="${row.priority == 1}">High</c:when>
						<c:when test="${row.priority == 2}">Medium</c:when>
						<c:when test="${row.priority == 3}">Low</c:when>
					</c:choose>
				</td>
			</tr>
			<tr bgcolor="${color}">
				<td colspan="4">
					<c:choose>
						<c:when test="${row.done == '0'}">${row.description}</c:when>
						<c:when test="${row.done == '1'}">
							<strike>${row.description}</strike>
						</c:when>
					</c:choose> <br>
					<div style="text-align: right">
						<button type="button" onclick="location.href='/detail.do?idx=${row.idx}'">수정</button>
						<button type="button" onclick="delete_todo('${row.idx}')">삭제</button>
					</div>
				</td>
			</tr>
		</c:forEach>
	</table>
</c:if>
</body>
</html>