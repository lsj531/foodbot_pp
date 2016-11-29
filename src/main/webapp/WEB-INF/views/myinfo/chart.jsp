<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style type="text/css">
  ul {
    list-style: none;
    margin-right : 0px;
    float : left;
  }
  li {
    padding : 5% 0 5% 0;
  }
</style>
<title>차트</title>

<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.5/css/bootstrap.min.css" integrity="sha384-AysaV+vQoT3kOAXZkl02PThvDr8HYKPZhNT5h/CXfBThSRXQ6jW5DO2ekP5ViFdi" crossorigin="anonymous">
<script src="http://code.jquery.com/jquery.js"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/d3/3.4.4/d3.min.js"></script>
<script src="../resources/graph/d3pie.min.js"></script>
</head>
<body>
	<h2>당신이 선택한 음식들의 맛들을 보여줍니다.</h2>
	<br><br>
	<ul>
		<li><button id="coldBtn" class="btn btn-info" onclick="cold()">차가운 음식 보기</button></li>
		<li><button id="hotBtn" class="btn btn-danger" onclick="hot()">뜨거운 음식 보기</button></li>
	</ul>
	<div id="pieChart" style="margin-left : 30%">
	</div>

	<script>
		var kkk;
		var pie;
		$(document).ready(function() {

		});
		function hot() {
			$.ajax({
				url : "/chart/chart",
				type : "get",
				dataType : "json",
				data : {
					"result" : "hot"
				},
				success : function(json) {
					
					console.log(typeof json);
					kkk = JSON.stringify(json.content);
					console.log(typeof kkk);
					console.log(kkk);
					makePIChart(json.content, "따뜻한 음식", "당신이 선택한 따뜻한 음식의 맛");
				}
			});
		}
		function cold() {
			$.ajax({
				url : "/chart/chart",
				type : "get",
				dataType : "json",
				data : {
					"result" : "cold"
				},
				success : function(json) {
					console.log(typeof json);
					kkk = JSON.stringify(json.content);
					console.log(typeof kkk);
					makePIChart(json.content, "차가운 음식", "당신이 선택한 차가운 음식의 맛");
				}
			});
		}
		function makePIChart(jsonData, major, sub) {
			console.log("pie" + typeof pie);
			if (typeof pie !== 'undefined') {
				pie.destroy();
			}
			console.log(kkk);
			pie = new d3pie("pieChart", {
				"header" : {
					"title" : {
						"text" : major,
						"fontSize" : 44,
						"font" : "courier"
					},
					"subtitle" : {
						"text" : sub,
						"color" : "#999999",
						"fontSize" : 10,
						"font" : "courier"
					},
					"location" : "pie-center",
					"titleSubtitlePadding" : 10
				},
				"footer" : {
					"color" : "#999999",
					"fontSize" : 10,
					"font" : "open sans",
					"location" : "bottom-center"
				},
				"size" : {
					"canvasWidth" : 590,
					"pieInnerRadius" : "70%",
					"pieOuterRadius" : "100%"
				},
				"data" : {
					"sortOrder" : "value-desc",
					"smallSegmentGrouping" : {
						"enabled" : true
					},
					"content" : jsonData
				},
				"labels" : {
					"outer" : {
						"format" : "label-percentage1",
						"pieDistance" : 30
					},
					"inner" : {
						"format" : "none",
						"hideWhenLessThanPercentage" : 1
					},
					"mainLabel" : {
						"fontSize" : 15
					},
					"percentage" : {
						"color" : "#999999",
						"fontSize" : 15,
						"decimalPlaces" : 2
					},
					"value" : {
						"color" : "#cccc43",
						"fontSize" : 11
					},
					"lines" : {
						"enabled" : true
					},
					"truncation" : {
						"enabled" : true
					}
				},
				"effects" : {
					"pullOutSegmentOnClick" : {
						"effect" : "default",
						"speed" : 400,
						"size" : 30
					},
					"highlightSegmentOnMouseover" : true
				},
				"misc" : {
					"colors" : {
						"segmentStroke" : "#000000"
					}
				}
			});
		}
		function changeTemp() {
			pie.destroy();

			if (curTemp === "hot") {
				pie = new d3pie("pieChart", {
					"header" : {
						"title" : {
							"text" : "차가운 음식",
							"fontSize" : 34,
							"font" : "courier"
						},
						"subtitle" : {
							"text" : "유저가 선택한 차가운 음식의 맛",
							"color" : "#999999",
							"fontSize" : 10,
							"font" : "courier"
						},
						"location" : "pie-center",
						"titleSubtitlePadding" : 10
					},
					"footer" : {
						"color" : "#999999",
						"fontSize" : 10,
						"font" : "open sans",
						"location" : "bottom-center"
					},
					"size" : {
						"canvasWidth" : 590,
						"pieInnerRadius" : "88%",
						"pieOuterRadius" : "70%"
					},
					"data" : {
						"sortOrder" : "value-desc",
						"smallSegmentGrouping" : {
							"enabled" : true
						},
						"content" : [ {
							"label" : "단",
							"value" : 18,
							"color" : "#26ae15"
						}, {
							"label" : "신",
							"value" : 22,
							"color" : "#fdfb2b"
						}, {
							"label" : "짠",
							"value" : 6,
							"color" : "#0f8bf0"
						}, {
							"label" : "매운",
							"value" : 9,
							"color" : "#f63e3e"
						}, {
							"label" : "고소",
							"value" : 7,
							"color" : "#fdfaef"
						}, {
							"label" : "느끼",
							"value" : 1,
							"color" : "#f8eaea"
						}, {
							"label" : "담백",
							"value" : 7,
							"color" : "#64f399"
						}, {
							"label" : "떫은",
							"value" : 0,
							"color" : "#cd57f0"
						}, {
							"label" : "감칠",
							"value" : 4,
							"color" : "#a4fb99"
						}, {
							"label" : "부드러운",
							"value" : 9,
							"color" : "#bbacee"
						}, {
							"label" : "바삭",
							"value" : 1,
							"color" : "#c4d727"
						}, {
							"label" : "쫄깃",
							"value" : 8,
							"color" : "#a9965c"
						}, {
							"label" : "물컹",
							"value" : 0,
							"color" : "#b656b5"
						}, {
							"label" : "구수",
							"value" : 1,
							"color" : "#79aea0"
						} ]
					},
					"labels" : {
						"outer" : {
							"format" : "label-percentage1",
							"pieDistance" : 20
						},
						"inner" : {
							"format" : "none",
							"hideWhenLessThanPercentage" : 1
						},
						"mainLabel" : {
							"fontSize" : 11
						},
						"percentage" : {
							"color" : "#999999",
							"fontSize" : 11,
							"decimalPlaces" : 1
						},
						"value" : {
							"color" : "#cccc43",
							"fontSize" : 11
						},
						"lines" : {
							"enabled" : true
						},
						"truncation" : {
							"enabled" : true
						}
					},
					"effects" : {
						"pullOutSegmentOnClick" : {
							"effect" : "default",
							"speed" : 400,
							"size" : 8
						},
						"highlightSegmentOnMouseover" : false
					},
					"misc" : {
						"colors" : {
							"segmentStroke" : "#000000"
						}
					}
				});
				curTemp = "cold";
				d3.select("#changeBtn").text("뜨거운 음식 보기");
			} else {
				pie = new d3pie("pieChart", {
					"header" : {
						"title" : {
							"text" : "뜨거운 음식",
							"fontSize" : 45,
							"font" : "courier"
						},
						"subtitle" : {
							"text" : "유저가 선택한 뜨거운 음식의 맛",
							"color" : "#999999",
							"fontSize" : 10,
							"font" : "courier"
						},
						"location" : "pie-center",
						"titleSubtitlePadding" : 10
					},
					"footer" : {
						"color" : "#999999",
						"fontSize" : 10,
						"font" : "open sans",
						"location" : "bottom-center"
					},
					"size" : {
						"canvasWidth" : 590,
						"pieInnerRadius" : "70%",
						"pieOuterRadius" : "100%"
					},
					"data" : {
						"sortOrder" : "value-desc",
						"smallSegmentGrouping" : {
							"enabled" : true
						},
						"content" : [ {
							"label" : "단",
							"value" : 10,
							"color" : "#26ae15"
						}, {
							"label" : "신",
							"value" : 3,
							"color" : "#fdfb2b"
						}, {
							"label" : "짠",
							"value" : 18,
							"color" : "#0f8bf0"
						}, {
							"label" : "매운",
							"value" : 20,
							"color" : "#f63e3e"
						}, {
							"label" : "고소",
							"value" : 7,
							"color" : "#fdfaef"
						}, {
							"label" : "느끼",
							"value" : 1,
							"color" : "#f8eaea"
						}, {
							"label" : "담백",
							"value" : 7,
							"color" : "#64f399"
						}, {
							"label" : "떫은",
							"value" : 0,
							"color" : "#cd57f0"
						}, {
							"label" : "감칠",
							"value" : 4,
							"color" : "#a4fb99"
						}, {
							"label" : "부드러운",
							"value" : 9,
							"color" : "#bbacee"
						}, {
							"label" : "바삭",
							"value" : 1,
							"color" : "#c4d727"
						}, {
							"label" : "쫄깃",
							"value" : 8,
							"color" : "#a9965c"
						}, {
							"label" : "물컹",
							"value" : 0,
							"color" : "#b656b5"
						}, {
							"label" : "구수",
							"value" : 1,
							"color" : "#79aea0"
						} ]
					},
					"labels" : {
						"outer" : {
							"format" : "label-percentage1",
							"pieDistance" : 20
						},
						"inner" : {
							"format" : "none",
							"hideWhenLessThanPercentage" : 1
						},
						"mainLabel" : {
							"fontSize" : 11
						},
						"percentage" : {
							"color" : "#999999",
							"fontSize" : 11,
							"decimalPlaces" : 1
						},
						"value" : {
							"color" : "#cccc43",
							"fontSize" : 11
						},
						"lines" : {
							"enabled" : true
						},
						"truncation" : {
							"enabled" : true
						}
					},
					"effects" : {
						"pullOutSegmentOnClick" : {
							"effect" : "default",
							"speed" : 400,
							"size" : 20
						},
						"highlightSegmentOnMouseover" : false
					},
					"misc" : {
						"colors" : {
							"segmentStroke" : "#000000"
						}
					}
				});
				curTemp = "hot";
				d3.select("#changeBtn").text("차가운 음식 보기");
			}
		}
	</script>
</body>
</html>