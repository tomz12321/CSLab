
function isPrimeNum(p){
	
	if(p<=1){
		return false;
	}else if(p <= 3){
		return true;
	}else if(p%2 == 0 || p%3 == 0 ){
		return false;
	}
	var sqrt = Math.floor(Math.sqrt(p));
	var i = 5;
	while(i <= sqrt){
		if(p%i == 0 || p% (i+2) == 0){
			return false;
		}
	 i = i+6;	
	}
	return true;
}

function primeNumList(max){
	var i = 1;
	var list = [];
	while(i<max){
		var isPrime = isPrimeNum(i);
		if(isPrime){
			list.push(i);
		}
		if(i>=3){
			i = i+2;
		}else{
			i++;
		}
		
	}

	return list;
}