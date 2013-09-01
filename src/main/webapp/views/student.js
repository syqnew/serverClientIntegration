define([ 'app', 'jquery', 'underscore', 'backbone', 'Handlebars', 'text!templates/student.template',
		'text!templates/timer.template', 'text!templates/openOrders.template', 'text!templates/news.template',
		'text!templates/quotePortfolioTable.template', 'text!templates/cancelOrder.template',
		'text!templates/transactions.template', '../news', '../updateTable', '../volumeGraph', '../flotGraph',
		'../trading', '../transactions', 'flot', 'flotTime', 'flotSymbol', 'bootstrap' ], function(App, $, _, Backbone, Handlebars,
		studentTemplate, timerTemplate, openOrdersTemplate, newsTemplate, quotePortfolioTableTemplate,
		cancelOrderTemplate, transactionsTemplate) {

	_studentTemplate = Handlebars.compile(studentTemplate);
	_timerTemplate = Handlebars.compile(timerTemplate);
	_openOrdersTemplate = Handlebars.compile(openOrdersTemplate);
	_newsTemplate = Handlebars.compile(newsTemplate);
	_quotePortfolioTableTemplate = Handlebars.compile(quotePortfolioTableTemplate);
	_cancelOrderTemplate = Handlebars.compile(cancelOrderTemplate);
	_transactionsTemplate = Handlebars.compile(transactionsTemplate);

	var marketYear = 0;
	var marketInterval, clientId, news, trading, transaction;
	var initialTemplate = {};
	initialTemplate["news"] = [ {
		"new" : "Market is closed."
	} ];

	var StudentView = Backbone.View.extend({
		el : $('#loginModal'),
		render : function(email) {
			// testin

			initialTemplate["email"] = email;
			$('#loginModal').html(_studentTemplate(initialTemplate));
			$('#shortSellingAlert').hide();
			// $('#priceGraph').html(_priceGraphTemplate());
			// $('#volumeGraph').html(_volumeGraphTemplate());
			$('#marketNews').html(_newsTemplate({
				"news" : [ {
					"new" : "Market is closed"
				} ]
			}));
			$('#quotePortfolioTable').html(_quotePortfolioTableTemplate({
				"last" : "-",
				"low" : "-",
				"high" : "-",
				"bid" : "-",
				"bidSize" : 0,
				"ask" : "-",
				"askSize" : 0,
				"volume" : 0,
				"quantity" : 400,
				"crlTotal" : 0,
				"cashTotal" : 10000,
				"total" : 10000
			}));
			$('button').prop('disabled', true);
			$('#openOrders').html(_openOrdersTemplate([]));
			$('#transactionBlock').html(_transactionsTemplate([]));

			// get id assigned to this client
			var clientData = "email=" + email;
			var ajax = $.ajax({
				type : "GET",
				url : "http://localhost:8080/client",
				data : clientData,
				dataType : "json",
				success : function(data) {
					console.log(data);
					if (data)
						clientId = data["id"];
					if (marketYear == 0)
						// make get requests to the server until
						// Market
						// is opened
						marketInterval = setInterval(checkMarketState, 500);
				}
			});

			$('#marketBuyBtn').on("click", function(event) {
				event.preventDefault();
				var order = {};
				order["orderType"] = 1;
				order["amount"] = $('#size').val();
				order["price"] = -1;
				order["time"] = new Date().getTime();
				order["unfulfilled"] = $('#size').val();
				// status 0 -> OK, 10 ->
				// cancelled
				order["status"] = 0;
				order["client"] = clientId;

				var clientData = "clientId=" + clientId;
				var ajax = $.ajax({
					type : "GET",
					url : "http://localhost:8080/metadata",
					data : clientData,
					dataType : "json",
					success : function(data) {
						if (data[0]["last"] != 0) {
							if ((data[0]["last"] * order["amount"]) <= data[1]["cash"]) {
								var ajax2 = $.ajax({
									type : "POST",
									url : "http://localhost:8080/order",
									data : JSON.stringify(order),
									dataType : "json",
									success : function(data) {
										console.log("success");
									}
								});
								$('#size').val("");
								$('#price').val("");
							} else {
								$('#shortSellingAlert').show();
							}

						} else {
							var ajax2 = $.ajax({
								type : "POST",
								url : "http://localhost:8080/order",
								data : JSON.stringify(order),
								dataType : "json",
								success : function(data) {
									console.log("success");
								}
							});
							$('#size').val("");
							$('#price').val("");
						}
					}
				});

			});

			$('#marketSellBtn').on("click", function(event) {
				event.preventDefault();
				var order = {};
				order["orderType"] = 2;
				order["amount"] = $('#size').val();
				order["price"] = -1;
				order["time"] = new Date().getTime();
				order["unfulfilled"] = $('#size').val();
				// status 0 -> OK, 10 ->
				// cancelled
				order["status"] = 0;
				order["client"] = clientId;

				var clientData = "email=" + email;
				var ajax = $.ajax({
					type : "GET",
					url : "http://localhost:8080/client",
					data : clientData,
					dataType : "json",
					success : function(data) {
						if (data["shares"] >= order["amount"]) {
							$('#size').val("");
							$('#price').val("");
							var ajax2 = $.ajax({
								type : "GET",
								url : "http://localhost:8080/client",
								data : clientData,
								dataType : "json",
								success : function(data) {
									if (data["shares"] >= order["amount"]) {
										var ajax2 = $.ajax({
											type : "POST",
											url : "http://localhost:8080/order",
											data : JSON.stringify(order),
											dataType : "json",
											success : function(data) {
												console.log("success");
											}
										});
									}
								}
							});
						} else {
							$('#shortSellingAlert').show();
						}
					}
				});

			});

			$('#limitBuyBtn').on("click", function(event) {
				event.preventDefault();
				var order = {};
				order["orderType"] = 3;
				order["amount"] = $('#size').val();
				order["price"] = $('#price').val();
				order["time"] = new Date().getTime();
				order["unfulfilled"] = $('#size').val();
				// status 0 -> OK, 10 ->
				// cancelled
				order["status"] = 0;
				order["client"] = clientId;

				var clientData = "clientId=" + clientId;
				var ajax = $.ajax({
					type : "GET",
					url : "http://localhost:8080/metadata",
					data : clientData,
					dataType : "json",
					success : function(data) {
						if (order["amount"] * order["price"] <= data[1]["cash"]) {
							var ajax2 = $.ajax({
								type : "POST",
								url : "http://localhost:8080/order",
								data : JSON.stringify(order),
								dataType : "json",
								success : function(data) {
									console.log("success");
								}
							});
							$('#size').val("");
							$('#price').val("");
						} else {
							$('#shortSellingAlert').show();
						}
					}
				});

			});

			$('#limitSellBtn').on("click", function(event) {
				event.preventDefault();
				var order = {};
				order["orderType"] = 4;
				order["amount"] = $('#size').val();
				order["price"] = $('#price').val();
				order["time"] = new Date().getTime();
				order["unfulfilled"] = $('#size').val();
				// status 0 -> OK, 10 ->
				// cancelled
				order["status"] = 0;
				order["client"] = clientId;
				var clientData = "email=" + email;
				var ajax = $.ajax({
					type : "GET",
					url : "http://localhost:8080/client",
					data : clientData,
					dataType : "json",
					success : function(data) {
						if (data["shares"] >= order["amount"]) {
							$('#size').val("");
							$('#price').val("");
							var ajax2 = $.ajax({
								type : "POST",
								url : "http://localhost:8080/order",
								data : JSON.stringify(order),
								dataType : "json",
								success : function(data) {
									console.log("success");
								}
							});

						} else {
							$('#shortSellingAlert').show();
						}
					}
				});

			});

			$('#cancelOrderBtn').on('click', function(event) {
				event.preventDefault();
				var e = document.getElementById("openOrdersCancel");
				var selected = e.options[e.selectedIndex].value;
				console.log(selected);
			});

		}
	});

	function checkMarketState() {
		var ajax = $.ajax({
			type : "GET",
			url : "http://localhost:8080/admin",
			dataType : "json",
			success : function(data) {
				if (data["year"] == 1 && marketYear == 0) {
					$('#marketNews').html(_newsTemplate({
						"news" : [ {
							"new" : "Market is open"
						} ]
					}));
					$('button').prop('disabled', false);
					marketYear = data["year"];
					updateTable(clientId, data["duration"]);
					news = new News(data["duration"], clientId);
					news.getStage1News();
					var timer = new TraderTimer();
					timer.countdown(data["duration"], '#studentTimer');
					var trading = new Trading(data["duration"], clientId);
					trading.startTrading();
					priceGraph(data["duration"]);
					makeVolumeGraph(data["duration"]);
					transaction = new Transaction(clientId, data["duration"]);
					transaction.getTransactions();
				} else if (data["year"] == 2 && marketYear == 1) {
					clearInterval(marketInterval);
					marketYear = data["year"];
					news.getStage2News();
					var timer = new TraderTimer();
					timer.countdown(data["duration"], '#studentTimer');
					var trading = new Trading(data["duration"], clientId);
					trading.startTrading();
					priceGraph(data["duration"]);
					makeVolumeGraph(data["duration"]);
					transaction.getTransactions(clientId, data["duration"]);
				}
			}
		});
	}

	return StudentView;

});
