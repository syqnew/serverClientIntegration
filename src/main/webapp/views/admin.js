define([ 'app', 'jquery', 'underscore', 'backbone', 'Handlebars',
		'views/marketSession', 'text!templates/admin.template', 'bootstrap' ],
		function(App, $, _, Backbone, Handlebars, MarketSessionView,
				AdminTemplate) {

			_AdminTemplate = Handlebars.compile(AdminTemplate);

			var AdminView = Backbone.View.extend({
				el : $('#loginModal'),
				render : function(username, password) {
					$('#loginModal').html(_AdminTemplate());

					$('#startButton').on("click", function(event) {
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
							dataType : "json",
							success : function(data) {
								console.log(data);
							}
						});
						var marketSessionView = new MarketSessionView();
						marketSessionView.render(event1, event2, duration);
					});
				}
			});
			return AdminView;

		});
