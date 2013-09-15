function News(duration, clientId) {
	this.duration = duration;
	this.clientId = clientId;
	this.temp = "";
}

News.prototype.constructor = News;

/*
 * Make two calls to the server to get stage 1 news. The first call is at the beginning of the period to get
 * general information about year 1. The second call is at the end of the period to reveal the type of year 
 * it was. The calls populate the market news section of the ui.
 */
News.prototype.getStage1News = function() {
	var requestString = "id=" + (this.clientId % 4);
	var ajax = $.ajax({
		type : "GET",
		url : "http://localhost:8080/news",
		data : requestString,
		dataType : "json",
		success : function(data) {
			this.temp = data["message"];
			$('#marketNews').html(_newsTemplate({
				"news" : [ {
					"new" : "Market is open"
				}, {
					"new" : this.temp
				} ]
			}));
		}
	});
	var t = setTimeout(function() {
		var requestString = "id=" + ((this.clientId % 4) + 4);
		var ajax = $.ajax({
			type : "GET",
			url : "http://localhost:8080/news",
			data : requestString,
			dataType : "json",
			success : function(data) {
				this.temp = data["message"];
				$('#marketNews').html(_newsTemplate({
					"news" : [ {
						"new" : "Market is closed"
					}, {
						"new" : this.temp
					} ]
				}));
			}
		});
	}, (this.duration * 60 * 1000) + 800);
}

/*
 * This function also has two calls to the server. The first is to get the current information (i.e. X in
 * Year 1 and NOT X in Year 2). The second is to reveal the market type of year 2. These calls populate the
 * market news section in the ui.
 */
News.prototype.getStage2News = function(newsTmp) {	
	var requestString = "id=" + ((this.clientId % 4) + 4);
	var ajax = $.ajax({
		type : "GET",
		url : "http://localhost:8080/news",
		data : requestString,
		dataType : "json",
		success : function(data) {
			this.temp = data["message"];
			$('#marketNews').html(_newsTemplate({
				"news" : [ {
					"new" : this.temp
				}, {
					"new" : "Market is open"
				} ]
			}));
		}
	});

	var t = setTimeout(function() {
		var requestString = "id=" + ((this.clientId % 4) + 8);
		var ajax = $.ajax({
			type : "GET",
			url : "http://localhost:8080/news",
			data : requestString,
			dataType : "json",
			success : function(data) {
				this.temp = data["message"];
				$('#marketNews').html(_newsTemplate({
					"news" : [ {
						"new" : "Market is closed"
					}, {
						"new" : this.temp
					} ]
				}));
			}
		});
	}, (this.duration * 60 * 1000) + 800);
}