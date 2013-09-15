define([ 'app', 'jquery', 'underscore', 'backbone', 'Handlebars', 'views/marketSession',
		'text!templates/admin.template', 'bootstrap' ], function(App, $, _, Backbone, Handlebars, MarketSessionView,
		AdminTemplate) {

	_AdminTemplate = Handlebars.compile(AdminTemplate);

	var AdminView = Backbone.View
			.extend({
				el : $('#loginModal'),
				render : function(username, password) {
					$('#loginModal').html(_AdminTemplate());

					// Attach listener to send fields to database
					$('#nextButton').on("click", function(event) {
						event.preventDefault();
						var event1 = $('#event1').val();
						var event2 = $('#event2').val();
						var duration = $('#duration').val();
						var data = {};
						data["year"] = 0;
						data["year1Event"] = event1;
						data["year2Event"] = event2;
						data["duration"] = duration;
						var ajax = $.ajax({
							type : "POST",
							url : "http://localhost:8080/admin",
							data : JSON.stringify(data),
							dataType : "json"
						});
						insertMessages(event1, event2);

						var marketSessionView = new MarketSessionView();
						marketSessionView.render(event1, event2, duration);
					});

					/*
					 * Creates news based off of the events and inserts them
					 * into the database. There are four variations of the news
					 * statements.
					 * 
					 * @param Event in year1, and Event in year2
					 */
					function insertMessages(event1, event2) {
						var stage0 = [];
						var stage1 = [];
						var stage2 = [];
						var state = [];
						if (event1.substring(0, 1) == "X")
							state.push(0);
						if (event1.substring(0, 1) == "Y")
							state.push(1);
						if (event1.substring(0, 1) == "Z")
							state.push(2);
						if (event2.substring(0, 1) == "X")
							state.push(0);
						if (event2.substring(0, 1) == "Y")
							state.push(1);
						if (event2.substring(0, 1) == "Z")
							state.push(2);
						var stateStr1 = [ "X", "Y", "Z" ];
						var stateStr2 = [ "X", "Y", "Z" ];

						for ( var s1 = 0; s1 < 3; s1++) {
							for ( var s2 = 0; s2 < 3; s2++) {
								if (s1 == state[0])
									continue;
								if (s2 == state[1])
									continue;
								var temp1 = {};
								temp1["message"] = "NOT " + stateStr1[s1] + " in Year 1 and NOT " + stateStr2[s2]
										+ " in Year 2";
								stage0.push(temp1);
								var temp2 = {};
								temp2["message"] = stateStr1[state[0]] + " in Year 1 and NOT " + stateStr2[s2]
										+ " in Year 2";
								stage1.push(temp2);
								var temp3 = {};
								temp3["message"] = stateStr1[state[0]] + " in Year 1 and " + stateStr2[state[1]]
										+ " in Year 2";
								stage2.push(temp3);
							}
						}

						var ajax = $.ajax({
							type : "POST",
							url : "http://localhost:8080/news",
							data : JSON.stringify(stage0.concat(stage1, stage2)),
							dataType : "json"
						});
					}
				}
			});
	return AdminView;
});
