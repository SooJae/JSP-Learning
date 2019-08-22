<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/custom.css">
<title>JSP Ajax 실시간 익명 사이트</title>
<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
<script src="js/bootstrap.js"></script>
<script type="text/javascript">
	var lastID = 0;
	function submitFunction() {
		var chatName = $('#chatName').val();
		var chatContent = $('#chatContent').val();
		$.ajax({
			type : "post",
			url : "./chatSubmitServlet",
			data : {
				/*파라미터이름 : 실제 보내는 값 (var chatName)  */
				//encodeURIComponent = 특수문자를 보내도 정상적으로 보내게 해준다. 받는쪽에서도 decode를 해줘야한다. URLDecoder
				chatName : encodeURIComponent(chatName),
				chatContent : encodeURIComponent(chatContent)
			},
			success : function(result) {
				if (result == 1) {
					autoClosingAlert('#successMessage', 2000);
				} else if (result == 0) {
					autoClosingAlert('#dangerMessage', 2000);
				} else {
					autoClosingAlert('#warningMessage', 2000);
				}
			}
		});
		/* 전송창을 비워줌 */
		$('#chatContent').val('');
	}
	function autoClosingAlert(selector, delay) {
		var alert = $(selector).alert();
		alert.show();
		window.setTimeout(function() {
			alert.hide()
		}, delay);
	}
	/* type을 매개변수로 받는다. */
	function chatListFunction(type) {
		$.ajax({
			type : "post",
			url : "./chatListServlet",
			data : {
				/*파라미터이름 : 실제 보내는 값 (var chatName)  */
				listType : type,
			},
			/* data에 담기도록한다. */
			success : function(data) {
				//비어있다면 오류가 발생하거나 데이터가 비어있다면, 함수를 바로 종료, 즉 파싱 가능한 데이터만 파싱하겠다.
				if(data == "") return;
				//alert(data);
				var parsed = JSON.parse(data); //data값을 JSON형태로 parsing 할수 있게 해준다.
				var result = parsed.result; // 결과로 담기는 원소들{"result":[[{...}]]} 이부분
				for (var i = 0; i < result.length; i++) {
					addChat(result[i][0].value, result[i][1].value,
							result[i][2].value);
				}
				lastID = Number(parsed.last);
				/* alert(lastID); */
			}
		});
	}
	function addChat(chatName, chatContent, chatTime) {
		$('#chatList')
				.append(
						'<div class="row">'
								+ '<div class="col-lg-12">'
								+ '<div class="media">'
								+ '<a class="pull-left" href="#">'
								+ '<img class="media-object img-circle" src="images/icon.png" alt="">'
								+ '</a>' + '<div class="media-body">'
								+ '<h4 class="media-heading">' + chatName
								+ '<span class="small pull-right">' + chatTime
								+ '</span>' + '</h4>' + '</div>' + '<p>'
								+ chatContent + '</p>' + '</div>' + '</div>'
								+ '</div>' + '<hr>');
		//글을 쓸때마다 스크롤을 맨 밑으로 내려준다.
		$('#chatList').scrollTop($('#chatList')[0].scrollHeight);
	}
	
	//1초마다 chatListFunction을 실행시키는 함수
	function getInfiniteChat(){
		setInterval(function(){
			chatListFunction(lastID);
			}, 1000);
	}
</script>
</head>
<body>
	<div class="container">
		<div class="container bootstrap snippet">
			<div class="row">
				<div class="col-xs-12">
					<div class="portlet portlet-default">
						<div class="portlet-heading">
							<div class="portlet-title">
								<h4>
									<i class="fa fa-circle text-green"></i>실시간 채팅방
								</h4>
							</div>

							<div class="clearfix"></div>
						</div>
						<div id="chat" class="panel-collapse collapse in">
							<!-- overflow-y 는 글을 작성할수록 밑으로 계속 생겨난다. -->
							<div id="chatList" class="portlet-body chat-widget"
								style="overflow-y: auto; width: auto; height: 300px;">
								<!-- 채팅기록 -->
							</div>
							<div class="portlet-footer">
								<div class="row">
									<div class="form-group col-xs-4">
										<input style="height: 40px;" type="text" id="chatName"
											class="form-control" placeholder="이름" maxlength="8">
									</div>
								</div>
								<div class="row" style="height: 90px">
									<div class="form-group col-xs-10">
										<textarea style="height: 80px;" id="chatContent"
											class="form-control" placeholder="메시지를 입력하세요" maxlength="100"></textarea>
									</div>
									<div class="form-group col-xs-2">
										<button type="button" class="btn btn-default pull-right"
											onclick="submitFunction();">전송</button>
										<div class="clearfix"></div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!-- /.col-md-4 -->
		</div>

		<div class="alert alert-success" id="successMessage"
			style="display: none;">
			<strong>메세지 전송해 성공했습니다.</strong>
		</div>
		<div class="alert alert-danger" id="dangerMessage"
			style="display: none;">
			<strong>이름과 내용을 모두 입력해주세요</strong>
		</div>
		<div class="alert alert-warning" id="warningMessage"
			style="display: none;">
			<strong>데이터베이스 오류가 발생했습니다.</strong>
		</div>
	</div>
	<script type="text/javascript">
	/* 처음 페이지가 로딩됐을때  함수를 실행한다.*/
		$(document).ready(function() {
			chatListFunction('ten');
			getInfiniteChat();
		});
	</script>
</body>
</html>