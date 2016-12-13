<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="https://cdnjs.cloudflare.com/ajax/libs/d3/3.5.5/d3.min.js"></script>
<title>선호음식 월드컵</title>
<script src="http://code.jquery.com/jquery.js"></script>
<link
	href="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css"
	rel="stylesheet" />
<script	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script	src="http://netdna.bootstrapcdn.com/bootstrap/3.1.1/js/bootstrap.min.js"></script>

<style>
@import url(http://fonts.googleapis.com/earlyaccess/jejugothic.css);

.btn-radio {
	width: 100%;
}

.img-radio {
	/*opacity: 0.5;*/
	margin-bottom: 5px;
}

.space-20 {
	margin-top: 20px;
}
/* CSS used here will be applied after bootstrap.css */
.carousel {
	margin-top: 20px;
}

.item .thumb {
	/*width: 25%;*/
	cursor: pointer;
	float: left;
}

.item .thumb img {
	width: 50px;
	height: 50px;
	margin: 2px;
}

.item img {
	width: 100%;
}

.istrain {
	display: none;
}

.istrain2 {
	width: 500px;
	height: 100px;
}

h1, h2 {
	font-family: 'Jeju Gothic', serif;
}

</style>
</head>
<body>
	<div class="istrain2"></div>
	<div class="container" >
		<div class="row istrain">
			<div class="col-md-9">
				<form class="form-horizontal well" role="form">
					<div class="row">
						<div class="col-xs-11">
							<h1 id="round" style="text-align: center; padding-left: 10%">16강</h1>
						</div>
					</div>
					<div class="row">
						<div class="col-xs-5 pull-left">
							<img id="left-food" src=""
								class="img-rounded img-responsive img-radio" style="width: 400px; height: 300px;">
							<button type="button" class="btn btn-primary btn-radio"></button>
							<input type="checkbox" id="left-item" class="hidden">
						</div>
						<div class="col-xs-1">
							<h1>&nbsp;&nbsp;&nbsp;vs</h1>
						</div>
						<div class="col-xs-5 pull-right">
							<img id="right-food" src=""
								class="img-rounded img-responsive img-radio" style="width: 400px; height: 300px;">
							<button type="button" class="btn btn-primary btn-radio"></button>
							<input type="checkbox" id="right-item" class="hidden">
						</div>
					</div>
					<div class="row space-20">
						<div class="col-xs-12">
							<div id="thumbcarousel" class="carousel slide" data-interval="false">
								<div class="carousel-inner">
									<div id="container" class="item active"></div>
								</div>
							</div>
							<!-- /thumbcarousel -->
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>

	<script>
		$(document).ready(function() {
			$.ajax({
				url : '/idle/istrain',
				type : 'get',
				data : {
					"result" : result
				},
				success : function(data) {
				if (data.result === 1) {
					 $(".istrain").css("display","none");
						function test2() {
							console.log("<h2>추가 학습 중입니다.</<h2>");
							$(".istrain2").html("<h2>추가 학습 중입니다.</h2>");
						};
						test2(); // 학습중
						
					} else if (data.result === 0) {
						//test1(); // 이상형월드컵 실행
						  $(".istrain2").html("<h2>음식 이상형 월드컵을 통해 내 취향을 더 확실히 학습시킬 수 있습니다. </h2>");
						$(".istrain").css("display","block");
						setFoodImage();
					}
				},
				error : function(result) {
					alert('실패' + result);
				}
			});

		});
		
		var result="";
		// 음식 배열 -> 추후 .json 파일로 관리
		var foodArray = [ {
			name : "감자수제비",
			ingredient : "밀가루,육수,감자|양파,호박,파,간장,계란",
			taste : "고소한,뜨거운,쫀득한",
			code : "1000100000020002",
			imgPath : "f_002"
		}, {
			name : "제육볶음",
			ingredient : "돼지고기,고추장|파,당근,마늘,소금,후추,간장,양파,설탕",
			taste : "달콤한,매운,쫄깃한,따뜻한",
			code : "1000100000010007",
			imgPath : "f_027"
		}, {
			name : "닭갈비",
			ingredient : "닭고기,고추장,양배추,고구마,양파,대파,배춧잎,가래떡,상추,깻잎",
			taste : "짭짤한,매운,뜨거운,쫄깃한",
			code : "1000100000010003",
			imgPath : "f_006"
		}, {
			name : "삼계탕",
			ingredient : "닭고기|쌀,인삼,마늘,대추,파,소금,후추",
			taste : "고소한,뜨거운,쫀득한",
			code : "1000100000010004",
			imgPath : "f_009"
		}, {
			name : "갈비탕",
			ingredient : "소고기,육수|무,파,마늘,양파,소금",
			taste : "짭짤한,뜨거운,담백한",
			code : "1000100000020003",
			imgPath : "f_004"
		}, {
			name : "순두부찌개",
			ingredient : "순두부,육수,고춧가루|간장,파,양파,계란",
			taste : "매콤한,부드러운,뜨거운",
			code : "1000100000020008",
			imgPath : "f_017"
		}, {
			name : "김치찌개",
			ingredient : "김치,육수,고춧가루|참기름,돼지고기,두부,파",
			taste : "달콤한,얼큰한,뜨거운",
			code : "1000100000020006",
			imgPath : "f_015"
		}, {
			name : "된장찌개",
			ingredient : "된장,육수|소고기,두부,양파,파,고추",
			taste : "구수한,뜨거운",
			code : "1000100000020007",
			imgPath : "f_016"
		}, {
			name : "짜장면",
			ingredient : "면,춘장|감자,양파,돼지고기,양배추,간장,굴소스,마늘,설탕",
			taste : "짭짤한,느끼한,따뜻한,쫀득한",
			code : "3000100000060001",
			imgPath : "f_044"
		}, {
			name : "고추잡채",
			ingredient : "돼지고기,고추기름|양파,피망,버섯,설탕,후추,맛술,간장,참기름,굴소스",
			taste : "매콤한,고소한,따뜻한,쫄깃한",
			code : "3000100000080001",
			imgPath : "f_047"
		}, {
			name : "돈까스",
			ingredient : "돼지고기,밀가루,계란,빵가루|",
			taste : "바삭한,달콤한,담백한,느끼한,따뜻한",
			code : "2000100000050001",
			imgPath : "f_051"
		}, {
			name : "메밀",
			ingredient : "면,간장|오이,다시마,소금,김,맛술",
			taste : "시원한,부드러운",
			code : "2000100000060001",
			imgPath : "f_054"
		}, {
			name : "회덮밥",
			ingredient : "회,밥,고추장|당근,양배추,오이,깻잎",
			taste : "시원한,물컹한,담백한",
			code : "2000100000070001",
			imgPath : "f_055"
		}, {
			name : "햄버거",
			ingredient : "빵,소고기|토마토,상추,마요네즈,케첩",
			taste : "담백한,짭짤한,물컹한,따뜻한",
			code : "4000100000090001",
			imgPath : "f_057"
		}, {
			name : "치킨",
			ingredient : "닭고기,튀김가루|후추",
			taste : "짭짤한,담백한,따뜻한,쫄깃한",
			code : "4000100000050001",
			imgPath : "f_060"
		}, {
			name : "부리또",
			ingredient : "소고기,또띠아,사워크림|설탕,마늘,감자,치즈,소금,할라피뇨",
			taste : "담백한,물컹한,싱거운",
			code : "4000100000090003",
			imgPath : "f_061"
		}];

		function setFoodImage() {
			$('#left-food').attr("src",
					"/resources/fimage/image/" + foodArray[0].imgPath + ".jpg")
					.siblings('button').text(foodArray[0].name);
			$('#right-food').attr("src",
					"/resources/fimage/image/" + foodArray[1].imgPath + ".jpg")
					.siblings('button').text(foodArray[1].name);
		}

		function setThumbImage(path) {
			var imgArray = $('#container');
			var divThumb = $('<div>', {
				dataTarget : "#carousel",
				dataSlideTo : "0",
				class : "thumb"
			}).append($('<img>', {
				src : "/resources/fimage/image/" + path + ".jpg",
				class : "img-rounded"
			}));
			imgArray.append(divThumb);
		}

		var foodArrayForNextRound = [];

		$('.btn-radio').click(
			function(e) {
				var unselected = $('.btn-radio').not(this).removeClass(
						'active').siblings('input').prop('checked', false)
						.siblings('.img-radio').siblings('button').text();

				var selected = $(this).addClass('active').siblings('input')
						.prop('checked', true).siblings('.img-radio')
						.siblings('button').text();

				//선택된 푸드 객체
				var foodSel = (foodArray.find(function(el) {
					return el.name === selected;
				}));
				//선택안된 푸드 객체
				var foodUnsel = (foodArray.find(function(el) {
					return el.name === unselected;
				}));

				//선택된 푸드 객체의 코드
				console.log("선택:", foodSel.name, foodSel.code);
				//선택안된 푸드 객체의 코드
				//		    console.log(foodUnsel.name,foodUnsel.code);
         result +=  foodSel.code+ "," +foodUnsel.code+"|";
				foodArrayForNextRound.push(foodSel);

				if (foodArray.length == 2) {
					if (foodArrayForNextRound.length == 1) { // 최종 선택 완료 
						$(".form-horizontal").html("<h1>완료!</h1>");
						console.log("끝");
						$.ajax({
							url : '/idle/idle',
							dataType : 'text',
							data : {
								result : result
							},
							type : 'GET',
							success : function(json) {
								//alert('성공');
							},
							error : function(result) {
								alert('실패' + result);
							}
						});
						return;
					}
					foodArray = foodArrayForNextRound;
					if (foodArrayForNextRound.length == 2)
						$('#round').text("결승전");
					else
						$('#round').text(foodArray.length + "강");
					foodArrayForNextRound = [];
				} else {
					foodArray = foodArray.slice(2);
				}
				setFoodImage();
				setThumbImage(foodSel.imgPath);
			});
	</script>
</body>
</html>