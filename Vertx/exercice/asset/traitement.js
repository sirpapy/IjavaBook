function envoieCode(){
	xhr.open("POST", "/eventbus/", true);
	alert(document.getElementById('code').value);
	xhr.send(document.getElementById('code').value);

}


function getXMLHttpRequest() {
	var xhr = null;
	
	if (window.XMLHttpRequest || window.ActiveXObject) {
		if (window.ActiveXObject) {
			try {
				xhr = new ActiveXObject("Msxml2.XMLHTTP");
			} catch(e) {
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

 // Voyez la fonction getXMLHttpRequest() définie dans la partie précédente

var xhr = getXMLHttpRequest();
xhr.onreadystatechange = function() {
    if (xhr.readyState == 4 && (xhr.status == 200 || xhr.status == 0)) {
            alert(xhr.responseText); // Données textuelles récupérées
    }
};
