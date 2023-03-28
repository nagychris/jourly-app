package com.example.jourlyapp.model.auth

data class User(val name: String = "", val passcode: String = "") {
    fun isValid(): Boolean {
        return this.name.isNotEmpty()
    }
}
