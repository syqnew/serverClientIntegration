var priceGraphInterval, timeLeft, min;
var priceData = [];
var bid = [];
var ask = [];

function priceGraph(place, duration) {
	min = new Date().getTime();
	timeLeft = duration * 60 * 1000;
	priceGraphInterval = setInterval(renderPriceGraph, 1000);

}

function renderPriceGraph() {
	if (timeLeft > 0)
		timeLeft -= 1000;
	else
		clearInterval(priceGraphInterval);

	console.log("past the if else");

	bid = [];
	ask = [];

	var clientRequest = "clientId=" + -1;
	var ajax = $.ajax({
		type : "GET",
		url : "http://localhost:8080/metadata",
		data : clientRequest,
		dataType : "json",
		success : function(data) {
			if (data) {
				var currentTime = new Date().getTime();
				if (data["last"] == 0) {
					priceData.push([]);
				} else {
					priceData.push([ currentTime, data["last"] ]);
				}
				if (data["bid"] == -1 || data["bid"] == 0) {
					bid.push([])
				} else {
					bid.push([ currentTime, data["bid"] ]);
				}
				if (data["ask"] == -1 || data["ask"] == 0) {
					ask.push([]);
				} else {
					ask.push([ currentTime, data["ask"] ]);
				}
				$.plot('#placeholder', [ {
					data : priceData,
					lines : {
						show : true,
						fill : false,
						fillColor : null
					},
					grid : {
						hoverable : true
					}
				}, {
					data : bid,
					points : {
						show : true,
						radius : 3,
						symbol : "upsideDownTriangle"
					},
					color : "#FF0000"
				}, {
					data : ask,
					points : {
						show : true,
						radius : 3,
						symbol : "triangle"
					},
					color : "#FF0000"

				}

				], {
					xaxis : {
						mode : "time",
						timeformat : "%H:%M:%S",
						min : min
					}
				});
			}
		}
	});
}


