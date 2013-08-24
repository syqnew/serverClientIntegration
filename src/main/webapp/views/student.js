define([ 'app', 'jquery', 'underscore', 'backbone', 'Handlebars', 'flot',
		'text!templates/student.template', 'text!templates/graph.template',
		'text!templates/timer.template', 'text!templates/openOrders.template', 
		'../timer', '../flotGraph', '../trading',
		'flotTime', 'bootstrap' ], function(App, $, _, Backbone, Handlebars,
		flot, studentTemplate, graphTemplate, timerTemplate, openOrdersTemplate) {

	_studentTemplate = Handlebars.compile(studentTemplate);
	_graphTemplate = Handlebars.compile(graphTemplate);
	_timerTemplate = Handlebars.compile(timerTemplate);
	_openOrdersTemplate = Handlebars.compile(openOrdersTemplate);

	var marketYear = 0;
	var marketInterval;
	var clientId;
	var initialTemplate = {};
	initialTemplate["news"] = [{"new": "Market is closed."}];

	var StudentView = Backbone.View.extend({
		el : $('#loginModal'),
		render : function(email) {
			initialTemplate["email"] = email;
			$('#loginModal').html(_studentTemplate(initialTemplate));
			$('#graph').html(_graphTemplate());

			// get id assigned to this client
			var clientData = "email=" + email;
			var ajax = $.ajax({
				type : "GET",
				url : "http://localhost:8080/client",
				data : clientData,
				dataType : "html",
				success : function(data) {
					if (data)
						clientId = data;
				}
			});

			// make get requests to the server until Market is opened
			if (marketYear == 0)
				marketInterval = setInterval(checkMarketState, 500);

			$('#marketBuyBtn').on("click", function(event) {
				event.preventDefault();
				var data = {};
				data["orderType"] = 1;
				data["amount"] = $('#size').val();
				data["price"] = -1;
				data["time"] = new Date().getTime();
				data["fulfilled"] = 0;
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
				var data = {};
				data["orderType"] = 2;
				data["amount"] = $('#size').val();
				data["price"] = -1;
				data["time"] = new Date().getTime();
				data["fulfilled"] = 0;
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

			$('#limitBuyBtn').on("click", function(event) {
				event.preventDefault();
				var data = {};
				data["orderType"] = 3;
				data["amount"] = $('#size').val();
				data["price"] = $('#price').val();
				data["time"] = new Date().getTime();
				data["fulfilled"] = 0;
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
				data["fulfilled"] = 0;
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
				if (data["year"] > 0) {
					marketYear = data["year"];
					clearInterval(marketInterval);
					timer(false, data["duration"], '#studentTimer');
					tradingOpen(data["duration"], clientId);
				}
			}
		});
	}

	return StudentView;

});
