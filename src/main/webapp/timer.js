var timerId, timeLeft, placeholder, button, admin;
var year = 1;

function timer(adminn, duration, placeholderr, buttonn) {
	admin = adminn;
	if (buttonn)
		button = buttonn;
	placeholder = placeholderr;
	timeLeft = duration * 1000 * 60;
	timerId = setInterval(countdown, 1000);
}

function countdown() {
	if (timeLeft > 0) {
		timeLeft -= 1000;
		if (!admin) $('button').prop('disabled', false);
	}
	else {
		clearInterval(timerId);

		if (admin) {
			$(button).button('complete');
			if (year == 2) {
				$(button).remove();
			}
		}
		else {
			// disable the all the buttons
			$('button').prop('disabled', true);
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

}