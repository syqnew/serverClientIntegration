function Transaction(clientId, duration) {
	this.clientId = clientId;
	this.duration = duration;
	this.timeLeft = duration * 60 * 1000;
	this.timerId;
	this.currentTransactions = [];
}

Transaction.prototype.constructor = Transaction;

Transaction.prototype.getTransactions = function() {
	this.timerId = setInterval(this.renderTransactions, 1000);
}

/*
 * Populate the transaction section of the ui
 */
Transaction.prototype.renderTransactions = function() {
	if (this.timeLeft > 0) this.timeLeft -= 1000;
	else clearInterval(this.timerId);
	
	var clientRequest = "clientId=" + this.clientId;
	var client = this.clientId;
	var ajax = $.ajax({
		type : "GET",
		url : "http://localhost:8080/sale",
		data : clientRequest,
		dataType : "json",
		success : function(data) {
			if (data.length > 0) {
				var list = [];
				for (var ct=0; ct< data.length; ct++) {
					if (data[ct]["buyerId"]==client) {
						list.push("You bought " + data[ct]["amount"] + " shares of CRL for $" + data[ct]["price"] + " per share");
					}
					else {
						list.push("You sold " + data[ct]["amount"] + " shares of CRL for $" + data[ct]["price"] + " per share");
					} 
				}
				$('#transactionBlock').html(_transactionsTemplate(list));
				
			}
		}
	});
}