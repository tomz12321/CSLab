/**
 * S-box
 */
 function subNib(nibble){
 	/*var js_script = document.createElement('script');
	js_script.type = "text/javascript";
	js_script.src = "js/conversion.js";
	js_script.async = true;
	document.getElementsByTagName('head')[0].appendChild(js_script);*/

 	var subList = ['1001', '0100', '1010', '1011', '1101', '0001', '1000', '0101', '0110', '0010', '0000', '0011', '1100', '1110', '1111', '0111'];
 	var nibDec;
 	var outputNibble;
 	nibDec = convertToDecimal(nibble);
 	//alert("nibDec:"+nibDec);
 	outputNibble = subList[nibDec];

 	return outputNibble;
 }