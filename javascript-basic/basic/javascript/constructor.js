const person = {};
person.name = "Jun"
person.sayHello = function () {
    console.log(`Hello, ${person.name}`);
};

person.sayHello();

function Circle(radius) {
    if (!new.target) {
        return new Circle(radius);
    }
    this.radius = radius;
    this.getDiameter = function () {
        return 2 * radius;
    };
}

const circle = Circle(10);
