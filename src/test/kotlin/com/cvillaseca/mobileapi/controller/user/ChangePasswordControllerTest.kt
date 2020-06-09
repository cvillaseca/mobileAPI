package com.cvillaseca.mobileapi.controller.user

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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.security.Principal


@ExtendWith(SpringExtension::class)
@WebMvcTest(ChangePasswordController::class)
@AutoConfigureMockMvc(addFilters = false)
internal class ChangePasswordControllerTest {

    @MockkBean
    lateinit var mockUserService: UserService

    @Autowired
    private lateinit var mockMvc: MockMvc

    private val username = "user@example.com"

    private val mockPrincipal = mockk<Principal> {
        every { name } returns username
    }

    @Test
    fun `when changing the password the old password matches, there is an ok response`() {
        val newPassword = "12345"
        val oldPassword = "48574"

        every { mockUserService.updatePassword(username, oldPassword, newPassword) } returns true

        performChangePassword(newPassword, oldPassword)
            .andExpect(status().isNoContent)
            .andReturn()

        verify { mockUserService.updatePassword(username, oldPassword, newPassword) }
    }

    @Test
    fun `when changing the password the old password does not match, there is an error`() {
        val newPassword = "123456"
        val oldPassword = "574874"

        every { mockUserService.updatePassword(username, oldPassword, newPassword) } returns false

        performChangePassword(newPassword, oldPassword)
            .andExpect(status().isBadRequest)
            .andReturn()

        every { mockUserService.updatePassword(username, oldPassword, newPassword) }
    }

    private fun performChangePassword(newPassword: String, oldPassword: String) =
        mockMvc.perform(post("/user/changePassword")
            .param("newPassword", newPassword)
            .param("oldPassword", oldPassword)
            .principal(mockPrincipal)
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
}
