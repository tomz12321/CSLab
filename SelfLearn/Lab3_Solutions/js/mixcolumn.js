/**
 * mix column
 */
function convertToMatrix(binS){
	binS = binS.replace(/[^0,1]/g, "");
	var matrixS = binS.split("");
	var outputMatrix = [];
	var s = 'S';
	var s00 = s+'0,0'.sub();
	var s01 = s+ '0,1'.sub();
	var s10 = s+ '1,0'.sub();
	var s11 = s+ '1,1'.sub();

	if(matrixS.length < 4){
		// 0-2
		outputMatrix = [s00, s01, s10, s11];	
	}else if(matrixS.length < 8){
		// 0-3
		outputMatrix = [matrixS.slice(0,4).join(""), s01, s10, s11 ];
	}else if(matrixS.length < 11){
		// 0-3, 4-7
		outputMatrix = [matrixS.slice(0,4).join(""), matrixS.slice(4,8).join(""), s10, s11];
	}else if(matrixS.length < 15){
		// 0-3, 4-7, 8-11
		outputMatrix = [matrixS.slice(0,4).join(""), matrixS.slice(4,8).join(""), matrixS.slice(8,12).join(""), s11 ];
	}else{
		outputMatrix = [matrixS.slice(0,4).join(""), matrixS.slice(4,8).join(""), matrixS.slice(8,12).join(""), matrixS.slice(12,16).join("")];
	}
	

	return outputMatrix;
}

function matrixMultiplication(binS){
	binS = binS.replace(/[^0,1]/g, "");
	
	var s00 = convertToDecimal(binS.substring(0,4));
	var s01 = convertToDecimal(binS.substring(4,8));
	var s10 = convertToDecimal(binS.substring(8,12));
	var s11 = convertToDecimal(binS.substring(12,16));

	var lookupArr = [0,4,8,12,3,7,11,15,6,2,14,10,5,1,13,9];

	var s001 = s00 ^ lookupArr[s10];
	var s011 = s01 ^ lookupArr[s11];
	var s101 = lookupArr[s00] ^ s10;
	var s111 = lookupArr[s01] ^ s11;
	s001 = pad(s001.toString(2));
	s011 = pad(s011.toString(2));
	s101 = pad(s101.toString(2));
	s111 = pad(s111.toString(2));

	var outputMatrix = [s001, s011, s101, s111];
	return outputMatrix;
}