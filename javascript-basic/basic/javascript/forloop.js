"use strict"

let result = 0;
let i;
for(i = 1; ; i++) {
    console.log(result);
    result += i;
    if(result >= 100) break;
}

console.log(i)