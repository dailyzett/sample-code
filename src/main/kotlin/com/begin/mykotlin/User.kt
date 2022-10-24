package com.begin.mykotlin

class User(val id: Int, val name: String, val address: String)

fun User.validateBeforeSave() {
    fun validate(value: String, fieldName: String) {
        if (value.isEmpty()) {
            throw IllegalArgumentException(
                    "Can't save user ${id}: empty $fieldName"
            )
        }
    }

    validate(name, "Name")
}

fun saveUser(user: User) {
    user.validateBeforeSave()

    //DB에 저장하는 로직
}