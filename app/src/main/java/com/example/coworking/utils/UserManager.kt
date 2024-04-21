package com.example.coworking.utils


data class User(
    val userId: Int? = null,
    val userName: String? = null
)

object UserManager {

    var currentUser = User()
        private set

    fun setCurrentCustomerId(id: Int, userName: String) {
        this.currentUser = this.currentUser.copy(
            userId = id,
            userName = userName
        )
    }
}