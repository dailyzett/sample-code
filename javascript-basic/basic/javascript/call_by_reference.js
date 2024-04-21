"use strict"

let first = {
    name: "John"
};

let second = {
    name: "Test",
};

first.name = second.name;

console.log(first.name);

first.name = "Good";

console.log(second.name);
console.log(first.name);

let third =  {
    name: "Third",
}

first = third;

console.log(third.name);
first.name = "change!"
console.log(third.name);