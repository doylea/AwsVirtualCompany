//This is a JS file for Ajax.
function getXmlHttpRequest() {
	var xmlhttp = null;

	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest(); 
	} else if (window.ActiveXObject) {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	return xmlhttp;
}