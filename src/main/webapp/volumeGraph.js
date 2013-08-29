function makeVolumeGraph(placeholder) {
	var data = [ [1, 10], [2, 8], [3, 4], [4, 13], [5, 17], [6, 9] ];

	$.plot(placeholder, [data] , {
		series: {
			bars: {
				show: true,
				barWidth: 0.6,
				align: "center"
			}
		},
		xaxis: {
			mode: "categories",
			tickLength: 0
		}
	});
}
