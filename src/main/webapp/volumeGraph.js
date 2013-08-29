var graphInterval, duration, timeLeft;

function makeVolumeGraph(placeholder, duration) {

	timeLeft = duration * 60 * 1000;
	graphInterval = setInterval(renderVolumeGraph, 1000);
}

function renderVolumeGraph() {
	if (graphInterval > 0) {
		timeLeft -= 1000;
	} else {
		clearInterval(graphInterval);
	}

	var data = [];

	var ajax = $.ajax({
		type : "GET",
		url : "http://localhost:8080/sale",
		dataType : "json",
		success : function(data) {
			for ( var ct = 0; ct < data.length; ct++) {
				list.push([ data[ct]["time"], data[ct]["amount"] ]);
			}
			if (data.length > 0) {
				$.plot(placeholder, [ data ], {
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

		}
	});
}
