function theta(a){
// θ [Keccak §2.3.2]
            var c = [], d = []; // intermediate sub-states
            
            for (var x=0; x<5; x++) {
            	//y=0
                c[x] = a[x][0].slice();
                for (var y=1; y<5; y++) {
                    c[x] = c[x] ^ a[x][y];
                }


            }

            for(x=0; x<5; x++){
            	d[x]=c[(x+4)%5] ^ c[(x+1)%5];
            	for(y=0; y<5; y++){
            		a[x][y] = a[x][y] ^ d[x];
            	}
            	
            }
            
            //alert("axy:"+a);
            return {array1:a, array2:c, array3:d};
}

function pi(a){
    var b =[[],[],[],[],[]];
    for(x=0; x<5; x++){
        for(y=0; y<5; y++){
            i=y;
            j=(2*x+3*y)%5;
            b[i][j]=a[x][y];
        }
    }
//alert("1 b:"+b);
    return b;

}