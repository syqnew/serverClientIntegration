function News(duration, clientId) {
	console.log(clientId);
	this.duration = duration;
	this.clientId = clientId;
	this.temp = "";
}

News.prototype.constructor = News;

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

News.prototype.getStage2News = function() {
	var outer = {};
	var inner1 = {"new": "Market is open"};
	var inner2 = {};
	inner2["new"] = this.temp;
	var list = [];
	list.push(inner2);
	list.push(inner1);
	outer["news"] = list;

	$('#marketNews').html(_newsTemplate(outer));
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