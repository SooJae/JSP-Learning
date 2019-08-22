<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%-- <% request.setCharacterEncoding("utf-8"); %>
<% response.setContentType("text/html; charset=utf-8"); %>  --%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="css/bootstrap.css">
<title>JSP AJAX</title>
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="js/bootstrap.js"></script>
<script type="text/javascript">
	var searchRequest = new XMLHttpRequest();
	var registerRequest = new XMLHttpRequest();
	function searchFunction() {
		/* UTF-8으로 인코딩된 것을 보낸다. */
		searchRequest.open("Post",
				"./UserSearchServlet?userName="
						+ encodeURIComponent(document
								.getElementById("userName").value), true);
		/*밑에것으로 해도 된다.  */
		/* searchRequest.open("Post","./UserSearchServlet?userName="+document.getElementById("userName").value, true); */
		searchRequest.onreadystatechange = searchProcess;
		searchRequest.send(null);
	}
	function searchProcess() {
		var table = document.getElementById("ajaxTable");
		table.innerHTML = "";
		/* 통신이 성공적으로 되었다면 */
		if (searchRequest.readyState == 4 && searchRequest.status == 200) {
			var object = eval('(' + searchRequest.responseText + ')');
			//var object= decodeURIComponent(eval('('+searchRequest.responseText+')'));

			/* 서블릿 안에서 처음에 있던 result를 갖고오겠다. */
			var result = object.result;
			for (var i = 0; i < result.length; i++) {
				/* 테이블에 하나의 행을 만든다. */
				var row = table.insertRow(0);
				for (var j = 0; j < result[i].length; j++) {
					/* cell을 추가한다. */
					var cell = row.insertCell(j);
					cell.innerHTML = result[i][j].value;
				}
			}
		}
	}
	window.onload = function() {
		searchFunction();
	}
	function registerFunction(){
		registerRequest.open("Post",
				"./UserRegisterServlet?userName="
						+ encodeURIComponent(document.getElementById("registerName").value)+
								"&userAge="+encodeURIComponent(document.getElementById("registerAge").value)+
								"&userGender="+encodeURIComponent($('input[name=registerGender]:checked').val())+
								"&userEmail="+encodeURIComponent(document.getElementById("registerEmail").value), true);
		/*밑에것으로 해도 된다.  */
		/* searchRequest.open("Post","./UserSearchServlet?userName="+document.getElementById("userName").value, true); */
		registerRequest.onreadystatechange = registerProcess;
		registerRequest.send(null);
	}
	
	function registerProcess(){
		if(registerRequest.readyState==4&&registerRequest.status==200){
			var result=registerRequest.responseText;
			if(result != 1){
				alert('등록에 실패했습니다.');
			}
			else{
				/* userName부분은 검색값이다. gender는 굳이 초기화해줄필요가 없어서 냅둠*/
				var userName =document.getElementById("userName");
				var registerName =document.getElementById("registerName");
				var registerAge =document.getElementById("registerAge");
				var registerEmail =document.getElementById("registerEmail");
				/* 공백으로 초기화해줌 */
				userName.value="";
				registerName.value="";
				registerAge.value="";
				registerEmail.value="";
				/* 새로추가되는 회원이 생겼으므로 다시 searchFunction을 해줌 */
				searchFunction();
			}
		}
	}
	/* $('#userName').on("keyup",search())
	$('#searchButton').on("click", search()) */
</script>
</head>
<body>
	<br>
	<div class="container">
		<div class="form-group row pull-right">
			<div class="col-xs-8">
				<input class="form-control" onkeyup="searchFunction()" id="userName"
					type="text" size="20">
			</div>
			<div class="col-xs-2">
				<button class="btn btn-primary" id="searchFunction"
					onclick="searchFunction();" type="button">검색</button>
			</div>
		</div>
		<table class="table"
			style="text-align: center; border: 1px solid #dddddd">
			<thead>
				<tr>
					<th style="background-color: #fafafa; text-align: center;">이름</th>
					<th style="background-color: #fafafa; text-align: center;">나이</th>
					<th style="background-color: #fafafa; text-align: center;">성별</th>
					<th style="background-color: #fafafa; text-align: center;">이메일</th>
				</tr>
			</thead>
			<!-- Ajax 테이블을 만들 것이다. -->
			<tbody id="ajaxTable">
			</tbody>
		</table>
	</div>
	<div class="container">
		<table class="table"
			style="text-align: center; border: 1px solid #dddddd">
			<thead>
				<tr>
					<th colspan="2"
						style="background-color: #fafafa; text-align: center;">회원 등록
						양식</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td style="background-color: #fafafa; text-align: center;"><h5>이름</h5></td>
					<td><input class="form-control" type="text" id="registerName"
						size="20"></td>
				</tr>
				<tr>
					<td style="background-color: #fafafa; text-align: center;"><h5>나이</h5></td>
					<td><input class="form-control" type="text" id="registerAge"
						size="20"></td>
				</tr>
				<tr>
					<td style="background-color: #fafafa; text-align: center;"><h5>성별</h5></td>
					<td>
						<div class="form-group"
							style="text-align: center; margin: 0 auto;">
							<div class="btn-group" data-toggle="buttons">
								<label class="btn btn-primary active"> <input
									type="radio" name="registerGender" autocomplete="off"
									value="남자" checked>남자
								</label> <label class="btn btn-primary"> <input type="radio"
									name="registerGender" autocomplete="off" value="여자">여자
								</label>
							</div>
						</div>
					</td>
				</tr>
				<tr>
					<td style="background-color: #fafafa; text-align: center;"><h5>이메일</h5></td>
					<td><input class="form-control" type="text" id="registerEmail"
						size="20"></td>
				</tr>
				<tr>
					<td colspan="2"><button class="btn btn-primary pull-right" onclick="registerFunction();" type="button">등록</button>
				</tr>
			</tbody>
		</table>
	</div>
</body>
</html>