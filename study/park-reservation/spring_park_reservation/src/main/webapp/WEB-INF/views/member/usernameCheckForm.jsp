<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>���̵� �ߺ� üũ</title>

<style type="text/css">
#wrap {
	width: 490px;
	text-align: center;
}

#chk {
	text-align: center;
}


input[type=button]{
	background-color:#000080;
	color: white;
	padding: 14px 20px;
	margin: 8px 0;
	cursor:   pointer;
	width: 20%;
	opacity: 0.9;
}

input[type=text]{
	width: 40%;
  padding: 15px;
  margin: 5px 0 22px 0;
  display: inline-block;

  background: #f1f1f1;
}

#useBtn {
    background-color: #000080;
	color: white;
	padding: 14px 20px;
	margin: 4px 0;
	border: none;
	cursor:   pointer;
	width: 20%;
	opacity: 0.9;
	visibility: hidden;
}

#cancelBtn {
	background-color: red;
	color: white;
	padding: 14px 20px;
	margin: 8px 0;
	border: none;
	cursor: pointer;
	width: 20%;
	opacity: 0.9;
	visibility: visible;
}

</style>

<script type="text/javascript">
    
        var httpRequest = null;
        
        // httpRequest ��ü ����
        function getXMLHttpRequest(){
            var httpRequest = null;
        
            if(window.ActiveXObject){
                try{
                    httpRequest = new ActiveXObject("Msxml2.XMLHTTP");    
                } catch(e) {
                    try{
                        httpRequest = new ActiveXObject("Microsoft.XMLHTTP");
                    } catch (e2) { httpRequest = null; }
                }
            }
            else if(window.XMLHttpRequest){
                httpRequest = new window.XMLHttpRequest();
            }
            return httpRequest;    
        }
        
        function idCheck(){
        	 
            var id = document.getElementById("userId").value;
 
            if (!id) {
                alert("���̵� �Է����� �ʾҽ��ϴ�.");
                return false;
            } 

            else
            {
                var param="id="+id
                httpRequest = getXMLHttpRequest();
                httpRequest.onreadystatechange = callback;
                httpRequest.open("post", "memberDuplicateCheck", true);    
                httpRequest.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded'); 
                httpRequest.send(param);
            }
        }
  
        // ȸ������â�� ���̵� �Է¶��� ���� �����´�.
        function pValue(){
            document.getElementById("userId").value = opener.document.userInfo.username.value;
        }
        
        // ���̵� �ߺ�üũ
      
        function callback(){
            if(httpRequest.readyState == 4){
                // ������� �����´�.
                var resultText = httpRequest.responseText;
                if(resultText == 0){
                    document.getElementById("cancelBtn").style.visibility='visible';
                    document.getElementById("useBtn").style.visibility='hidden';
                    document.getElementById("msg").innerHTML ="��� �Ұ����� ���̵��Դϴ�";
                } 
                else if(resultText == 1){ 
                    document.getElementById("cancelBtn").style.visibility='visible';
                    document.getElementById("useBtn").style.visibility='visible';
                    document.getElementById("msg").innerHTML = "��� ������ ���̵��Դϴ�.";
                }
            }
        }
        
        // ����ϱ� Ŭ�� �� �θ�â���� �� ���� 
        function sendCheckValue(){
            // �ߺ�üũ ����� idCheck ���� �����Ѵ�.
            opener.document.userInfo.usernameDuplication.value ="usernameCheck";
            // ȸ������ ȭ���� ID�Է¶��� ���� ����
            opener.document.userInfo.username.value = document.getElementById("userId").value;
            
            if (opener != null) {
                opener.chkForm = null;
                self.close();
            }    
        }    
   </script>

</head>
<body onload="pValue()">
	<div id="wrap">
		<br> <b><font size="4" color="gray">���̵� �ߺ�üũ</font></b>
		<hr size="1" width="460">
		<br>
		<div id="chk">
			<form id="checkForm">
				<input type="text" name="idinput" id="userId">
				<input type="button" value="�ߺ�Ȯ��" onclick="idCheck()">
			</form>
			<div id="msg" style="font-weight: bold;">&nbsp;</div>
			<br> <input id="cancelBtn" type="button" value="���"	onclick="window.close()">
				<input id="useBtn" type="button" value="����ϱ�" onclick="sendCheckValue()">
		</div>
	</div>
</body>
</html>