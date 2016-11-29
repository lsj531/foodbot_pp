<%@page import="org.foodbot.domain.ManagerVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%
	String cp = request.getContextPath();
%>
<%
	HttpSession session = request.getSession();
%>
<%
	ManagerVO managerVO = (ManagerVO) session.getAttribute("manager_login");
%>
<!DOCTYPE html>
<html lang="en">

<head>

<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Creative - Start Bootstrap Theme</title>
<!-- Bootstrap Core CSS -->
<link href="../resources/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" media="screen">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="../resources/bootstrap/css/bootstrap-responsive.css"
	rel="stylesheet">
	<script src="http://code.jquery.com/jquery.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/d3/3.5.5/d3.min.js"></script>
<script src="../resources/graph/RealTimeLineGraph2.js"></script>


<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
        <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->

<style>
.login {
	position: absolute;
	top: 200px;
	left: 40%;
	width: 700px;
	height: 550px;
}
</style>
</head>

<body id="page-top">


	<%
		if (session.getAttribute("manager_login") == null) {
	%>
	<div class="login">
		<section class="logincontainer">
			<article class="half">
				<h1>Food Bot</h1>
				<div class="tabs">
					<span class="tab signin active"><a href="#signin">Sign
							in</a></span>
				</div>
				<div class="content">
					<form action="login" method="post">
						<input type="email" name="uid" id="login__email" class="inpt"
							required="required" placeholder="Your email"> <label
							for="email">Your email</label> <input type="password"
							name="password" id="login__password" class="inpt"
							required="required" placeholder="Your password"> <label
							for="password">Your password</label>

						<div class="submit-wrap">
							<input type="submit" value="Sign in" class="submit">
						</div>
					</form>
				</div>
			</article>
		</section>
	</div>
	<%
		} else {
	%>

	<div class="navbar navbar-inverse navbar-fixed-top">
		<div class="navbar-inner">
			<div class="container">
				<button type="button" class="btn btn-navbar" data-toggle="collapse"
					data-target=".nav-collapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="brand" href="#">Food Bot</a>
				<div class="nav-collapse collapse">
					<ul class="nav">
						<li class="home"><a href="#">Home</a></li>
						<li class="insert"><a href="#insert">Insert</a></li>
						<li class="view"><a href="#view">View</a></li>
						<li class="train"><a href="#train">Train</a></li>
						<li class="online"><a href="#online">Online</a></li>
					</ul>
				</div>
				<!--/.nav-collapse -->
			</div>
		</div>
	</div>

	<div class="container-fluid">

		<div class="row-fluid hero-unit">
			<h2>관리자 페이지</h2>
		</div>

		<div class="row-fluid bb"></div>
		<!--
        <div class=" hero-unit navbar-fixed-bottom">
            <p>문의번호 : 010-123-4567</p>
        </div>
            -->
	</div>

	<%
		}
	%>

	<script src="http://code.jquery.com/jquery.js"></script>
	<script>
		$(document).ready(function() {

			$(".insert").click(function() {
				$.ajax({
					url : './insert',
					success : function(data) {
						$(".bb").html(data);
						$(".result").html("");
					}
				})
			});

			$(".train").click(function() {
				$.ajax({
					url : "./train",
					success : function(data) {
						$(".bb").html(data);
					}
				})
			});

			$(".online").click(function() {
				$.ajax({
					url : "./online",
					success : function(data) {
						$(".bb").html(data);
					}
				})
			});

			$(".home").click(function() {
				window.location.href = "./";

			});
			$(".ml").click(function() {
				$.ajax({
					url : './ml.html',
					success : function(data) {
						$(".bb").html(data);
					}
				})
			});

		});
	</script>


</body>

</html>
