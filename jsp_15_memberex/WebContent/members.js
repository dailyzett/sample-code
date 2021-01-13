/**
 * 
 */

function infoConfirm(){
	if(document.reg_frm.id.value.length == 0){
		alert("아이디는 필수사항입니다");
		reg_frm.id.focus();
		return;
	}
	
	if(document.reg_frm.id.value.length < 4){
		alert("아이디는 4자 이상 입력하세요");
		reg_frm.id.focus();
		return;
	}
	
	if(document.reg_frm.pw.value.length == 0){
		alert("패스워드는 필수사항입니다");
		reg_frm.pw.focus();
		return;
	}
	
	if(document.reg_frm.pw.value != document.reg_frm.pw_check.value){
		alert("패스워드가 일치하지않습니다");
		reg_frm.id.focus();
		return;
	}
	
	if(document.reg_frm.name.value.length == 0){
		alert("이름은 필수사항입니다");
		reg_frm.id.focus();
		return;
	}
	
	if(document.reg_frm.eMail.value.length == 0){
		alert("메일은 필수사항입니다");
		reg_frm.id.focus();
		return;
	}
	
	document.reg_frm.submit();
}


function updateInfoConfirm(){
	if(document.reg_frm.pw.value==""){
		alert("패스워드를 입력하세요");
		document.reg_frm.pw.focus();
		return;
	}
	
	if(document.reg_frm.pw.value != document.reg_frm.pw_check.value){
		alert("패스워드가 일치하지않습니다");
		reg_frm.id.focus();
		return;
	}
	
	if(document.reg_frm.eMail.value.length == 0){
		alert("메일은 필수사항입니다");
		reg_frm.id.focus();
		return;
	}
}