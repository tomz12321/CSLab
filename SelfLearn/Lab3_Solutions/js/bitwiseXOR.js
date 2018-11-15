/**
 * bitwise XOR
 */

function bitwiseXOR(bin1, bin2) {
	// convert the input binary to decimal
	// convert the input binary to decimal
	var a, b;
	a = convertToDecimal(bin1);
	b = convertToDecimal(bin2);
	var xor = a ^ b;
	var output;
	output = xor.toString(2);

	// pad to 4 bits
	output = pad(output);
	return output;

}
function bitwiseXORUnpad(bin1, bin2) {
	// convert the input binary to decimal	
	var a, b;
	a = convertToDecimal(bin1);
	b = convertToDecimal(bin2);
	var xor = a ^ b;
	var output;
	output = xor.toString(2);

	// pad to 4 bits
	//output = pad(output);
	return output;

}
function bitwiseXORArray(arr1, arr2){
	var len1 = arr1.length;
	var len2 = arr2.length;
	var finalLen;
	var finalArr = [];


	var bin1 = arr1.slice().reverse().join("");
	var bin2 = arr2.slice().reverse().join("");
	//alert("bin1:"+bin1+" bin2:"+bin2);

	var xorStr = bitwiseXORUnpad(bin1, bin2);
	//alert("xorStr unpad:"+xorStr);
	
	if(len1 > len2){
		finalLen = len1;
	}else{
		finalLen = len2;
	} 

	finalArr = xorStr.split("");

	//pad to the longest array
	finalArr = padArrayLeading0(finalLen, finalArr);
	//alert("finalArr not reversed:"+finalArr);
	finalArr = finalArr.reverse();

	return finalArr;


}
function bitwiseXORUnpadDec(a, b) {
	// convert the input binary to decimal	
	var xor = a ^ b;
	var output;
	output = xor.toString(2);

	// pad to 4 bits
	//output = pad(output);
	return output;

}
function pad(input) {
	if (input.length % 4 != 0) {
		// var quotient = Math.floor(input.length/8);
		var padNum = 4 - input.length % 4;
		// alert(""+quotient+" "+input.length+" "+padNum);

		for (var i = 0; i < padNum; i++) {
			input = "0" + input;
		}
	}
	return input;
}

function convertToDecimal(binary) {

	if (binary == "" || isValidBin(binary) == false) {
		//alert('Invalid binary number.');
	} else {
		// convert to decimal
		outputDecimal = parseInt(binary, 2).toString(10);

		// return the result
		return outputDecimal;
	}

}

function isValidBin(binary) {
	var re = /^[01]+$/;

	if (re.test(binary)) {
		// alert('Valid Binary Number.');
		return true;
	} else {
		// alert('Invalid Binary Number.');
		return false;
	}
}