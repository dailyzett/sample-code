package com.begin.four.two

data class Person(val name: String) {
    object nameComparator : Comparator<Person> {
        override fun compare(o1: Person?, o2: Person?): Int =
                o1?.name?.compareTo(o2!!.name)!!
    }
}
