(function () {
    'use strict'

    function Foo() {
        if (!new.target) {
            return new Foo
        }
        console.log(this);
    }

    Foo();
}());

