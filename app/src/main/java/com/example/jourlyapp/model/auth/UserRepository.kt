package com.example.jourlyapp.model.auth;

import android.util.Log

interface UserRepository {
    fun getUser(): User?
    fun createUser(name: String, passcode: String): User?
    fun removeUser()
}

object UserRepositoryImpl : UserRepository {
    private val tag = this.javaClass.name
    private const val userNameKey = "user.name"
    private const val userPasscodeKey = "user.passcode"

    private lateinit var storageService: EncryptedStorageService

    operator fun invoke(
        encryptedStorageService: EncryptedStorageService
    ): UserRepositoryImpl {
        storageService = encryptedStorageService
        return this
    }

    override fun getUser(): User? {
        val userName = storageService.getString(userNameKey)
        val userPasscode = storageService.getString(userPasscodeKey)

        return if (userName.isNullOrEmpty()) null else User(
            userName,
            userPasscode ?: ""
        )
    }

    override fun createUser(name: String, passcode: String): User? {
        if (name.isEmpty()) {
            Log.d(tag, "User with empty name cannot be stored.")
            return null
        }
        if (storageService.hasKey(userNameKey)) {
            Log.d(
                tag, "User already stored. " +
                        "Removing stored user, overwriting with new one."
            )
            removeUser()
        }
        // store string values in local encrypted storage
        storageService.storeString(userNameKey, name)
        storageService.storeString(userPasscodeKey, passcode)

        Log.d(tag, "Stored user with name '$name' and passcode '$passcode'")

        return User(name, passcode)
    }

    override fun removeUser() {
        storageService.removeString(key = userNameKey)
        storageService.removeString(key = userPasscodeKey)
    }
}
