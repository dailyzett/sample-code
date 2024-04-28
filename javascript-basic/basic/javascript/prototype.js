function Person(name) {
    this.name = name;
}

const me = new Person('Lee');

const parent = {
    constructor: Person,
    sayHello: function () {
        console.log(`Hello ${this.name}`);
    }
};

Person.prototype = parent;
Object.setPrototypeOf(me, parent);
me.sayHello();

console.log(me.constructor === Person)
