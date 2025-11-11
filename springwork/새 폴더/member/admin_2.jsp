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
        const response = await fetch(`/jobque/checkid?userid=${encodeURIComponent(userid)}`);
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
      const useridInput = document.getElementById("userid");
      useridInput.addEventListener("input", () => {
        isIdChecked = false;
        document.getElementById("register").disabled = true;
        document.getElementById("idMsg").textContent = "";
      });
    });
  </script>
</head>
<body>
<h2>회원가입</h2>
<form name="form1" method="post" action="/login">
	<div>
		<label for="name">이름</label>
		<input type="text" id="name" name="name" required>
	</div>

	<div>
		<label for="gender">성별</label>
		<input type="radio" name="gender" value="남자"> 남자
        <input type="radio" name="gender" value="여자"> 여자
	</div>

	<div>
		<label for="birth">생년월일</label>
		<input type="password" id="confirmPwd" oninput="checkPasswordMatch()" required>
		<span id="pwdMsg" class="pwd-message"></span>
	</div>

	<button type="submit" id="nextBtn" disabled>다음으로</button>
</form>
</body>
</html>