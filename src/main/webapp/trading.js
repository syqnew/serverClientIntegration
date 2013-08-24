var timerId, timeLeft, clientId;

function tradingOpen(duration, clientIdd) {
	
	clientId = clientIdd;
	
	timeLeft = duration * 1000 * 60;
	timerId = setInterval(checkForUpdates, 500);
	var orders = {};
	orders["orders"] = [{order: "Order 1", id: "order1Button"},
	                    {order: "Order 2", id: "order2Button"}];
	$('#openOrders').html(_openOrdersTemplate(orders));
	
	$('#order1Button').on("click", function(event) {
		event.preventDefault();
		console.log("order 1 was clicked");
	});
	$('#order2Button').on("click", function(event) {
		event.preventDefault();
		console.log("order 2 was clicked");
	});

}

function checkForUpdates() {
	if (timeLeft > 0) {
		timeLeft -= 500;
	}
	else {
		clearInterval(timerId);
	}
	
	var requestStr = "clientId=" + clientId;
	
	var ajax = $.ajax({
		type : "GET",
		url : "http://localhost:8080/order",
		data : requestStr, 
		dataType : "json",
		success : function(data) {
			console.log(data);
		}
	});
	
}

// TODO
function createListenersCancel () {
	
}