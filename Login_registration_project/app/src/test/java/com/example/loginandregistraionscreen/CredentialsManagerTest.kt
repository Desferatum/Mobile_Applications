package com.example.credentials

import org.junit.jupiter.api.Test
import org.junit.Assert.*

class CredentialsManagerTest {

    // Test empty email
    @Test
    fun givenEmptyEmail_thenReturnFalse() {
        val credentialsManager = CredentialsManager()

        val isEmailValid = credentialsManager.isEmailValid("")

        assertEquals(false, isEmailValid)
    }

    // Test wrong email format
    @org.junit.jupiter.api.Test
    fun givenWrongEmailFormat_thenReturnFalse() {
        val credentialsManager = CredentialsManager()

        val isEmailValid = credentialsManager.isEmailValid("test-email")

        assertEquals(false, isEmailValid)
    }

    // Test proper email
    @org.junit.jupiter.api.Test
    fun givenProperEmail_thenReturnTrue() {
        val credentialsManager = CredentialsManager()

        val isEmailValid = credentialsManager.isEmailValid("test@example.com")

        assertEquals(true, isEmailValid)
    }

    // Test empty password
    @Test
    fun givenEmptyPassword_thenReturnFalse() {
        val credentialsManager = CredentialsManager()

        val isPasswordValid = credentialsManager.isPasswordValid("")

        assertEquals(false, isPasswordValid)
    }

    // Test filled password
    @Test
    fun givenFilledPassword_thenReturnTrue() {
        val credentialsManager = CredentialsManager()

        val isPasswordValid = credentialsManager.isPasswordValid("password")

        assertEquals(true, isPasswordValid)
    }


    //User registers with proper credentials and unused email - success
    @Test
    fun givenProperUnusedCredentials_whenUserRegisters_thenSuccess() {
        val credentialsManager = CredentialsManager()
        val newEmail = "another@te.st"
        val newPassword = "1234qwer"

        credentialsManager.register("Full name", newEmail, "600 600 600", newPassword)

        val isLoginSuccess = credentialsManager.login(newEmail, newPassword)
        assertTrue(isLoginSuccess)
    }


}