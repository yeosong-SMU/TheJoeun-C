<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>회원가입</title>
<script>
	function checkPasswordMatch() {
		const pwd = document.getElementById("passwd").value;
      	const confirmPwd = document.getElementById("confirmPwd").value;
      	const msg = document.getElementById("pwdMsg");

      	if (confirmPwd === "") {
        	msg.textContent = "";
        	return;
      	}

      	if (pwd === confirmPwd) {
       		msg.style.color = "green";
        	msg.textContent = "비밀번호가 일치합니다.";
      	} else {
        	msg.style.color = "red";
        	msg.textContent = "비밀번호가 일치하지 않습니다.";
      	}
    }

    let isIdChecked = false;  // 아이디 중복 체크 완료 여부

    async function checkDuplicateId() {
      	const userid = document.getElementById("user_id").value;
      	const idMsg = document.getElementById("idMsg");
      	const register = document.getElementById("register");

      	if (!userid) {
        	idMsg.textContent = "아이디를 입력하세요.";
        	idMsg.style.color = "red";
        	isIdChecked = false;
        	register.disabled = true;
        	return;
      	}

      	try {
        	//const response = await fetch(`/jobque/checkid?userid=${encodeURIComponent(userid)}`);
        	const data = await response.json();

	        if (data.exists) {
	          	idMsg.textContent = "이미 사용 중인 아이디입니다.";
	          	idMsg.style.color = "red";
	          	isIdChecked = false;
	          	register.disabled = true;
	        } else {
	          	idMsg.textContent = "사용 가능한 아이디입니다.";
	          	idMsg.style.color = "green";
	          	isIdChecked = true;
	          	register.disabled = false;
	        }
      	} catch (error) {
	        console.error("아이디 중복 확인 실패:", error);
	        idMsg.textContent = "오류가 발생했습니다.";
	        idMsg.style.color = "red";
	        isIdChecked = false;
	        register.disabled = true;
      	}
    }

    // 아이디 입력값이 변경되면 다시 체크해야 하므로 가입 버튼 비활성화
    document.addEventListener("DOMContentLoaded", () => {
      const useridInput = document.getElementById("user_id");
      useridInput.addEventListener("input", () => {
        isIdChecked = false;
        document.getElementById("nextBtn").disabled = true;
        document.getElementById("idMsg").textContent = "";
      });
    });
</script>
</head>
<body>
<h2>회원가입</h2>
<form name="form1" method="post" action="/admin/2">
	<div>
		<label for="user_id">아이디를 입력하세요.</label>
		<input type="text" id="user_id" name="user_id" required>
	</div>
	<div>
        <button type="button" id="checkBtn" onclick="checkDuplicateId()">중복 확인</button>
        <span id="idMsg" class="id-message"></span>
	</div>
	
	<div>
		<label for="passwd">비밀번호를 입력하세요.</label>
		<input type="password" id="passwd" name="passwd" required oninput="checkPasswordMatch()">
	</div>
	
	<div>
		<label for="confirmPwd">비밀번호를 다시 입력하세요.</label>
		<input type="password" id="confirmPwd" oninput="checkPasswordMatch()" required>
		<span id="pwdMsg" class="pwd-message"></span>
	</div>
	
	<button type="submit" id="nextBtn" disabled>다음으로</button>
</form>
</body>
</html>