    var data = [];
    var bandPos = [-1, -1];
    var pos;
    var xdomain = 900;
    var ydomain = 10;
    var colors = ["steelblue"];

    var margin = {
        top: 40,
        right: 40,
        bottom: 50,
        left: 60
    }

    var width = 760 - margin.left - margin.right;
    var height = 450 - margin.top - margin.bottom;
    var zoomArea = {
        x1: 100,
        y1: 0,
        x2: xdomain,
        y2: ydomain
    };

    var drag = d3.behavior.drag();

    //data for testing
    var d1 = [];

    var epoch = 100;
//    var timer = setInterval(function () {
//        d1.push([epoch, Math.random() * 15 + 1]);
//        draw();
//        epoch += 100;
//    },1000);
    function costRequest() {
   		setInterval(function() {
   			$.ajax({
   				url : './trainCost',
   				dataType : 'json',
   				type : 'get',
   				success : function(data) {
   					console.log(data.set[0].cost);
   					d1.push([epoch, data.set[0].cost])
   					draw();
   					if(data.set[0].cost == -1) {
   						//alert("eeeee");
   					} else {
   						$("#currCost").append("<b>"+data.set[0].cost.toFixed(2)+"</b><br>");
   					}
   				 epoch += 100;
   				}, 
   				error : function(result) {
   					console.log("실패   " + result);
   				}
   			});
   		}, 4000);
   	}
    
//    <div class="FirstTimeOperation" style="width: 40%; padding-left: 10px; padding-right: 10px;">
//		<span id="graph_pane"></span><br>
//	</div>

    data.push(d1);
    console.log(data);
    var svg = d3.select("#graph_pane").append("svg")
            .attr("width", width + margin.left + margin.right)
            .attr("height", height + margin.top + margin.bottom)
            .append("g")
            .attr("transform", "translate(" + margin.left + "," + margin.top + ")");


    var x = d3.scale.linear()
            .range([0, width]).domain([100, xdomain]);

    var y = d3.scale.linear()
            .range([height, 0]).domain([0, ydomain]);

    var xAxis = d3.svg.axis()
            .scale(x)
            .orient("bottom");

    var yAxis = d3.svg.axis()
            .scale(y)
            .orient("left");

    var line = d3.svg.line()
//            .interpolate("basis")
            .x(function(d,i) {
//                console.log("x",d);
                return x(d[0]);
            })
            .y(function(d) {
//                console.log("y",d)
                return y(d[1]);
            });

    var band = svg.append("rect")
            .attr("width", 0)
            .attr("height", 0)
            .attr("x", 0)
            .attr("y", 0)
            .attr("class", "band");

    svg.append("g")
            .attr("class", "x axis")
            .call(xAxis)
            .attr("transform", "translate(0," + height + ")");

    svg.append("g")
            .attr("class", "y axis")
            .call(yAxis);

    svg.append("clipPath")
            .attr("id", "clip")
            .append("rect")
            .attr("width", width)
            .attr("height", height);
    
    function draw() {
        if(epoch % 1000 == 0) {
//            draw();
            zoomArea.x1 = 100;
             zoomArea.x2 = epoch + 900;
            dragend();
        }
        for (var idx in data) {
            svg.append("path")
                    .datum(data[idx])
                    .attr("class", "line line" + idx)
                    .attr("clip-path", "url(#clip)")
                    .style("stroke", colors[0])
                    .attr("d", line);
        }
    }
//    draw();
    function remove() {
        svg.selectAll("path").remove();
    }


    var zoomOverlay = svg.append("rect")
            .attr("width", width - 10)
            .attr("height", height)
            .attr("class", "zoomOverlay")
            .call(drag);


    zoom();

    function dragend() {


         remove();
        bandPos = [-1, -1];

        d3.select(".band").transition()
                .attr("width", 0)
                .attr("height", 0)
                .attr("x", bandPos[0])
                .attr("y", bandPos[1]);

        zoom();
    }

    drag.on("dragend", dragend);

    drag.on("drag", function() {
        var pos = d3.mouse(this);
        console.log("drag",pos);
        if (pos[0] < bandPos[0]) {
            d3.select(".band").
            attr("transform", "translate(" + (pos[0]) + "," + bandPos[1] + ")");
        }
        if (pos[1] < bandPos[1]) {
            d3.select(".band").
            attr("transform", "translate(" + (pos[0]) + "," + pos[1] + ")");
        }
        if (pos[1] < bandPos[1] && pos[0] > bandPos[0]) {
            d3.select(".band").
            attr("transform", "translate(" + (bandPos[0]) + "," + pos[1] + ")");
        }
        //set new position of band when user initializes drag
        if (bandPos[0] == -1) {
            bandPos = pos;
            d3.select(".band").attr("transform", "translate(" + bandPos[0] + "," + bandPos[1] + ")");
        }

        d3.select(".band").transition().duration(1)
                .attr("width", Math.abs(bandPos[0] - pos[0]))
                .attr("height", Math.abs(bandPos[1] - pos[1]));
    });

    function zoom() {
        //recalculate domains
        if (zoomArea.x1 > zoomArea.x2) {
            x.domain([zoomArea.x2, zoomArea.x1]);
        } else {
            x.domain([zoomArea.x1, zoomArea.x2]);
        }

        if (zoomArea.y1 > zoomArea.y2) {
            y.domain([zoomArea.y2, zoomArea.y1]);
        } else {
            y.domain([zoomArea.y1, zoomArea.y2]);
        }

        //update axis and redraw lines
        var t = svg.transition().duration(750);11
        t.select(".x.axis").call(xAxis);
        t.select(".y.axis").call(yAxis);

        t.selectAll(".line").attr("d", line);
    }

    var zoomOut = function() {
        x.domain([0, xdomain]);
        y.domain([0, ydomain]);

        var t = svg.transition().duration(750);
        t.select(".x.axis").call(xAxis);
        t.select(".y.axis").call(yAxis);

        t.selectAll(".line").attr("d", line);
    }
