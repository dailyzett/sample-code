"use strict"

const student = {
    name: "John Doe",
    score: {
        math: 90,
        science: 95,
        english: 85
    }
};

const student2 = {
    name: "John Doe",
    score: {
        science: 95,
        english: 85
    }
};

console.log(student?.score?.math);
console.log(student2?.score?.math ?? 0);