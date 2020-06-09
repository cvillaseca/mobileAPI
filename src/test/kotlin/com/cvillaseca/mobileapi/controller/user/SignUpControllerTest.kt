package com.cvillaseca.mobileapi.controller.user

import com.cvillaseca.mobileapi.model.User
import com.cvillaseca.mobileapi.service.UserService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@ExtendWith(SpringExtension::class)
@WebMvcTest(SignUpController::class)
@AutoConfigureMockMvc(addFilters = false)
internal class SignUpControllerTest {

    @MockkBean
    lateinit var mockUserService: UserService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `when a new user sign up, there is an ok response`() {
        val email = "newuser@example.com"
        val password = "1234"
        val newUserId = 50L
        val expectedUser = mockk<User> {
            every { id } returns newUserId
        }

        every { mockUserService.createUser(email, password) } returns expectedUser

        performSignUp(email, password)
            .andExpect(status().isCreated)
            .andExpect(content().string(newUserId.toString()))
            .andReturn()

        verify { mockUserService.createUser(email, password) }
    }

    @Test
    fun `when a new user sign up with an existing email, there is an error response`() {
        val email = "existingemail@example.com"
        val password = "12345"

        every { mockUserService.createUser(email, password) } returns null

        performSignUp(email, password)
            .andExpect(status().isConflict)
            .andReturn()

        verify { mockUserService.createUser(email, password) }
    }

    private fun performSignUp(email: String, password: String) =
        mockMvc.perform(MockMvcRequestBuilders.post("/signUp")
            .param("email", email)
            .param("password", password)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
}
