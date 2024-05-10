const users = [
    {id: 1, name: 'sejun'},
    {id: 2, name: 'Kim'},
    {id: 3, name: 'Park'},
    {id: 4, name: 'Jong'},
];

function predicate(key, value) {
    return item => item[key] === value;
}

let number = users.findIndex(predicate('name', 'Kim'));
console.log(number);