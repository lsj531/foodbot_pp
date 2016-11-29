
	//음식 배열 저장 -> 나중에 .json 파일로 관리 합시다.
	var foodArray = [{
		name : "감자수제비",
		ingredient : "밀가루,육수,감자|양파,호박,파,간장,계란",
		taste : "고소한,뜨거운,쫀득한",
		code : "1000100000020002",
		imgPath :"f_002"
	},{
		name : "제육볶음",
		ingredient : "돼지고기,고추장|파,당근,마늘,소금,후추,간장,양파,설탕",
		taste : "달콤한,매운,쫄깃한,따뜻한",
		code : "1000100000010007",
		imgPath : "f_027"
	},{
		name : "닭갈비",
		ingredient : "닭고기,고추장,양배추,고구마,양파,대파,배춧잎,가래떡,상추,깻잎",
		taste : "짭짤한,매운,뜨거운,쫄깃한",
		code : "1000100000010003",
		imgPath : "f_006"
	},{
		name : "삼계탕",
		ingredient : "닭고기|쌀,인삼,마늘,대추,파,소금,후추",
		taste : "고소한,뜨거운,쫀득한",
		code : "1000100000010004",
		imgPath : "f_009"
	},{
		name : "갈비탕",
		ingredient : "소고기,육수|무,파,마늘,양파,소금",
		taste :"짭짤한,뜨거운,담백한",
		code : "1000100000020003",
		imgPath : "f_004"
	},{
		name : "순두부찌개",
		ingredient : "순두부,육수,고춧가루|간장,파,양파,계란",
		taste : "매콤한,부드러운,뜨거운",
		code : "1000100000020008" ,
		imgPath : "f_017"
	},{
		name : "김치찌개",
		ingredient : "김치,육수,고춧가루|참기름,돼지고기,두부,파",
		taste : "달콤한,얼큰한,뜨거운",
		code : "1000100000020006",
		imgPath : "f_015"
	},{
		name : "된장찌개",
		ingredient : "된장,육수|소고기,두부,양파,파,고추",
		taste : "구수한,뜨거운",
		code : "1000100000020007",
		imgPath : "f_016"
	}];

	function setFoodImage() {
		$('#left-food').attr("src","/resources/fimage/images/"+foodArray[0].imgPath+".jpg")
		.siblings('button').text(foodArray[0].name);
		$('#right-food').attr("src","./images/"+foodArray[1].imgPath+".jpg")
		.siblings('button').text(foodArray[1].name);
	}

	function setThumbImage(path) {
		var imgArray = $('#container');
		var divThumb = $('<div>', {
			dataTarget : "#carousel",
			dataSlideTo : "0",
			class : "thumb"
		}).append($('<img>',{
			src : "./images/"+path+".jpg",
			class : "img-rounded"
		}));
		imgArray.append(divThumb);
	}

	setFoodImage();
//	setThumbImage();

	var foodArrayForNextRound = [];

	$('.btn-radio').click(function(e) {
		var unselected = $('.btn-radio').not(this).removeClass('active')
		.siblings('input').prop('checked',false)
		.siblings('.img-radio').siblings('button').text();

		var selected = $(this).addClass('active')
		.siblings('input').prop('checked',true)
		.siblings('.img-radio').siblings('button').text();

		//선택된 푸드 객체
		var foodSel = (foodArray.find(function (el) {
			return el.name === selected;
		}));
		//선택안된 푸드 객체
		var foodUnsel = (foodArray.find(function (el) {
			return el.name === unselected;
		}));

		//선택된 푸드 객체의 코드
		console.log("선택:",foodSel.name,foodSel.code);
		//선택안된 푸드 객체의 코드
//		console.log(foodUnsel.name,foodUnsel.code);

		foodArrayForNextRound.push(foodSel);

		if(foodArray.length == 2) {
			if(foodArrayForNextRound.length == 1) {
				$(".form-horizontal").html("<h1>완료!</h1>");
			}
			foodArray = foodArrayForNextRound;
			foodArrayForNextRound = [];
		} else {
			foodArray = foodArray.slice(2);
		}
		setFoodImage();
		setThumbImage(foodSel.imgPath);
	});


