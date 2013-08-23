var timerId, timeLeft, clientId;

function tradingOpen(duration, clientIdd) {
	
	clientId = clientIdd;
	
	timeLeft = duration * 1000 * 60;
	timerId = setInterval(checkForUpdates, 500);
	var orders = {};
	orders["orders"] = ["Order 1", "Order 2"];
	$('#openOrders').html(_openOrdersTemplate(orders));

}

function checkForUpdates() {
	if (timeLeft > 0) {
		timeLeft -= 500;
	}
	else {
		clearInterval(timerId);
	}
	
}