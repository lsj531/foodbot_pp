/**
 * Created by minho on 2016. 11. 14..
 */

var width = 800;
var height = 600;
var margin = {"top" : 0, "bottom" : 0 , "left" : 0 , "right" : 0};
var path = "../resources/idleWorldCup/";
var result = "";


function test1() {
	var tasteInfoData = null;
	d3.csv(path+"kFood_ver6.csv", function (data) {
		d3.csv(path+"taste.csv",function (d) {
			tasteInfoData = d;
		});
		var start = 0; 
		var nextContainer = [];

		var g = d3.select("svg") 
		.append("g")
		.style("border","1px black solid")
		.attr("class","foodContainer") 
		.attr("transform", "translate(0,200)");

		// .style("stroke-width","1px");


		function update() { 
			var curData = data.slice(arguments[0], arguments[0] + 2); 
			console.log(curData);
			var appearCircle = g.selectAll("g.appearCircle") 
			.data(curData)
			.enter()
			.append("g")
			.attr("class", "appearCircle")
			.attr("transform", function (d, i) { 
				return "translate(" + (i * 400 + 200) + ", 0)" 
			})
			.style("stroke","black")
			.style("stroke-width","2px")
			.call(appendCircle);

			if(curData.length === 1) {
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
				appearCircle.remove();
				calcAttrValue(null,true);
				return;
			}
		}; 

		function appendCircle(selection) {
			var radius = 150;
			selection.each(function (el){

				var foodImg = d3.select(this)
				.insert("image","text")
				.attr("class","InfoodCircle")
				.attr("xlink:href",function (d) {
					return "../resources/fimage/image/" + d.imgPath + ".jpg";
				})
				.style("border-radius","30")
				.attr("x","-100")
				.attr("y","-100")
				.attr("width","200px")
				.attr("height","200px");

				var foodNameText = d3.select(this)
				.append("text")
				.attr("class","InfoodCircle")
				.attr("text-anchor",'middle')
				.attr("font-size","50px")
				.attr("y",radius + 50)
				.text(el["foodName"]);


				foodImg.on("mouseover",function (d) {
					d3.select(this)
					.style("cursor","pointer")
					.attr("width","220px")
					.attr("height","220px");


					d3.selectAll("td.data")
					.data(d3.values(d))
					.html(function (p) {
						return p;
					});
				});

				foodImg.on("mouseout",function () {
					d3.select(this)
					.attr("width","200px")
					.attr("height","200px");
				}) ;

				foodImg.on("click",function (d) {
					var tastesOfFood = d.taste.split(",");
					calcAttrValue(tastesOfFood);

					var clickIndex = data.indexOf(d);
					var unclickIndex = null;
					//data[clickIndex] <- 여기에 클릭한 음식데이터 정보 있으니까
					//이거 같다가 쓰면되요
					unclickIndex = ((clickIndex % 2) == 1) ? clickIndex - 1 : clickIndex + 1;
					console.log(clickIndex);
					console.log(unclickIndex);
					console.log("클릭된 정보 ",data[clickIndex]);
					console.log("클릭안된 정보 ",data[unclickIndex]);
					result += data[clickIndex].ingredient+ "," +data[unclickIndex].ingredient+"|";

					nextContainer.push(data[clickIndex]);
					start += 2;

					if(start === data.length) {
						start = 0;
						data = nextContainer;
						console.log(nextContainer);
						nextContainer = [];
					}
					
					d3.selectAll(".appearCircle").remove();
					update(start);
				});
			});
		};
		update(start);
	});

	d3.text(path+"modal.html",function (data) {
		d3.select("div").append("div").attr("id","modal").html(data);
	});

	var arr = [];

	for(var i = 0; i < 17; i += 1) {
		arr[i] = 0;
	}

	function calcAttrValue(tastesOfFood, end) {
		if(end === true) {
			for(var i in arr) {
				console.log(arr[i]);
			}
			return;
		}
		for(var i in tasteInfoData) {
			for(var j in tastesOfFood)
				if(tasteInfoData[i]["맛"] === tastesOfFood[j]) {
					var idx = parseInt(tasteInfoData[i]["맛종류"]) - 1;
					arr[idx] += 1;
				}
		}
	};
};
