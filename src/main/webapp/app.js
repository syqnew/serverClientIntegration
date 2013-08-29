require
		.config({
			paths : {
				'jquery' : [ "//cdnjs.cloudflare.com/ajax/libs/jquery/2.0.3/jquery.min" ],
				'bootstrap' : [ "//cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.2/js/bootstrap.min" ],
				'Handlebars' : [ "//cdnjs.cloudflare.com/ajax/libs/handlebars.js/1.0.0/handlebars.min" ],
				'backbone' : [ "//cdnjs.cloudflare.com/ajax/libs/backbone.js/1.0.0/backbone-min" ],
				'underscore' : [ "//cdnjs.cloudflare.com/ajax/libs/underscore.js/1.5.1/underscore-min" ],
				'text' : [ "//cdnjs.cloudflare.com/ajax/libs/require-text/2.0.10/text" ],
				'flot' : [ "//cdnjs.cloudflare.com/ajax/libs/flot/0.8.1/jquery.flot.min" ],
				'flotTime' : "js/jquery.flot.time",
				'flotSymbol' : "js/jquery.flot.symbol"
			},
			shim : {
				"bootstrap" : {
					deps : [ "jquery" ]
				},
				"backbone" : {
					deps : [ 'jquery', 'underscore' ],
					exports : 'Backbone'
				},
				"Handlebars" : {
					exports : 'Handlebars'
				},
				"flot" : {
					deps : [ "jquery" ],
					exports : 'flot'
				},
				"flotTime" : {
					deps : [ "jquery", "flot" ],
					exports : 'flotTime'
				},
				"flotSymbol" : {
					deps : [ "jquery", "flot" ],
					exports : 'flotSymbol'
				}
			}
		});

require([ 'jquery', 'Handlebars', 'backbone', 'underscore', 'router' ],
		function(jquery, Handlebars, backbone, underscore, Router) {
		});
