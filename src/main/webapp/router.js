define([ 'app', 'jquery', 'underscore', 'backbone', 'Handlebars',
		'views/student', 'views/admin' ], function(App, $, _, Backbone,
		Handlebars, StudentView, AdminView) {

	$('.alert').hide();

	$('#studentLogin').on("click", function(event) {
		event.preventDefault();
		$('.alert').hide();
		var email = $('#studentInput').val();

		var data = {};
		data["email"] = email;
		var ajax = $.ajax({
			type : "POST",
			url : "http://localhost:8080/client",
			data : JSON.stringify(data),
			dataType : "json"
		});
		var studentView = new StudentView();
		studentView.render(email);

		
	});

	$('#adminLogin').on("click", function(event) {
		event.preventDefault();
		$('.alert').hide();
		var username = $('#adminUsername').val();
		var password = $('#adminPassword').val();

		if (username == "admin" && password == "admin") {
			var adminView = new AdminView();
			adminView.render(username, password);
		} else {
			$('#adminAlert').show();
		}
	});

});
