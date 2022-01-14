<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
	<head>
		<!-- 합쳐지고 최소화된 최신 CSS -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
		<!-- 부가적인 테마 -->
		<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
	 	
	 	<!-- <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script> -->
	 	<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
		<title>회원가입</title>
	</head>
	<script type="text/javascript">
		$(document).ready(function(){
			// 취소
			$(".cencle").on("click", function(){
				location.href = "/";
			})
			
			function regData(f){
				if($("#userId").val()==""){
					alert("아이디를 입력해주세요.");
					$("#userId").focus();
					return false;
				}
				if($("#userPass").val()==""){
					alert("비밀번호를 입력해주세요.");
					$("#userPass").focus();
					return false;
				}
				if($("#userPass").val()!=$("#userPass2").val()){
					alert("입력하신 비밀번호와 비밀번호 확인이 서로 다릅니다.");
					$("#userPass").focus();
					return false;
				}
				if($("#userName").val()==""){
					alert("성명을 입력해주세요.");
					$("#userName").focus();
					return false;
				}
				
				var idChkVal = $("#idChk").val();
				
				if(idChkVal == "N"){
					alert("중복확인 버튼을 눌러주세요.");
					return false;
				}else if(idChkVal == "Y"){
					f.submit();
				}
			}
		})
		
		function fn_idChk(){
			var userid = $("#userId").val();
			$.ajax({
				url : "/member/idChk?userId="+userid,
				type : "get",
				dataType : "json",
				//data : {"userId" : userid},
				success : function(dt){
					if(dt == 1){
						//$("#status").text("중복된 아이디입니다.");
						alert("중복된 아이디입니다.");
						return false;
					}else if(dt == 0){
						//$("#status").text("사용가능한 아이디입니다.");
						$("#idChk").val("Y");
						alert("사용가능한 아이디입니다.");
						return false;
					}
				}, 
				error:function(){
	                //alert("에러입니다");
	            }
			});
		}
	</script>
	<body>
<%
	String msg2 = "";
	String qr = "";
	if(session.getAttribute("msg")!=null) {
		msg2 = (String) session.getAttribute("msg");
		if(msg2 == "ok") {
			qr="사용이 가능한 아이디입니다.";
%>
	<script>
	$(document).ready(function(){
		$("#idChk").val("Y");
	});
	</script>
<%
		} else {
			qr="사용이 불가능한 아이디입니다.";
%>
	<script>
	$(document).ready(function(){
		$("#idChk").val("N");
	});
	</script>
<%
		}		
	}
%>	
<div class="container">
	<header>
		<h1></h1>
	</header>
	<hr />
	<div>
		<%@include file="nav.jsp" %>
	</div>
	<div class="content">
			<form action="/member/registery" method="post" id="regForm" name="vo" onsummit="return regData(this)">
				<div class="form-group has-feedback">
					<label class="control-label" for="userId">아이디</label>
					<input class="form-control" type="text" id="userId" name="userId" required />
					<button class="idChkBtn" type="button" id="idChkBtn" onclick="fn_idChk();">중복확인</button>
					<input type="hidden" id="idChk" value="N"/>
				</div>
				<div class="form-group has-feedback">
					<label class="control-label" for="userPass">패스워드</label>
					<input class="form-control" type="password" id="userPass" name="userPass" required/>
				</div>
				<div class="form-group has-feedback">
					<label class="control-label" for="userPass2">패스워드 확인</label>
					<input class="form-control" type="password" id="userPass2" name="userPass2" required/>
				</div>
				<div class="form-group has-feedback">
					<label class="control-label" for="userName">성명</label>
					<input class="form-control" type="text" id="userName" name="userName" required />
				</div>
				<div class="form-group has-feedback">
					<button class="btn btn-success" type="submit" id="submit">회원가입</button>
					<button class="cencle btn btn-danger" type="button">취소</button>
				</div>
			</form>
			<div id="status">${qr}</div>
	</div>
</div>
	</body>
	
</html>