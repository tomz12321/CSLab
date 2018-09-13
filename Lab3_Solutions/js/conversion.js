/**
 * bitwise XOR
 */


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
		alert('Invalid binary number.');
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

function convertToBinary(inputDecimal) {	

    
    
    if(inputDecimal == "" || isNaN(inputDecimal) ){    
     document.getElementById("inputDecimal").placeholder = "Invalid input";
    }
    else{
    	//convert to binary
        outputBinary = pad(parseInt(inputDecimal,10).toString(2));
  
        return outputBinary;  
    }
    
       
}

function convertToBinaryUnpad(inputDecimal) {	
    
    if(inputDecimal == "" || isNaN(inputDecimal) ){    
     document.getElementById("inputDecimal").placeholder = "Invalid input";
    }
    else{
    	//convert to binary
        outputBinary = parseInt(inputDecimal,10).toString(2);
  
        //display the result
   		return outputBinary;  
    }
    
       
}

function padArrayLeading0(length, arr){
	var newArr = [];
	var smallLen = arr.length;
	for(var i=0; i<length; i++){
		if(i<smallLen){
			newArr[length-i-1]=arr[smallLen-i-1];
		}else{
			newArr[length-i-1]=0;
		}
	}
	return newArr;
}