<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>Bootply snippet - Chat panel</title>
<meta name="generator" content="Bootply" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<meta name="description" content="Chat panel" />
<link
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.3/css/bootstrap.min.css"
	rel="stylesheet">
<link
	href="//netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css"
	rel="stylesheet" id="bootstrap-css">
<!--[if lt IE 9]>
    <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <![endif]-->

<link href="/resources/search/search.css" rel="stylesheet">
<style>
#btt {
	border: 0px;
	background-color: rgba(245, 245, 245, 0.9);
	width: 40px;
	overflow: hidden;
}
</style>
</head>
<!-- HTML code from Bootply.com editor -->
<body>

	<div class="container">
		<div class="row">
			<div class="col-md-1"></div>
			<div class="col-md-5">
				<div class="panel-color">
					<div class="panel spanel panel-primary">
						<button id="btt" type="button" aria-hidden="true">
							<b>x</b>
						</button>
						<div class="search-panel-body">
							<ul class="search" id="resultList">
								<li class="left clearfix" id="listEl" style="display: none">
									<div class="search-body clearfix">
										<div>
											<a id="imgLink" href="#post-1001"> <img id="imgUrl"
												class="col-xs-12" src="" alt="식당이미지">
												<div class="clearfix"></div>
											</a>
										</div>
										<strong class="pull-left primary-font"> <span
											id="title" class="glyphicon glyphicon-home"></span>
										</strong> <strong class="pull-right primary-font"> <span
											id="addr" class="glyphicon glyphicon-home"></span>
										</strong>
									</div>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-7" id="post">
				<article id="">
					<h1 class="title">
						상세 정보
						<!--<img id="imgUrl" class="" src="" alt="식당이미지">-->
						<button type="button" class="close" aria-hidden="true">×</button>
					</h1>
					<div>
						<div id="map" style="width: 500px; height: 500px;"></div>
						<div id="imgSlide">이미지슬라이드 자리.</div>
						<div class="info">
							<a href="#"><span id="name" class="glyphicon glyphicon-home"></span></a>
							<a href="#"><span class="glyphicon glyphicon-road">&nbsp;길찾기</span></a>
							<a href="#"><span id="phone" class="glyphicon glyphicon-earphone"></span></a>
							<div class="clearfix"></div>
						</div>
					</div>
				</article>
			</div>
		</div>
	</div>
	<script type='text/javascript'
		src="//ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
	<script type='text/javascript'
		src="//netdna.bootstrapcdn.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="//apis.daum.net/maps/maps3.js?apikey=88485d00abcd6a98f6f09e1b96a94245"></script>
	<script src="http://code.jquery.com/jquery.js"></script>
	<!-- JavaScript jQuery code from Bootply.com editor  -->
	<script type='text/javascript'></script>
	<script>
    (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
                (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
            m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
    })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
    ga('create', 'UA-40413119-1', 'bootply.com');
    ga('send', 'pageview');
</script>
	<script type="text/javascript">
        function findUserCoord(resultFood, searchFilterInfo) {
        	 console.log("resultFood");
            var url = "https://apis.daum.net"
            url += "/local/geo/addr2coord";
            url += "?apikey=88485d00abcd6a98f6f09e1b96a94245";
            url += "&q="+searchFilterInfo.addr;
            url += "&output=json";
            $.ajax({
                url: url,
                type: 'GET',
                cache: false,
                dataType : 'jsonp',
                success: function(data) {
                    console.log(data.channel.item[0]["lat"]);
                    console.log(data.channel.item[0]["lng"]);
                    searchFilterInfo.coord = {
                        lat : data.channel.item[0]["lat"] ,
                        lng : data.channel.item[0]["lng"]
                    };
                    findRestaurantByFoodName(resultFood,searchFilterInfo);
                },
                error:function(request,status,error){
                    alert("다시 시도해주세요.\n" + "code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
                }
            });
        }
        
        function findRestaurantByFoodName(resultFood, searchFilterInfo) {
            var url = "https://apis.daum.net";
            url += "/local/v1/search/keyword.json";
            url += "?apikey=88485d00abcd6a98f6f09e1b96a94245";
            url += "&query="+resultFood;
            url += "&location="+searchFilterInfo.coord.lat+","+searchFilterInfo.coord.lng;
            url += "&radius="+searchFilterInfo.radius;
            console.log(url);
            $.ajax({
                url: url,
                type: 'GET',
                cache: false,
                dataType : 'jsonp',
                success: function(data) {
                    //페이지에 더 있는거 추가해야함!!!
//                console.log(data.channel);
                    var resultList = data.channel.item;
//                    console.log(resultList);
                    initHandler(resultList);
                    createResultTable(resultList);
                    console.log(resultList);
                },
                error:function(request,status,error){
                    alert("다시 시도해주세요.\n" + "code:"+request.status+"\n"+"message:"+request.responseText+"\n"+"error:"+error);
                }
            });
        }
        
        function createResultTable(resultList) {
            var resultUl = $('#resultList');

            for(var i in resultList) {
                var imgUrl = resultList[i].imageUrl;
                if(resultList[i].imageUrl == "") {
                    console.log("이미지 없다");
                    imgUrl = "/resources/img/no_image.png" 
                  } 
                var title = resultList[i].title;
                var addr = resultList[i].address;

                var listEl = $('#listEl').clone(true);
                listEl.find('#imgLink').attr("href","#post-"+i);
                listEl.find('#imgUrl').attr("src",imgUrl);
                listEl.find('#title').text(title);
                listEl.find('#addr').text(addr);
                listEl.show();
                resultUl.append(listEl);
            }
            
            console.log("createResultTable 성공");
//            createMapModal(resultList);
        }
        
        function createMapModal(resultList, postId) {
        console.log("createMapModal start");
            var selectedIndex = postId.slice(6);
            var restaurantInfo = resultList[selectedIndex];
            var modalDiv = $('#post');
            modalDiv.children().attr("id",postId.slice(1));

//            createImgSlide(resultList);

            $('#phone').html("&nbsp"+restaurantInfo.phone);
            $('#name').html("&nbsp"+restaurantInfo.title);

            var lat = restaurantInfo.latitude;
            var lng = restaurantInfo.longitude;

            var container = document.getElementById('map'); //지도를 담을 영역의 DOM 레퍼런스
            var options = { //지도를 생성할 때 필요한 기본 옵션
                center: new daum.maps.LatLng(lat, lng), //지도의 중심좌표.
                level: 3 //지도의 레벨(확대, 축소 정도)
            };
            var map = new daum.maps.Map(container, options); //지도 생성 및 객체 리턴
            var markerPosition  = new daum.maps.LatLng(lat, lng);
            // 마커를 생성합니다
            var marker = new daum.maps.Marker({
                position: markerPosition
            });
            // 마커가 지도 위에 표시되도록 설정합니다
            marker.setMap(map);
            console.log(map);
            console.log("createMapModal end");
        }
        
        function createImgSlide (resultList) {
            console.log(resultList);
            var imgUl = $('#imgList');

            for(var i in resultList) {
            	if(resultList[i].imageUrl == "") {
            		console.log("이미지 없다");
            		resultList[i].imageUrl = "no_image.png" 
            	} 
                var li = $('<li>',{
                    width : "20px",
                    height : "20px"
                }).append(
                        $('<img>',{
                          src : resultList[i].imageUrl,
                          alt : "식당이미지"
                        })
                );
                console.log(li);
                li.appendTo(imgUl);
            }
            console.log(imgUl);
            return imgUl;
        }
        function initHandler(resultList) {
//            console.log(resultList);
            $('a[href^="#post-"]').on('click', function(event) {
                var postId = $(this).attr("href");
                createMapModal(resultList, postId);
                event.preventDefault();
                $('article.active').removeClass('active');
                $('#comments').removeClass('active');

                var percentage = parseInt($(window).width()) - parseInt($(this).css('width'));
                percentage = percentage / parseInt($(window).width());
                percentage = percentage * 100;

                if (percentage <= 20) {
                    $('#posts').removeClass('open');
                }
                
                console.log(percentage);
                // THIS IS WHERE AJAX CODE WOULD GO TO LOAD ARTICLES DYNAMICALLY
                $($(this).attr('href')).addClass('active');
            });

            $('article > .title > .close').on('click', function(event) {
                event.preventDefault();
                $('#comments').removeClass('active');
                $(this).closest('article').removeClass('active');
            });
        }
      
        function searchRestaurant(food) {
            var resultFood = food;
            console.log("1 실행")
            //현제 세션에 있는 유저가 설정한 검색필터 정보
            var searchFilterInfo = {
                addr : "서울특별시 성북구 삼선동2가 389",
                coord : null ,
                radius : 1000
            };
            findUserCoord(resultFood, searchFilterInfo);
        }
        $(function () {
            $("#btt").on("click",function() {
              $('.searchPage').animate({'left':'-30%'},500).css("display","none");
              });
    });
</script>
</body>
</html>