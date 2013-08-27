define([ 'app', 'jquery', 'underscore', 'backbone', 'Handlebars', 'flot',
		'text!templates/student.template', 'text!templates/graph.template',
		'text!templates/timer.template', 'text!templates/openOrders.template',
		'text!templates/news.template', 'text!templates/quotePortfolioTable.template', 
		'../timer', '../news', '../flotGraph',
		'../trading', 'flotTime', 'bootstrap' ], function(App, $, _, Backbone,
		Handlebars, flot, studentTemplate, graphTemplate, timerTemplate,
		openOrdersTemplate, newsTemplate, quotePortfolioTableTemplate) {

	_studentTemplate = Handlebars.compile(studentTemplate);
	_graphTemplate = Handlebars.compile(graphTemplate);
	_timerTemplate = Handlebars.compile(timerTemplate);
	_openOrdersTemplate = Handlebars.compile(openOrdersTemplate);
	_newsTemplate = Handlebars.compile(newsTemplate);
	_quotePortfolioTableTemplate = Handlebars.compile(quotePortfolioTableTemplate);

	var marketYear = 0;
	var marketInterval, clientId, news;
	var initialTemplate = {};
	initialTemplate["news"] = [ {
		"new" : "Market is closed."
	} ];

	var StudentView = Backbone.View.extend({
		el : $('#loginModal'),
		render : function(email) {
			initialTemplate["email"] = email;
			$('#loginModal').html(_studentTemplate(initialTemplate));
			$('#graph').html(_graphTemplate());
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
						// make get requests to the server until Market
						// is opened
						marketInterval = setInterval(checkMarketState, 500);
				}
			});

			$('#marketBuyBtn').on("click", function(event) {
				event.preventDefault();
				var data = {};
				data["orderType"] = 1;
				data["amount"] = $('#size').val();
				data["price"] = -1;
				data["time"] = new Date().getTime();
				data["unfulfilled"] = $('#size').val();
				// status 0 -> OK, 10 -> cancelled
				data["status"] = 0;
				data["client"] = clientId;

				var ajax = $.ajax({
					type : "POST",
					url : "http://localhost:8080/order",
					data : JSON.stringify(data),
					dataType : "json",
					success : function(data) {
						console.log("success");
					}
				});
				$('#size').val("");
				$('#price').val("");
			});

			$('#marketSellBtn').on("click", function(event) {
				event.preventDefault();
				var order = {};
				order["orderType"] = 2;
				order["amount"] = $('#size').val();
				order["price"] = -1;
				order["time"] = new Date().getTime();
				order["unfulfilled"] = $('#size').val();
				// status 0 -> OK, 10 -> cancelled
				order["status"] = 0;
				order["client"] = clientId;
				
				var ajax = $.ajax({
					type : "GET",
					url : "http://localhost:8080/client",
					data : clientData,
					dataType : "json",
					success : function(data) {
						if ( data["shares"] >= order["amount"]) {
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

				
				$('#size').val("");
				$('#price').val("");
			});

			$('#limitBuyBtn').on("click", function(event) {
				event.preventDefault();
				var data = {};
				data["orderType"] = 3;
				data["amount"] = $('#size').val();
				data["price"] = $('#price').val();
				data["time"] = new Date().getTime();
				data["unfulfilled"] = $('#size').val();
				// status 0 -> OK, 10 -> cancelled
				data["status"] = 0;
				data["client"] = clientId;

				var ajax = $.ajax({
					type : "POST",
					url : "http://localhost:8080/order",
					data : JSON.stringify(data),
					dataType : "json",
					success : function(data) {
						console.log("success");
					}
				});
				$('#size').val("");
				$('#price').val("");
			});

			$('#limitSellBtn').on("click", function(event) {
				event.preventDefault();
				var data = {};
				data["orderType"] = 4;
				data["amount"] = $('#size').val();
				data["price"] = $('#price').val();
				data["time"] = new Date().getTime();
				data["unfulfilled"] = $('#size').val();
				// status 0 -> OK, 10 -> cancelled
				data["status"] = 0;
				data["client"] = clientId;

				var ajax = $.ajax({
					type : "POST",
					url : "http://localhost:8080/order",
					data : JSON.stringify(data),
					dataType : "json",
					success : function(data) {
						console.log("success");
					}
				});
				$('#size').val("");
				$('#price').val("");

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
					news = new News(data["duration"], clientId);
					news.getStage1News();
					var timer = new TraderTimer();
					timer.countdown(data["duration"], '#studentTimer');
					tradingOpen(data["duration"], clientId);
				}
				else if (data["year"] == 2 && marketYear == 1) {
					clearInterval(marketInterval);
					marketYear = data["year"];
					news.getStage2News();
					var timer = new TraderTimer();
					timer.countdown(data["duration"], '#studentTimer');
				} 
			}
		});
	}

	return StudentView;

});
