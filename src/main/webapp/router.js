define([ 'app', 'jquery', 'underscore', 'backbone', 'Handlebars',
		'views/student', 'views/admin' ], function(App, $, _, Backbone,
		Handlebars, StudentView, AdminView) {

	$('.alert').hide();
	
	$('#studentLogin').on("click", function(event) {
		event.preventDefault();
		$('.alert').hide();
		var email = $('#studentInput').val();
		if (email.indexOf("@") != -1) {
			var studentView = new StudentView();
			studentView.render(email);
		} else {
			$('#studentAlert').show();
		}
	});
	
	$('#adminLogin').on("click", function(event) {
		event.preventDefault();
		$('.alert').hide();
		var username = $('#adminUsername').val();
		var password = $('#adminPassword').val();
		
		if (username == "admin" && password == "admin"){
			var adminView = new AdminView();
			adminView.render(username, password);
		} else {
			$('#adminAlert').show();
		}
	});

});
