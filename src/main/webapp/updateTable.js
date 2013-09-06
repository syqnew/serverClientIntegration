var timerId, clientId, duration, timeLeft;
var timeInterval = 1000;

function updateTable(client, period) {
	console.log("update table method");
	clientId = client;
	duration = period;
	timeLeft = period * 60 * 1000;
	timerId = setInterval(renderTable, timeInterval);
}

function renderTable() {
	if (timeLeft > 0) timeLeft -= timeInterval;
	else clearInterval(timerId);
	
	var tableData = {};
	
	var clientData = "clientId=" + clientId;
	console.log(clientId);
	var ajax = $.ajax({
		type : "GET",
		url : "http://localhost:8080/metadata",
		data : clientData,
		dataType : "json",
		success : function(data) {
			console.log(data);
			tableData["quantity"] = data[1]["shares"];
			tableData["cashTotal"] = data[1]["cash"];
			
			tableData["volume"] = data[0]["volume"];
			if ( data[0]["high"] == 0) tableData["high"] = "-";
			else tableData["high"] = data[0]["high"];
			if ( data[0]["low"] != 100000000) tableData["low"] = data[0]["low"];
			else tableData["low"] = "-";
			if ( data[0]["ask"] != "-1") tableData["ask"] = data[0]["ask"];
			else tableData["ask"] = "-";
			if ( data[0]["askSize"] != "0") tableData["askSize"] = data[0]["askSize"];
			else tableData["askSize"] = 0;
			if ( data[0]["bid"] != "-1") tableData["bid"] = data[0]["bid"];
			else tableData["bid"] = "-";
			if ( data[0]["bidSize"] != "0") tableData["bidSize"] = data[0]["bidSize"];
			else tableData["bidSize"] = 0;
			if ( data[0]["last"] != "0") {
				tableData["last"] = data[0]["last"];
				tableData["crlTotal"] = tableData["last"] * tableData["quantity"];
			} else {
				tableData["last"] = "-";
				tableData["crlTotal"] = "0";
			}
			tableData["total"] = tableData["crlTotal"] + tableData["cashTotal"];
			$('#quotePortfolioTable').html(_quotePortfolioTableTemplate(tableData));
		}
	});
	
}