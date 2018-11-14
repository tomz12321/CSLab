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
		return p.toString(10);  
	}
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
