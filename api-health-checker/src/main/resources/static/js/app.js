//to call the web socket end point


var stompClient = null;

var apiData = [{ key: "1",              name: "BOOK SERVICE" }];
	
function setConnected(connected) {
	$("#connect").prop("disabled", connected);
	$("#disconnect").prop("disabled", !connected);
	if (connected) {
		$("#conversation").show();
	}
	else {
		$("#conversation").hide();
	}
	$("#apidata").html("");
}

function connect() {
	var socket = new SockJS('/apistats-websocket');
	stompClient = Stomp.over(socket);
	stompClient.connect({}, function () {
		setConnected(true);
		console.log('Connected: porra!!');
		stompClient.subscribe('/topic/apidatastats', function (payload) {
			showGreeting(JSON.parse(payload.body));
		});
	});
}

function disconnect() {
	if (stompClient !== null) {
		stompClient.disconnect();
	}
	setConnected(false);
	console.log("Disconnected");
}

function sendName() {
	stompClient.send("/app/apidata", {}, JSON.stringify({'name': "CALLED_APIS_QUANTITY"}));
}

function showGreeting(message) {
	$("#apidata").append("<tr><td>" + message + "</td></tr>");
	apiData[(apiData.length -1)+1] =  {parent: "1", name: message.toString()};
	model.nodeDataArray = apiData;
	
	myDiagram.model = model; 
}

$(function () {
	$("#apidata_form").on('submit', function (e) {
		e.preventDefault();
	});
	$( "#connect" ).click(function() { connect(); });
	$( "#disconnect" ).click(function() { disconnect(); });
	$( "#send" ).click(function() { sendName(); });
});