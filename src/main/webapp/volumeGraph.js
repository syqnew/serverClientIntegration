var graphInterval, duration, timeLeft;

function makeVolumeGraph(duration) {

	timeLeft = duration * 60 * 1000;
	graphInterval = setInterval(renderVolumeGraph, 1000);
}

function renderVolumeGraph() {
	if (graphInterval > 0) {
		timeLeft -= 1000;
	} else {
		clearInterval(graphInterval);
	}

	var dataSet = [];

	var ajax = $.ajax({
		type : "GET",
		url : "http://localhost:8080/sale",
		dataType : "json",
		success : function(data) {
			for ( var ct = 0; ct < data.length; ct++) {
				dataSet.push([ data[ct]["time"], data[ct]["amount"] ]);
			}
			$.plot('#placeholder2', [ dataSet ], {
				series : {
					bars : {
						show : true,
						barWidth : 0.6,
						align : "center"
					}
				},
				xaxis : {
					mode : "time",
					timeformat : "%H:%M:%S"
				}
			});

		}
	});
}
