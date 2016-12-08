<%@page import="org.foodbot.config.Config"%>
<%@page import="org.foodbot.domain.ChatVO"%>
<%@page import="java.util.List"%>
<%@page import="org.foodbot.domain.MemberVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String cp = request.getContextPath();
	MemberVO memberVO = (MemberVO) session.getAttribute("login");
	List<ChatVO> chatList = (List<ChatVO>) session.getAttribute("chat");
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="UTF-8">
<title>Chat</title>
<meta name="generator" content="Bootply" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="description" content="Chat panel" />
<link
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="apple-touch-icon" href="/bootstrap/img/apple-touch-icon.png">
<link rel="apple-touch-icon" sizes="72x72"
	href="/bootstrap/img/apple-touch-icon-72x72.png">
<link rel="apple-touch-icon" sizes="114x114"
	href="/bootstrap/img/apple-touch-icon-114x114.png">
<!-- Theme CSS -->
<link href="<%=cp%>/resources/chatting/chat.css" rel="stylesheet">
</head>
<body STYLE="background-color: transparent">
	<%
		if (memberVO != null) {
	%>
	<div class="container">
		<div class="row">
			<div class="col-md-7"></div>
			<div class="col-md-5">
				<div class="panel panel-primary">
					<div class="panel-heading">
						<span class="glyphicon glyphicon-comment"></span> Chat
						<div class="btn-group pull-right">
							<button type="button"
								class="btn btn-default btn-xs dropdown-toggle"
								data-toggle="dropdown">
								<span class="glyphicon glyphicon-chevron-down"></span>
							</button>
							<ul class="dropdown-menu slidedown">
								<li><a href="http://www.jquery2dotnet.com"><span
										class="glyphicon glyphicon-refresh"> </span>Refresh</a></li>
								<li><a href="http://www.jquery2dotnet.com"><span
										class="glyphicon glyphicon-ok-sign"> </span>Available</a></li>
								<li><a href="http://www.jquery2dotnet.com"><span
										class="glyphicon glyphicon-remove"> </span>Busy</a></li>
								<li><a href="http://www.jquery2dotnet.com"><span
										class="glyphicon glyphicon-time"></span> Away</a></li>
								<li class="divider"></li>
								<li><a href="http://www.jquery2dotnet.com"><span
										class="glyphicon glyphicon-off"></span> Sign Out</a></li>
							</ul>
						</div>
					</div>
					<div class="panel-body">
						<ul class="chat">
							<li class="right clearfix" style="display: none;"><span
								class="chat-img pull-right"> <img
									src="http://placehold.it/50/FA6F57/fff&amp;text=ME"
									alt="User Avatar" class="img-circle">
							</span>
								<div class="chat-body clearfix">
									<div class="header">
										<strong class="sender pull-right primary-font"></strong>
									</div>
									<p class="message"></p>
								</div></li>

							<li class="left leftImage clearfix" style="display: none;"><span
								class="chat-img pull-left"> <img
									src="http://placehold.it/50/55C1E7/fff&amp;text=U"
									alt="User Avatar" class="img-circle">
							</span>
								<div class="chat-body clearfix">
									<div class="header">
										<strong class="sender primary-font"></strong> <small
											class="pull-right text-muted"> <span
											class="glyphicon glyphicon-time"></span>
										</small>
									</div>
									<a class="clickedFood" href="#">
										<div class="row">
											<div class="col-xs-5"></div>
											<img class="col-xs-7 fimage" src="">
											<div class="clearfix"></div>
										</div>
										<div class="row">
											<div class="col-xs-5"></div>
											<p>
											<div class="col-xs-7 message">
												<div class="clearfix"></div>
											</div>
											</p>
										</div>
									</a>
								</div></li>

							<li class="leftNoImage clearfix" style="display: none;"><span
								class="chat-img pull-left"> <img
									src="http://placehold.it/50/55C1E7/fff&amp;text=U"
									alt="User Avatar" class="img-circle">
							</span>
								<div class="chat-body clearfix">
									<div class="header">
										<strong class="sender primary-font"></strong> <small
											class="pull-right text-muted"> <span
											class="glyphicon glyphicon-time"></span>
										</small>
									</div>
									<div id="chat-posts">
										<div class="col-xs-5"></div>
										<div class="col-xs-7 message"></div>
									</div>
								</div></li>
							<div id="chatList">
								<%
									for (int i = 0; i < chatList.size(); i++) {
											if (chatList.get(i).getSender().equals(chatList.get(i).getUid())) {
								%>
								<li class="right clearfix"><span
									class="chat-img pull-right"> <img
										src="http://placehold.it/50/FA6F57/fff&amp;text=ME"
										alt="User Avatar" class="img-circle">
								</span>
									<div class="chat-body clearfix">
										<div class="header">
											<strong class="sender pull-right primary-font"><%=chatList.get(i).getSender()%></strong>
										</div>
										<p class="message"><%=chatList.get(i).getContent()%></p>
									</div></li>
								<%
									} else if (chatList.get(i).getImage_path() != null) {
								%>
								<li class="left leftImage clearfix"><span
									class="chat-img pull-left"> <img
										src="http://placehold.it/50/55C1E7/fff&amp;text=U"
										alt="User Avatar" class="img-circle">
								</span>
									<div class="chat-body clearfix">
										<div class="header">
											<strong class="sender primary-font"><%=chatList.get(i).getSender()%></strong>
										</div>
										<a class="clickedFood" href="#">
											<div clsss="row">
												<div class="col-xs-5"></div>
												<img class="col-xs-7 fimage"
													src="/resources/fimage/image/<%=chatList.get(i).getImage_path()%>">
												<div class="clearfix"></div>
											</div>
											<div clsss="row">
												<div class="col-xs-5"></div>
												<div class="col-xs-7 message">
													<p><%=chatList.get(i).getContent()%></p>
													<div class="clearfix"></div>
												</div>
											</div>
										</a>
									</div></li>
								<%
									} else {
								%>

								<li class="leftNoImage clearfix"><span
									class="chat-img pull-left"> <img
										src="http://placehold.it/50/55C1E7/fff&amp;text=U"
										alt="User Avatar" class="img-circle">
								</span>
									<div class="chat-body clearfix">
										<div class="header">
											<strong class="sender primary-font"><%=chatList.get(i).getSender()%></strong>
											<small class="pull-right text-muted"> <span
												class="glyphicon glyphicon-time"></span>
											</small>
										</div>
										<div id="chat-posts">
											<div class="col-xs-5"></div>
											<div class="col-xs-7 message">
												<p><%=chatList.get(i).getContent()%></p>
											</div>
										</div>
									</div></li>
								<%
									}
								}
								%>
							</div>
						</ul>
					</div>
					<div class="panel-footer">
						accordion
						<div class="input-group">
							<input id="btn-input" type="text" class="form-control input-sm"
								placeholder="Type your message here..."> <span
								class="input-group-btn">
								<button class="btn btn-warning btn-sm" id="btn-chat">
									Send</button>
							</span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<input type="hidden" id="nickname" value=<%=memberVO.getUid()%>></input>
	<%
		}
	%>

	<script type='text/javascript'
		src="//ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
	<script type='text/javascript'
		src="//netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
	<script src="<%=cp%>/resources/js/jquery.js"></script>
	<script src="<%=cp%>/resources/js/sockjs.js"></script>
	<!-- JavaScript jQuery code from Bootply.com editor  -->

	<script type='text/javascript'>
	var wsocket;
	var chatAppend;
	
	function connect() {
		//wsocket = new WebSocket("ws://localhost:9999/chat-ws");
		wsocket = new SockJS("<c:url value="/chat-ws"/>");
		wsocket.onopen = onOpen;
		wsocket.onmessage = onMessage;
		wsocket.onclose = onClose;

		// 초기에 메시지창 스크롤을 최하단으로 맞춤.
		scrollDown();
	}
	function disconnect() {
		wsocket.close();
	}
	function onOpen(evt) {
		//appendMessage("연결되었습니다");
	}
	// 핸들러로부터 메시지를 받음
	function onMessage(evt) {
		var data = evt.data;
		console.log(data.substring(4));
		// 0: msg  1:userid 2:message
		var msgList = data.split(":");
		msg = msgList[2];

		if (data.substring(0, 4) == "msg:") {
			message = data.substring(4);
			appendMessage(message, msg);
		}
	}

	function onClose(evt) {
		//appendMessage("연결을 끊었습니다");
	}

	function send() {
		var nickname = $("#nickname").val();
		var msg = $("#btn-input").val();
		wsocket.send("msg:" + nickname + ":" + msg);
		$("#btn-input").val("");
	}

	// 메시지창 스크롤을 최하단으로 맞춤
	function scrollDown() {
		var chatAreaHeight = $(".panel-body").height();
		var maxScroll = $("#chatList").height() - chatAreaHeight;
		$(".panel-body").scrollTop($("#chatList").height());
	}

	function receiveMessage(sender, msg, path) {
		if (path == "null") {
			chatAppend = $("li.leftNoImage:last").clone(true);
			$("#chatList").append(chatAppend.addClass("li.leftNoImage:last").show());
			$(".message:last").html(msg);
			$(".sender:last").html(sender);
		} else {
			// 텍스트 추가를 위한 태그 복사
			chatAppend = $("li.leftImage:last").clone(true);
			$("#chatList").append(chatAppend.addClass("li.left:last").show());
			$(".message:last").html(msg);
			$(".fimage:last").attr("src", "resources/fimage/image/" + path);
			$(".sender:last").html(sender);
		}
		scrollDown();
	}

	function appendMessage(message, msg) {
		// 텍스트 추가를 위한 태그 복사
		chatAppend = $("li.right:last").clone(true);
		$("#chatList").append(chatAppend.addClass("li.right:last").show());
		$(".message:last").html(msg);

		scrollDown();

		// data 는 사용자가 보낸 메시지..
		var m = message;
		// 사용자가 요청한 메시지를 분석/분류 하기위한 요청 ..
		$.ajax({
			url : 'mlp/message',
			dataType : 'json',
			type : 'POST',
			contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
			data : {
				"m" : m
			},
			success : function(json) {
				receiveMessage(json.sender, json.msg, json.path);
				//alert(json.sender + " " + json.msg);
			},
			error : function(result) {
				//alert('실패' + result);
			}
		});
	}

	$(document).ready(function() {
		$('#btn-input').keypress(function(event) {
			var keycode = (event.keyCode ? event.keyCode : event.which);
			if (keycode == '13') {
				send();
			}
			event.stopPropagation();
		});
		$('#btn-chat').click(function() {
			send();
		});
		$('#enterBtn').click(function() {
			connect();
		});
		$('#exitBtn').click(function() {
			disconnect();
		});
		connect();

		$(document).on("click", ".clickedFood", function() {
			var clickedFood = $(this).find('.message').text();
			console.log(($(this).find('.message').text()));
			searchRestaurant(clickedFood);
			$('.searchPage').animate({'left':'0px'}).css("display","block");

			$.ajax({
				url : '/mlp/mlp',
				dataType : 'json',
				type : 'POST',
				data : {
					"food" : clickedFood
				},
				success : function(json) {
					//receiveMessage(json.msg);
					//alert(json.msg);
				},
				error : function(result) {
					//alert('실패' + result);
				}
			});
		});
	});
	</script>

	<script>
	(function(i, s, o, g, r, a, m) {
		i['GoogleAnalyticsObject'] = r;
		i[r] = i[r] || function() {
			(i[r].q = i[r].q || []).push(arguments)
		}, i[r].l = 1 * new Date();
		a = s.createElement(o), m = s.getElementsByTagName(o)[0];
		a.async = 1;
		a.src = g;
		m.parentNode.insertBefore(a, m)
	})(window, document, 'script',
			'//www.google-analytics.com/analytics.js', 'ga');
	ga('create', 'UA-40413119-1', 'bootply.com');
	ga('send', 'pageview');
	</script>
</body>
</html>