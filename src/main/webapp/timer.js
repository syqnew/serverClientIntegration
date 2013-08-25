var timerId, timerStage2Id, timeLeft, timeStage2Left, placeholder, button, temp, durationOriginal;
var year = 1;

function TraderTimer() {
}
TraderTimer.prototype.countdown = function(duration, placeholder) {
	$('button').prop('disabled', false);
	var timeLeft = duration * 60 * 1000;
	var timerId = setInterval(function() {
		if (timeLeft > 0) {
			timeLeft -= 1000;
		} else {
			clearInterval(timerId);
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
	}, 1000);
}

function AdminTimer() {
}
AdminTimer.prototype.countdown = function(duration, placeholder, button) {
	var timeLeft = duration * 1000 * 60;
	timerId = setInterval(function() {
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
	}, 1000);
}
