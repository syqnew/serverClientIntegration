function flotGraph(placeholder) {

	var data = [], totalPoints = 300;

	function getRandomData() {

		if (data.length > 0)
			data = data.slice(1);

		// Do a random walk

		while (data.length < totalPoints) {

			var prev = data.length > 0 ? data[data.length - 1] : 50, y = prev
					+ Math.random() * 10 - 5;

			if (y < 0) {
				y = 0;
			} else if (y > 100) {
				y = 100;
			}

			data.push(y);
		}

		// Zip the generated y values with the x values

		var res = [];
		for ( var i = 0; i < data.length; ++i) {
			res.push([ i, data[i] ])
		}

		return res;
	}

	var plot = $.plot(placeholder, [{ 
		data: [ [12, 43], [56, 34], [67, 23] ], 
		lines : {show: true}
	}, 
	{
		data : [[250, 75], [100,34]], points: {show : true, radius : 3, symbol : "upsideDownTriangle"}, color : "#FF0000" }]);


	function update() {

//		plot.setData([ getRandomData() ]);

		// Since the axes don't change, we don't need to call plot.setupGrid()

		plot.draw();
//		setTimeout(update, 1000);
	}

	update();
}