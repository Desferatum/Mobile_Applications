package com.example.loginandregistraionscreen

import com.example.loginandregistraionscreen.CredentialsManager
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Test

class CredentialsManagerTest {

    // Test empty email
    @Test
    fun givenEmptyEmail_thenReturnFalse() {
        val credentialsManager = CredentialsManager()

        val isEmailValid = credentialsManager.isEmailValid("")

        assertEquals(false, isEmailValid)
    }

    // Test wrong email format
    @Test
    fun givenWrongEmailFormat_thenReturnFalse() {
        val credentialsManager = CredentialsManager()

        val isEmailValid = credentialsManager.isEmailValid("test-email")

        assertEquals(false, isEmailValid)
    }

    // Test proper email
    @Test
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


    // User registers with proper credentials and unused email - success
    @Test
    fun givenProperUnusedCredentials_whenUserRegisters_thenSuccess() {
        val credentialsManager = CredentialsManager()
        val newEmail = "another@te.st"
        val newPassword = "1234qwer"

        credentialsManager.register(newEmail, newPassword)

        val isLoginSuccess = credentialsManager.login(newEmail, newPassword)
        assertTrue(isLoginSuccess)
    }

    // User provides used email while registering -> failure
    @Test
    fun givenUsedEmail_WhileRegistering_thenFailure() {
        val credentialsManager = CredentialsManager()
        val usedEmail = "test@examp.le"
        val password = "asdfghjk"

        credentialsManager.register(usedEmail, password)
        val exist = credentialsManager.isUserRegistered("test@examp.le")

        assertEquals(true, exist)
    }

    // User provides used email while registering in different casing -> failure
    @Test
    fun givenUsedEmailInDifferentCasing_WhileRegistering_thenFailure() {
        val credentialsManager = CredentialsManager()
        val email = "used@ema.il"
        val password = "password123"

        credentialsManager.register(email, password)

        val result = credentialsManager.register("USED@EMA.IL", "newpassword")
        assertEquals("This email is already taken", result)
    }

    // User provides used email while login in different casing -> success
    @Test
    fun givenUsedEmailInDifferentCasing_WhileLogin_thenSuccess() {
        val credentialsManager = CredentialsManager()
        val email = "used@ema.il"
        val password = "password123"

        credentialsManager.register(email, password)

        val loginResult = credentialsManager.login("USED@EMA.IL", password)
        assertTrue(loginResult)
    }
}