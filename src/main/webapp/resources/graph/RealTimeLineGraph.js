/**
 * Created by minho on 2016. 11. 22..
 */


// g_reloadTimer = setInterval(reloadManager,500);

var current_watt_for_graph = 0;


function graphStart(epoch,cost){
    var n = 100,    // x 축 범위를 위한 변수
        random = d3.random.normal(0, 0),
        data = d3.range(n).map(random);   // 0~0 으로 x축(60) 범위를 초기화 한다.

    var margin = {top: 20, right: 20, bottom: 20, left: 40},    // 그래프 상하좌우 공백
        width = 500 - margin.left - margin.right,                      // 그래프 x 크기
        height = 280 - margin.top - margin.bottom;                 // 그래프 y 크기

    var x = d3.scale.linear()              // 그래프의 너비에 맞추어 x  축을 0~59로 나눈다.
        .domain([0, n - 1])
        .range([0, width]);

    var y = d3.scale.linear()             // 그래프의 높이에 맞추어 0~100으로 나눈다.
        .domain([0, 8])
        .range([height, 0]);

    var line = d3.svg.line()               // svg 라인이 설정되는(그려지는) 방법을 알려준다.
        .x(function(d, i) { return x(i); })
        .y(function(d, i) { return y(d); });


    // div id 가 "graph_pane" 인것에 svg 형식의 그래프 그려준다.
    var svg = d3.select("#graph_pane").append("svg")
        .attr("width", width + margin.left + margin.right)       // 너비 설정
        .attr("height", height + margin.top + margin.bottom) // 높이 설정
        .append("g")                                                             //  그룹 "g"  속성  추가
        .attr("transform", "translate(" + margin.left + "," + margin.top + ")");  // 변환(transform) 속성 설정

    svg.style("fill", "black")    // 그래프 색상 설정
console.dir(svg.style);
    svg.append("defs")
        .append("clipPath")   // clipPath 설정 (보여지길 원하는 사이즈 설정, 이외는 버림)
        .attr("id", "clip")
        .append("rect")  // rect 설정
        .attr("width", width)
        .attr("height", height);

    svg.append("g")   // x 축에 대한 그룹 엘리먼트 설정
        .attr("class", "x axis")
        .attr("transform", "translate(0," + y(0) + ")")
        .call(d3.svg.axis().scale(x).orient("bottom"));

    svg.append("g")  // y 축에 대한 그룹 엘리먼트 설정
        .attr("class", "y axis")
        .call(d3.svg.axis().scale(y).orient("left"));

    var path = svg.append("g")
        .attr("clip-path", "url(#clip)")
        .append("path")    // 실제 데이터가 그려질 패스에 대한 설정
        .datum(data)
        .attr("class", "line")
        .attr("d", line);

    path    // 실제 데이터가 그려질 패스에 대한 스타일 설정
        .style('stroke-width', 1)
        .style('stroke', 'black')
        .style('fill','none');
    
    if(cost !== -1) {
    	
    	tick(cost);
    } else {
    	alert("aa");
    }
    function tick() {
        // 새로운 데이터를 뒤에 추가한다. (ajax 를 통해 1초에 한번씩 가장 최신 데이터를 가져온것을 넣어줌)
        data.push(current_watt_for_graph);
        // 라인을 PATH 방식으로 그리자!!!
        path
            .attr("d", line)
            .attr("transform", null)    // 기존 변환 행렬을 초기화하고
            .transition()                 // 변환 시작
            .duration(4000)          // 1초동안 애니매이션하게 설정
            .ease("linear")           // ease 보간을 리니어로 처리한다.(https://github.com/mbostock/d3/wiki/Transitions#d3_ease)
            // .attr("transform", "translate(" + x(-1) + ",0)")   //  변환행렬 설정   # 패스를 다시 그리는 방식이                                                                                     //  아니라 좌표를 변환함으로써 출렁거리는것을 막는다.
            .each("end", tick);    //tick 함수 계속 호출
        //가장 오래된 데이터를 제거한다.
        data.shift();
    }
}

graphStart();


 var graph_timer = null;
 var graph_time_interval = 1000; // Polling data once per 1 sec

 function costRequest() {
		setInterval(function() {
			$.ajax({
				url : './trainCost',
				dataType : 'json',
				type : 'get',
				success : function(data) {
					console.log(data.set[0].cost);
					console.log(data.set[0].epoch);
					//console.log(data.a[0].msg);
					//console.log(data.a.length);
					current_watt_for_graph = data.set[0].cost;
					if(data.set[0].cost == -1) {
						alert("eeeee");
						//clearInterval(this);
					} else {
						$("#currCost").append("<b>"+data.set[0].cost.toFixed(2)+"</b><br>");
					}
					
				}, 
				error : function(result) {
					console.log("실패   " + result);
				}
			});
		}, 5000);
	}