const increase = function (num) {
    return ++num;
};

const decrease = function (num) {
    return --num;
};

const auxs = {increase, decrease};

function makeCounter(aux) {
    let num = 0;
    return function () {
        num = aux(num);
        return num;
    };
}

const increaser = makeCounter(auxs.increase)
console.log(increaser());
console.log(increaser());