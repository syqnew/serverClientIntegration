function Trading(duration, clientId) {
	this.duration = duration;
	this.clientId = clientId;
	this.timeLeft = duration * 60 * 1000;
	this.timerId = null;
}

Trading.prototype.constructor = Trading;

Trading.prototype.startTrading = function() {
	this.timerId = setInterval(this.checkForUpdates, 1000);
};

Trading.prototype.checkForUpdates = function() {
	if (this.timeLeft > 0)
		this.timeLeft -= 1000;
	else
		clearInterval(timerId);

	var shares;
	var cash;
	var price;

	var requestStr = "clientId=" + this.clientId;
	var ajax = $
			.ajax({
				type : "GET",
				url : "http://localhost:8080/metadata",
				data : requestStr,
				dataType : "json",
				success : function(data) {
					price = data[0]["last"];
					shares = data[1]["shares"];
					cash = data[1]["cash"];

					var ajax2 = $
							.ajax({
								type : "GET",
								url : "http://localhost:8080/order",
								data : requestStr,
								dataType : "json",
								success : function(data) {
									if (data) {

										var ordersList = [];
										for ( var ct = 0; ct < data.length; ct++) {
											var temp = {};
											temp["id"] = data[ct]["id"];
											if (data[ct]["orderType"] == 1
													&& data[ct]["unfulfilled"] > 0
													&& (price
															* data[ct]["unfulfilled"] <= cash)) {

												temp["order"] = "Buy "
														+ data[ct]["unfulfilled"]
														+ " shares at Market Price";
												temp["value"] = data[ct]["id"];
											} else if (data[ct]["orderType"] == 2
													&& data[ct]["unfulfilled"] > 0
													&& (data[ct]["unfulfilled"] <= shares)) {
												temp["order"] = "Sell "
														+ data[ct]["unfulfilled"]
														+ " shares at Market Price";
												temp["value"] = data[ct]["id"];
											} else if (data[ct]["orderType"] == 3
													&& data[ct]["unfulfilled"] > 0
													&& (data[ct]["price"]
															* data[ct]["unfulfilled"] <= cash)) {
												temp["order"] = "Buy "
														+ data[ct]["unfulfilled"]
														+ " shares at $"
														+ data[ct]["price"];
												temp["value"] = data[ct]["id"];
											} else if (data[ct]["orderType"] == 3
													&& data[ct]["unfulfilled"] > 0
													&& (data[ct]["unfulfilled"] <= shares)) {

												temp["order"] = "Sell "
														+ data[ct]["unfulfilled"]
														+ " shares at $"
														+ data[ct]["price"];
												temp["value"] = data[ct]["id"];
											} else {
												//
												console.log(data[ct]);
//												var ajax = $.ajax({
//													type : "POST",
//													url : "http://localhost:8080/sale",
//													data : data[ct],
//													dataType : "json"
//												});
											}

											ordersList.push(temp);
										}
										$('#openOrdersTable').html(
												_openOrdersTemplate(ordersList));
										$('#cancelOrder').html(_cancelOrderTemplate(ordersList));
									}
								}
							});
				}
			});

};



