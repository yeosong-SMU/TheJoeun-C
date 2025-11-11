<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<%@ include file="../include/session_check.jsp" %>
<!-- <link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<link href="/resources/summernote/summernote.min.css" rel="stylesheet">
<script src="/resources/summernote/summernote.min.js"></script> -->
<script>
/* $(function() {
	$("#description").summernote({
		height : 300,
		width : 800
	});
}); */
function product_write() {
	const product_name = document.form1.product_name.value;
	const price = document.form1.price.value;
	const description = document.form1.description.value;
	if(product_name == "") {
		alert("상품명을 입력하세요.");
		document.form1.product_name.focus();
		return;
	}
	if(price == "") {
		alert("가격을 입력하세요.");
		document.form1.price.focus();
		return;
	}
	if(description == "") {
		alert("상품설명을 입력하세요.");
		document.form1.description.focus();
		return;
	}
	document.form1.action="/shop/product/insert.do";
	document.form1.submit();
}
</script>
</head>
<body>
<%@ include file="../include/menu.jsp" %>
<h2>상품 등록</h2>
<form name="form1" method="post" enctype="multipart/form-data">
	<table>
		<tr>
			<td>상품명</td>
			<td><input name="product_name"></td>
		</tr>
		<tr>
			<td>가격</td>
			<td><input name="price"></td>
		</tr>
		<tr>
			<td>상품설명</td>
			<td>
				<textarea id="description" rows="5" cols="60" name="description"></textarea>
				<!-- 
				<textarea id="description" rows="5" cols="80" name="description" placeholder="상품설명을 입력하세요"></textarea>
				<script>
					CKEDITOR.replace("description");  //태그에 id 속성 추가
					
					CKEDITOR.replace("description", {  //이미지 업로드 기능
						filebrowserUploadUrl : "/imageUpload.do"
					});
				</script> -->
			</td>
		</tr>
		<tr>
			<td>상품이미지</td>
			<td><input type="file" name="file1"></td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<input type="button" value="등록" onclick="product_write()">
				<input type="button" value="목록" onclick="location.href='/shop/product/list.do'">
			</td>
		</tr>
	</table>
</form>
</body>
</html>