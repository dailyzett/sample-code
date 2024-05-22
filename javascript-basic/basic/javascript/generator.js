const infiniteFibonacci = function () {
    let [pre, cur] = [0, 1];

    return {
        [Symbol.iterator]() {
            return this;
        },

        next() {
            [pre, cur] = [cur, pre + cur];
            return {value: cur};
        }
    };
}();

for (const num of infiniteFibonacci) {
    if (num > 10000) break;
    console.log(num);
}
