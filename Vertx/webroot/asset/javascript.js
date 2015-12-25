
//document.getElementById("Exercice").innerHTML ="salutklsjmqlkdfjqmskdfjmkqsjdf";


    $(function () {
        load();
    });

    
    function update(id, name, origin) {
        $.ajax({
            method: "PUT",
            url: "/api/whiskies/" + id,
            data: JSON.stringify({name: name, origin: origin})
        }).done(function () {
            load();
        });
    }
    function load() {
    	var adress = window.location+"";
    	adress = adress.split("/");
    	alert(adress[adress.length -1]);
        $.getJSON("exercice", function (data) {
            $.each(data, function (key, val) {
            	document.getElementById('Exercice').innerHTML = val.content;
            });
            initCallbacks();
        });
    }

       

var socket = new WebSocket("ws://localhost:8080/eventbus2");

    socket.onmessage = function(event) {
       // alert("Received data from websocket: " + event.data);
        document.getElementById('Exercice').innerHTML="->"+event.data+"<-"+'\n';
    }

    

var socket = new WebSocket("ws://localhost:8080/exercice/*");

    socket.onmessage = function(event) {
       // alert("Received data from websocket: " + event.data);
        document.getElementById('Exercice').innerHTML="->"+event.data+"<-"+'\n';
    }

    











function envoieCode() {
	xhr.open("POST", "/eventbus", true);
	alert(document.getElementById('code').value);
	xhr.send(document.getElementById('code').value);
}



$(document).bind(
		'keypress',
		function(event) {
			if (event.which === 13 && event.shiftKey) {
				document.getElementById('resultat').innerHTML = document
						.getElementById('code').value
				// document.getElementById('codeArea').appendChild('</br>
				// <textarea>qsldjhflqksjhd</textarea>');
				document.getElementById('codeArea').innerHTML = document
						.getElementById('codeArea').innerHTML
						+ '</br> </br> <textarea code="code"'+1+'"></textarea>'
				envoieCode();
			}
		});

function getXMLHttpRequest() {
	var xhr = null;

	if (window.XMLHttpRequest || window.ActiveXObject) {
		if (window.ActiveXObject) {
			try {
				xhr = new ActiveXObject("Msxml2.XMLHTTP");
			} catch (e) {
				xhr = new ActiveXObject("Microsoft.XMLHTTP");
			}
		} else {
			xhr = new XMLHttpRequest();
		}
	} else {
		alert("Votre navigateur ne supporte pas l'objet XMLHTTPRequest...");
		return null;
	}

	return xhr;
}

var xhr = getXMLHttpRequest();
xhr.onreadystatechange = function() {
	if (xhr.readyState == 4 && (xhr.status == 200 || xhr.status == 0)) {
		document.getElementById('resultat').innerHTML = xhr.responseText;
	}
};
