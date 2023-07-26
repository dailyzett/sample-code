/**
 * 
 */


function checkValue(){
	var form = document.userInfo;
	
	if(form.usernameDuplication.value != "usernameCheck"){
		alert("아이디 중복 확인이 필요합니다.");
		return false;
	}
	
	if(form.pw.value != form.pwr.value){
		alert('비밀번호가 일치하지 않습니다');
		return false;
	}
}

function openUsernameCheck(){
	window.name = "parentForm";
	window.open("IDCheckForm", "checkForm", "width=500, height=500");
}


function inputUsernameCheck(){
	document.userInfo.usernameDuplication.value="usernameUncheck";
}