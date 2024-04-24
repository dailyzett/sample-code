// console.log(add(2,3))
let add = (x, y) => x + y;

console.log(add(2, 3));

let person = {
    name: 'test name'
}

let newPerson = function (person, newName) {
    person.name = newName;
    return person;
};

console.log(person.name)
console.log(newPerson("John"));


console.log((function (a, b) {
    return a * b;
}(10, 20)))


let repeat = function (n, f) {
    for (let i = 0; i < n; i++) f(i)
}

let logAll = function (i) {
    console.log(i);
}

// repeat(5, logAll);

let logOdd = function (i) {
    if (i % 2) console.log(i);
}

repeat(10, logOdd)

let res = [1, 2, 3, 4, 6].filter(function (item) {
    return item % 2;
});

let resMap = [1, 2, 3].map(function (item) {
    return item * 2;
});

let resReduce = [1, 2, 3].reduce(function (acc, cur) {
    return acc + cur
}, 0);

console.log(res);
console.log(resMap);
console.log(resReduce);

let x = 1;
let foo = function () {
    let x = 10;
    bar();
};

let bar = function () {
    console.log(x);
};

foo();
bar();


