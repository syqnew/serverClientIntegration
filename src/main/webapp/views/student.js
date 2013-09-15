define([ 'app', 'jquery', 'underscore', 'backbone', 'Handlebars', 'text!templates/student.template',
		'text!templates/timer.template', 'text!templates/openOrders.template', 'text!templates/news.template',
		'text!templates/quotePortfolioTable.template', 'text!templates/cancelOrder.template',
		'text!templates/transactions.template', '../news', '../updateTable', '../volumeGraph', '../flotGraph',
		'../trading', '../transactions', 'flot', 'flotTime', 'flotSymbol', 'bootstrap' ], function(App, $, _, Backbone,
		Handlebars, studentTemplate, timerTemplate, openOrdersTemplate, newsTemplate, quotePortfolioTableTemplate,
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
			initialTemplate["email"] = email;
			$('#loginModal').html(_studentTemplate(initialTemplate));
			$('#shortSellingAlert').hide();
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
					if (data)
						clientId = data["id"];
					if (marketYear == 0)
						// make get requests to the server until market is
						// opened
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
				order["status"] = 0;
				order["client"] = clientId;

				var clientData = "clientId=" + clientId;
				var ajax = $.ajax({
					type : "GET",
					url : "http://localhost:8080/metadata",
					data : clientData,
					dataType : "json",
					success : function(data) {
						// Check if there is a last price
						if (data[0]["last"] != 0) {
							if ((data[0]["last"] * order["amount"]) <= data[1]["cash"]) {
								var ajax2 = $.ajax({
									type : "POST",
									url : "http://localhost:8080/order",
									data : JSON.stringify(order),
									dataType : "json"
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
								dataType : "json"
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
											dataType : "json"
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
								dataType : "json"
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
								dataType : "json"
							});

						} else {
							$('#shortSellingAlert').show();
						}
					}
				});

			});

			// TODO / FIXME
			$('#cancelOrderBtn').on('click', function(event) {
				event.preventDefault();
				var selected = $('#openOrdersCancel').children(':selected');
				console.log(selected);
			});
		}
	});

	/*
	 * Checks to see if the Market has opened
	 */
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

					// Get Year 1 News
					news = new News(data["duration"], clientId);
					news.getStage1News();

					// Start the timer for the session
					var timer = new TraderTimer();
					timer.countdown(data["duration"], '#studentTimer');

					// Show trading orders
					var trading = new Trading(data["duration"], clientId);
					trading.startTrading();

					// Show the graphs of stock price and volume
					priceGraph(data["duration"]);
					makeVolumeGraph(data["duration"]);

					// Show transactions made
					transaction = new Transaction(clientId, data["duration"]);
					transaction.getTransactions();
				} else if (data["year"] == 2 && marketYear == 1) {
					// clear the timer that checks the state of the market
					clearInterval(marketInterval);
					marketYear = data["year"];

					// get Year 2 News
					news.getStage2News();

					// Start timer for year 2
					var timer = new TraderTimer();
					timer.countdown(data["duration"], '#studentTimer');

					// Show the trading orders
					var trading = new Trading(data["duration"], clientId);
					trading.startTrading();

					// Reveal the graphs
					priceGraph(data["duration"]);
					makeVolumeGraph(data["duration"]);

					// Update transactions
					transaction.getTransactions(clientId, data["duration"]);
				}
			}
		});
	}

	return StudentView;

});
