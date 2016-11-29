<%@page import="org.foodbot.domain.ManagerVO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	ManagerVO managerVO = (ManagerVO) session.getAttribute("manager_login");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="https://cdnjs.cloudflare.com/ajax/libs/d3/3.5.5/d3.min.js"></script>
<script src="../resources/graph/RealTimeLineGraph.js"></script>
<title>train page</title>

<style>


.trainGraph {
height: 400px;
border : 10px solid rgba(220, 220, 220, 0.7);
border-radius : 20px;
background-color: rgba(245, 245, 245, 0.9);
box-shadow:10px 10px 10px silver;
-moz-box-shadow:10px 10px 10px silver;
-webkit-box-shadow:10px 10px 10px silver
}

.trainCost {
height: 400px;
border : 10px solid rgba(230, 230, 230, 0.7);
border-radius : 20px;
background-color: rgba(245, 245, 245, 0.9);
	overflow-y: scroll;
	box-shadow:10px 10px 10px silver;
-moz-box-shadow:10px 10px 10px silver;
-webkit-box-shadow:10px 10px 10px silver
}
</style>
</head>
<body>


		<div class="span1"> 
			<form method="post" action="train">
				<h3>Online Learning Page</h3>
				<textarea name="comment" rows="2" cols="10"></textarea>
				<h2>
					<input id="goTrain" type="submit" value="GO TRAIN">
				</h2>
				<input type="hidden" name="uid" value="<%=managerVO.getUid()%>" />
			</form>
		</div>
		<div class="span1"></div>
			<div class="span4 trainGraph">
			<h3>학습 그래프</h3>
				<div class="FirstTimeOperation"
					style="width: 40%; padding-left: 10px; padding-right: 10px;">
					<span id="graph_pane"></span><br>
				</div>
				<button onclick="stopWorker()">종료</button>
			</div>
			<div class="span2 trainCost"><h3>ERROR</h3><div id="currCost"></div></div>

	<script>
		
		$(document).ready(function() {

			$("#goTrain").on("click", function() {
				costRequest();
			});
		});
	</script>
</body>
</html>