var timerId, timeLeft, placeholder, button, admin;
var year = 1;

function timer(adminn, duration, placeholderr, buttonn) {
	admin = adminn;
	if (buttonn)
		button = buttonn;
	placeholder = placeholderr;
	timeLeft = duration * 1000 * 60;
	if (admin)
		timerId = setInterval(adminCountdown, 1000);
	else
		timerId = setInterval(traderCountdown, 1000);
}

function adminCountdown() {
	if (timeLeft > 0) {
		timeLeft -= 1000;
	} else {
		clearInterval(timerId);
		$(button).button('complete');
		if (year == 2) {
			$(button).remove();
		}
		year = 2;
	}
	var minutes = Math.floor(timeLeft / (60 * 1000));
	var seconds = Math.floor((timeLeft - (minutes * 60 * 1000)) / 1000);

	$(placeholder).html(_TimerTemplate({
		year : year,
		minutes : minutes,
		seconds : seconds
	}));

	var ajax = $.ajax({
		type : "GET",
		url : "http://localhost:8080/marketMaker",
		dataType : "json",
		success : function(data) {
			console.log(data);
		}
	});

}

function traderCountdown() {
	if (timeLeft > 0) {
		timeLeft -= 1000;
		$('button').prop('disabled', false);
	} else {
		clearInterval(timerId);
		// disable the all the buttons
		$('button').prop('disabled', true);
		year = 2;
	}

	var minutes = Math.floor(timeLeft / (60 * 1000));
	var seconds = Math.floor((timeLeft - (minutes * 60 * 1000)) / 1000);

	$(placeholder).html(_TimerTemplate({
		year : year,
		minutes : minutes,
		seconds : seconds
	}));

}