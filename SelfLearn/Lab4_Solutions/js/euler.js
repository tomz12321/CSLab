/**
 * Euler's algorithm (extended Euclidean algorithm)
 */
 


 function gcd(a,b)
 { 
 if (b == 0)
   {return a}
 else
   {return gcd(b, a % b)}
 }
