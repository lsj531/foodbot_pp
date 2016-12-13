<%@ page language="java" contentType="text/html; charset=EUC-KR"
	pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Insert title here</title>
<style>
@import url(http://fonts.googleapis.com/earlyaccess/jejugothic.css);

table {
	font-family: 'Jeju Gothic', serif;
	/* 	font-family: arial, sans-serif; */
	border-collapse: collapse;
	width: 50%;
	min-width: 400px;
}

td, th {
	min-width: 40px;
	border: 1px solid #dddddd;
	/* 	text-align: left; */
	padding: 8px;
}

tr:nth-child(even) {
	background-color: #dddddd;
}

/*  */
.tab-wrap * { 
    margin: 0px; 
    padding: 0px; 
}
.tab-wrap { 
    position: relative; 
    padding-top: 30px; 
}
.tab-wrap li { 
    z-index: 2; 
    position: absolute; 
    top: 0px; 
    width: 100px; 
    height: 30px; 
    text-indent: -9999%; 
}
.tab-wrap li:nth-of-type(1) { 
    left: 0px; 
}
.tab-wrap li:nth-of-type(2) { 
    left: 100px; 
}
.tab-wrap li:nth-of-type(3) { 
    left: 200px; 
}
.tab-wrap li:nth-of-type(4) { 
    left: 300px; 
}
.tab-wrap li a { 
    display: block; 
    width: 100%; 
    height: 100%; 
}
.tab-wrap article h1 { 
    position: absolute; 
    top: 0px; 
    width: 100px; 
    height: 30px; 
    line-height: 30px; 
    box-sizing: border-box; 
    border: 1px solid #ddd; 
    text-align: center; 
    font-size: 12px; 
}
.tab-wrap article p { 
    border: 1px solid #ddd; 
    padding: 30px; 
}
.tab-wrap article:target h1 { 
    background-color: yellow; 
}
.tab-wrap article:nth-of-type(1) h1 { 
    left: 0px; 
}
.tab-wrap article:nth-of-type(2) h1 { 
    left: 100px; 
}
.tab-wrap article:nth-of-type(3) h1 { 
    left: 200px; 
}
.tab-wrap article:nth-of-type(4) h1 { 
    left: 300px; 
}
.tab-wrap article p { 
    display: none; 
}
.tab-wrap article:target p { 
    display: block; 
}

</style>
</head>
<body>
	<h1>리스트 페이지 입니다.</h1>
	<div class="tab-wrap">
		<ul>
			<li><a href="#">전체</a></li>
			<li><a href="#">한식</a></li>
			<li><a href="#">일식</a></li>
			<li><a href="#">중식</a></li>
			<li><a href="#">양식</a></li>
		</ul>
		<div>
			<article id="tab1">
				<h1>tab1 title</h1>
				<p>Here is tab1 contents</p>
			</article>
			<article id="tab2">
				<h1>tab2 title</h1>
				<p>Here is tab2 contents</p>
			</article>			
			<article id="tab3">
				<h1>tab3 title</h1>
				<p>Here is tab3 contents</p>
			</article>
			<article id="tab2">
				<h1>tab4 title</h1>
				<p>Here is tab4 contents</p>
			</article>
		</div>
	</div>
	<div>
		<table>
			<tr>
				<th width="10%" align="center">순위</th>
				<th width="30%">분류</th>
				<th width="40%">음식명</th>
				<th width="15%">수치</th>
			</tr>
			<tr>
				<td align="center">1</td>
				<td>한식</td>
				<td>제육볶음</td>
				<td align="center">0.91</td>
			</tr>
			<tr>
				<td align="center">2</td>
				<td>한식</td>
				<td>닭갈비</td>
				<td align="center">0.05</td>
			</tr>
			<tr>
				<td align="center">3</td>
				<td>일식</td>
				<td>초밥</td>
				<td align="center">0.03</td>
			</tr>
			<%
				List<FoodVO> foodlist = fservice.listAll();
			%>
<!-- 			<tr>
				<td align="center"></td>
			</tr> -->
			<%
				
			%>


		</table>
	</div>

</body>
</html>