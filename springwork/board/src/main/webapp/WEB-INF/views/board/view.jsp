<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script>
$(function() {
	$("#btnList").click(function() {
		location.href="/board/list.do";
	});
	listReply("1");
	$("#btnReply").click(function() {
		reply();
	});
	$(".fileDrop").on("dragenter dragover", function(e){
		e.preventDefault();
	});
	$(".fileDrop").on("drop", function(e){
		e.preventDefault();
		const files = e.originalEvent.dataTransfer.files;
		const file = files[0];
		const form_data = new FormData();
		form_data.append("file", file);
		$.ajax({
			url: "/upload/ajax_upload",
			data: form_data,
			dataType: "text",
			processData: false,
			contentType: false,
			type: "post",
			success: function(data) {
				const fileInfo = getFileInfo(data);
				let html = "<a href='" + fileInfo.get_link + "'>" + fileInfo.original_name + "</a><br>";
				html += "<input type='hidden' class='file' value='" + fileInfo.file_name + "'>";
				$("#uploadedList").append(html);
			}
		});
	});
	listAttach();
	$("#uploadedList").on("click", ".file_del", function(e){
		const that=$(this);
		$.ajax({
			type: "post",
			url: "/upload/delete_file",
			data: {file_name: $(this).attr("data-src")},
			dataType: "text",
			success: function(result){
				if(result == "deleted"){
					that.parent("div").remove();
				}
			}
		});
	});
	
	$("#btnUpdate").click(function() {
		let str = "";
		$("#uploadedList .file").each(function(i){
			str += "<input type='hidden' name='files["+i+"]' value='" + $(this).val() + "'>";
		});
		$("#form1").append(str);
		document.form1.action="/board/update.do";
		document.form1.submit();
	});
	
	$("#btnDelete").click(function() {
		if(confirm("삭제하시겠습니까?")){
			document.form1.action="/board/delete.do";
			document.form1.submit();
		}
	});
});

function getFileInfo(file_name) {
	const get_link = "/upload/display_file?file_name=" + file_name;
	original_name = file_name.substr(file_name.indexOf("_") + 1);
	return { original_name: original_name, get_link: get_link, file_name: file_name};
}
function listAttach() {
	$.ajax({
		type: "post",
		url: "/board/list_attach/${dto.idx}",
		success: function(list) {
			$(list).each(function() {
				const fileInfo=getFileInfo(this);
				let html="<div><a href='" + fileInfo.get_link + "'>" + fileInfo.original_name + "</a>&nbsp;&nbsp;";
				html += "<a href='#' class='file_del' data-src='" + this + "'>[삭제]</a></div>";
				$("#uploadedList").append(html);
			});
		}
	});
}

function reply() {
	const reply_text = $("#reply_text").val();
	const board_idx = "${dto.idx}";
	const params = {"reply_text" : reply_text, "board_idx": board_idx};
	$.ajax({
		type: "post",
		url: "/reply/insert.do",
		data: params,
		success: function(){
			alert("댓글이 등록되었습니다.");
			listReply("1");
		}
	});
}

function listReply(num) {
	$.ajax({
		url: "/reply/list.do?board_idx=${dto.idx}&curPage=" + num,
		success: function(result){
			$("#listReply").html(result);
		}
	});
}

function showModify(idx) {
	$.ajax({
		url: "/reply/detail/" + idx,
		success: function(result){
			$("#modifyReply").html(result);
			$("#modifyReply").css("visibility", "visible");
		}
	});
}
</script>

<style>
.fileDrop {
	width: 600px; height: 100px;
	border: 1px dotted gray;
	background: gray;
}
</style>
</head>
<body>
<%@ include file="../include/menu.jsp" %>
<form id="form1" name='form1' method="post">
	<div>작성일자: <fmt:formatDate value="${dto.regdate}" pattern="yyyy-MM-dd a HH:mm:ss" /></div>
	<div>조회수: ${dto.hit}</div>
	<div>이름: ${dto.name}</div>
	<div>제목: <input name="title" value="${dto.title}"></div>
	<div dtyle="width:80%">내용 : <textarea rows="3" cols="80" name="contents" id="contents">${dto.contents}</textarea></div>
	<div id="uploadedList"></div>
	<div class="fileDrop"></div>
	<div>
		<input type="hidden" name="idx" value="${dto.idx}">
		<c:if test="${sessionScope.userid == dto.writer}">
			<button type="button" id="btnUpdate">수정</button>
			<button type="button" id="btnDelete">삭제</button>
		</c:if>
		<button type="button" id="btnList">목록</button>
	</div>
</form>
<div style="width:700px; text-align:center;">
<c:if test="${sessionScope.userid != null}">
	<textarea rows="5" cols="80" id="reply_text" placeholder="댓글을 작성하세요."></textarea>
	<br>
	<button type="button" id="btnReply">댓글쓰기</button>
</c:if>
</div>
<div id="listReply"></div>

<!-- 댓글 수정 화면 영역 -->
<div id="modifyReply"></div>
</body>
</html>