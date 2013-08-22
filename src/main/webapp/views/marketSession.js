define([ 'app', 'jquery', 'underscore', 'backbone', 'Handlebars',
		'text!templates/marketSession.template',
		'text!templates/timer.template', '../timer', 'bootstrap' ], function(
		App, $, _, Backbone, Handlebars, MarketSessionTemplate, TimerTemplate) {

	_MarketSessionTemplate = Handlebars.compile(MarketSessionTemplate);
	_TimerTemplate = Handlebars.compile(TimerTemplate);

	var MarketSessionView = Backbone.View.extend({
		el : $('#loginModal'),
		render : function(event1, event2, duration) {
			$('#loginModal').html(_MarketSessionTemplate({
				event1 : event1,
				event2 : event2,
				duration : duration
			}));

			$('#timer').html(_TimerTemplate({
				year : year,
				minutes : duration,
				seconds : "00"
			}));
			$('#startPeriodButton').on("click", function(event) {
				event.preventDefault();
				$('#startPeriodButton').button('loading');
				var data = {};
				data["year"] = year;
				data["year1Event"] = event1;
				data["year2Event"] = event2;
				var ajax = $.ajax({
					type : "POST",
					url : "http://localhost:8080/admin",
					data : JSON.stringify(data),
					dataType : "json",
					success : function(data) {
						console.log(data);
					}
				});
				timer(duration, '#timer', '#startPeriodButton');
			});
		}
	});

	return MarketSessionView;

});
