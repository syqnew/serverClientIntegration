var timerId, timeLeft, placeholder, button;
var year = 1;

function timer(duration, placeholderr, buttonn, yearr) {
	if (buttonn) button = buttonn;
	if (yearr) year = yearr;
	placeholder = placeholderr;
	timeLeft = duration * 1000 * 60;
	timerId = setInterval(countdown, 1000);
}

function countdown() {
	if (timeLeft > 0) timeLeft -= 1000;
	else {
		clearInterval(timerId);
		//logic only applies to the admin market session page
		if (button) {
			$(button).button('complete');
			if (year == 2) {
				$(button).remove();
			}
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