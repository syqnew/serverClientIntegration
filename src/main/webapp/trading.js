var timerId, timeLeft, clientId, temp;
var timeInterval = 2000;

function tradingOpen(duration, clientIdd) {

	clientId = clientIdd;
	timeLeft = duration * 1000 * 60;
	timerId = setInterval(checkForUpdates, timeInterval);
}

function checkForUpdates() {
	if (timeLeft > 0) {
		timeLeft -= timeInterval;
	} else {
		clearInterval(timerId);
	}

	var requestStr = "clientId=" + clientId;

	var ajax = $.ajax({
		type : "GET",
		url : "http://localhost:8080/order",
		data : requestStr,
		dataType : "json",
		success : function(data) {
			if (data) {

				var orders = {};
				var ordersList = [];
				for ( var ct = 0; ct < data.length; ct++) {
					var temp = {};
					temp["id"] = data[ct]["id"];
					if (data[ct]["orderType"] == 1 && data[ct]["unfulfilled"] != 0)
						temp["order"] = "Buy " + data[ct]["unfulfilled"]
								+ " shares at Market Price";
					if (data[ct]["orderType"] == 2 && data[ct]["unfulfilled"] != 0)
						temp["order"] = "Sell " + data[ct]["unfulfilled"]
								+ " shares at Market Price";
					if (data[ct]["orderType"] == 3 && data[ct]["unfulfilled"] != 0)
						temp["order"] = "Buy " + data[ct]["unfulfilled"]
								+ " shares at $" + data[ct]["price"];
					if (data[ct]["orderType"] == 3 && data[ct]["unfulfilled"] != 0)
						temp["order"] = "Sell " + data[ct]["unfulfilled"]
								+ " shares at $" + data[ct]["price"];
					
					ordersList.push(temp);
				}
				orders["orders"] = ordersList;
				console.log(orders);
				$('#openOrders').html(_openOrdersTemplate(orders));
			}
		}
	});

}
