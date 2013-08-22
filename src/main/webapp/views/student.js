define(
		[ 'app', 'jquery', 'underscore', 'backbone', 'Handlebars', 'flot', 'text!templates/student.template', 
		  'text!templates/graph.template', 'text!templates/timer.template', '../timer', '../flotGraph', 'flotTime', 'bootstrap' ],
		function(App, $, _, Backbone, Handlebars, flot, studentTemplate, graphTemplate, timerTemplate) {
			
			_studentTemplate = Handlebars.compile(studentTemplate);
			_graphTemplate = Handlebars.compile(graphTemplate);
			_timerTemplate = Handlebars.compile(timerTemplate);

			var StudentView = Backbone.View.extend({
				el : $('#loginModal'),
				render : function(email) {
					$('#loginModal').html(_studentTemplate({email: email}));
					$('#graph').html(_graphTemplate());
					$('#studentTimer').html(_timerTemplate({year: "1", minutes: "2", seconds: "00"}));
					
					//flot testing
					flotGraph('#placeholder');
//					var d1 = [];
//					for (var i = 0; i < 14; i += 0.5) {
//						d1.push([i, Math.sin(i)]);
//					}
//					var d2 = [[0, 3], [4, 8], [8, 5], [9, 13]];
//					// A null signifies separate line segments
//					var d3 = [[0, 12], [7, 12], null, [7, 2.5], [12, 2.5]];
//					$.plot("#placeholder", [d1,  d2, d3]);
					
					//timer testing
					timer(2, '#studentTimer', null, 1);
				}
			});
			return StudentView;

		});
