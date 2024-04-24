var x = 'Global';

function tx() {
    console.log(x);
    var x = 'Local'
}

tx()
console.log(x)

let counter = (function () {
    let x = 0; // private

    return {
        increase() {
            return x++;
        },
        decrease() {
            return x--;
        },
    }
}());

console.log(counter.increase())
console.log(counter.increase())
console.log(counter.increase())
console.log(counter.decrease())