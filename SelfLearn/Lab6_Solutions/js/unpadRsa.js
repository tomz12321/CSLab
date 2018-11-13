/**
 * unpadded rsa 
 */
function rsaEncryption(e, n, plaintext){
	if(!isNaN(plaintext)){
		var p = parseInt(plaintext);
		//var c = powMod(p, e, n);
		//var c =bi_mod(bi_pow(p, e) , n);
		var c = fastModExp(p, e, n);
		//var c = Math.pow(p, e) % n;
		//alert("p:"+p+" e:"+e+" n:"+n);
		return c.toString(10);  
	}
}

function rsaDecryption(d, n, ciphertext){
	if(!isNaN(ciphertext)){
		var c = parseInt(ciphertext);
		//var p = Math.pow(c, d) % n;
		//var p = powMod(c, d, n);
		//var p = bi_mod(bi_pow(c, d),n);
		var p = fastModExp(c, d, n);
		//alert("c:"+c+" d:"+d+" n:"+n+" p:"+p+" Math.pow(c, d):"+Math.pow(c, d));
		return p;  
	}
}

function convertToNumAlpha(str){

}

function fastModExp(m, e, n){
	var eBin = e.toString(2).split("");
	var eLen = eBin.length;
	//alert("e:"+eBin);
	var i = 0;
	var resTotal = 1;
	while(i<eLen){
		if(i == 0){
			result = m % n;
		}else{
			result = result * result;
			result = result % n;
		}
		if(eBin[eLen-1-i] == 1){
			resTotal = resTotal * result;
			resTotal = resTotal % n;
		}


		i++;
	}

	return resTotal;
}

function encryptMsg(e, n, msg) {
	//convert to lowercase/uppercase and remove the space
    var plaintext = msg.toLowerCase().replace(/[^a-z\s]/g, ""); 
    var ciphertext = ""; 
    //alert("plaintext:"+plaintext);
    if(plaintext.length < 1){ 
    	alert("Please enter some plaintext"); 
        return; 
        }    

       
	//convert plaintext to number and encrypt
    for(var i=0; i < plaintext.length; i++){
    	//use unicode to identify each letter. e.g. a is 97
    	var code = plaintext.charCodeAt(i);
    	var numeric;
    	var newcode;
    	if(code == 32){
    		numeric = 26;
    		newcode = rsaEncryption(e, n, numeric);

    	}else{
    		numeric = code - 97;
    		newcode = rsaEncryption(e, n, numeric);

    	}
		newcode = pad(newcode.toString(10));
		ciphertext = ciphertext + newcode;
     }
    
   
    return ciphertext;
   
}
function pad(input) {
	if (input.length % 2 != 0) {
		// var quotient = Math.floor(input.length/8);
		var padNum = 2 - input.length % 2;
		// alert(""+quotient+" "+input.length+" "+padNum);

		for (var i = 0; i < padNum; i++) {
			input = "0" + input;
		}
	}
	return input;
}

function decryptMsg(d, n, msg) {
	//convert to lowercase/uppercase and sanitize
    var ciphertext = msg.replace(/[^0-9]/g, "");  
    var plaintext ="";
    if(ciphertext.length < 1){ 
    	alert("please enter some ciphertext"); return; 
        }    
    

	for(var i=0; i < ciphertext.length; i = i+2){
    	var block = parseInt(ciphertext.substring(i, i+2));
    	//alert("block:"+block);
    	var code = rsaDecryption(d, n, block);
    	//alert("code:"+code);
    	var newcode;
    	if(code == 26){
    		newcode = 32;
    	}else{
    		newcode = code+97;
    	}
       
       // alert("newcode:"+newcode);
		var newletter = String.fromCharCode(newcode).toUpperCase();
		//alert("newletter:"+newletter);
		plaintext = plaintext + newletter;
     }
    
    return plaintext;
    
}