package com.cvillaseca.mobileapi.controller.admin

import com.cvillaseca.mobileapi.model.UserInfo
import com.cvillaseca.mobileapi.service.UserService
import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
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
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@ExtendWith(SpringExtension::class)
@WebMvcTest(UserController::class)
@AutoConfigureMockMvc(addFilters = false)
internal class UserControllerTest {
    @MockkBean
    lateinit var mockUserService: UserService

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `when the admin request the users, the user list is returned`() {
        val users = listOf(
            UserInfo(id = 3L, userId = 4L, email = "user@example.com", profileImage = "url"),
            UserInfo(id = 4L, userId = 5L, email = "user2@example.com", profileImage = "url2")
        )
        val objectMapper = ObjectMapper()
        val userInfoJSON = objectMapper.writeValueAsString(users)
        every { mockUserService.getAllUsers() } returns users

        performUsers()
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(userInfoJSON))
            .andReturn()

        verify { mockUserService.getAllUsers() }
    }

    private fun performUsers() =
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/users")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
}