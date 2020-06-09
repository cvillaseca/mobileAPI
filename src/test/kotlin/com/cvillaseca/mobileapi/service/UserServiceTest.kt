package com.cvillaseca.mobileapi.service

import com.cvillaseca.mobileapi.dao.RoleDao
import com.cvillaseca.mobileapi.dao.UserDao
import com.cvillaseca.mobileapi.dao.UserInfoDao
import com.cvillaseca.mobileapi.model.Role
import com.cvillaseca.mobileapi.model.User
import com.cvillaseca.mobileapi.model.UserInfo
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@WebMvcTest(UserService::class)
@AutoConfigureMockMvc(addFilters = false)
internal class UserServiceTest {
    @MockkBean
    lateinit var mockUserDao: UserDao

    @MockkBean
    lateinit var mockRoleDao: RoleDao

    @MockkBean
    lateinit var mockUserInfoDao: UserInfoDao

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    private lateinit var service: UserService

    private val username = "user@example.com"

    @Test
    fun `when the user is loaded by username, the user is returned`() {
        val expectedUser = mockk<User>(relaxed = true)
        every { mockUserDao.findOneByUsername(username) } returns expectedUser

        val userDetails = service.loadUserByUsername(username)

        verify { mockUserDao.findOneByUsername(username) }
        assertEquals(expectedUser.username, userDetails.username)
    }

    @Test
    fun `when the user is not found, there is a UsernameNotFoundException`() {
        every { mockUserDao.findOneByUsername(username) } returns null

        assertThrows<UsernameNotFoundException> {
            service.loadUserByUsername(username)
        }

        verify { mockUserDao.findOneByUsername(username) }
    }

    @Test
    fun `when a new user is created successfully, the user info is returned`() {
        val userId = 3L
        val expectedUser = mockk<User>(relaxed = true) {
            every { id } returns userId
        }
        val userRole = mockk<Role>(relaxed = true)
        val expectedNewUserInfo = UserInfo(userId = userId, email = username)
        every { mockUserDao.findOneByUsername(username) } returns null
        every { mockRoleDao.findByName("USER") } returns userRole
        every { mockUserDao.save(any<User>()) } returns expectedUser
        every { mockUserInfoDao.save(expectedNewUserInfo) } returns expectedNewUserInfo

        val newUser = service.createUser(username, "password")

        verify { mockUserDao.findOneByUsername(username) }
        verify { mockRoleDao.findByName("USER") }
        verify { mockUserDao.save(any<User>()) }
        verify { mockUserInfoDao.save(expectedNewUserInfo) }
        assertEquals(expectedUser, newUser)
    }

    @Test
    fun `when the new user already exists, a null is returned`() {
        val sameEmailUser = mockk<User>(relaxed = true)
        every { mockUserDao.findOneByUsername(username) } returns sameEmailUser

        val newUser = service.createUser(username, "password")

        verify { mockUserDao.findOneByUsername(username) }
        assertNull(newUser)
    }

    @Test
    fun `when updating the password successfully, a true value is returned`() {
        val oldPassword = "1234"
        val newPassword = "newPassword"
        val expectedUser = mockk<User>(relaxed = true) {
            every { password } returns passwordEncoder.encode(oldPassword)
        }
        every { mockUserDao.findOneByUsername(username) } returns expectedUser
        every { mockUserDao.save(any<User>()) } returns expectedUser

        val passwordUpdated = service.updatePassword(username, oldPassword, newPassword)

        verify { mockUserDao.findOneByUsername(username) }
        verify { mockUserDao.save(any<User>()) }
        assertTrue(passwordUpdated)
    }

    @Test
    fun `when updating the password the current password doesn't match, a false value is returned`() {
        val oldPassword = "wrongOldPassword"
        val newPassword = "newPassword"
        val expectedUser = mockk<User>(relaxed = true) {
            every { password } returns passwordEncoder.encode("oldPassword")
        }
        every { mockUserDao.findOneByUsername(username) } returns expectedUser

        val passwordUpdated = service.updatePassword(username, oldPassword, newPassword)

        verify { mockUserDao.findOneByUsername(username) }
        assertTrue(passwordUpdated.not())
    }

    @Test
    fun `when getting the user info successfully, the info is returned`() {
        val userId = 3L
        val expectedUser = mockk<User>(relaxed = true) {
            every { id } returns userId
        }
        val expectedUserInfo = mockk<UserInfo>(relaxed = true)
        every { mockUserDao.findOneByUsername(username) } returns expectedUser
        every { mockUserInfoDao.findOneByUserId(userId) } returns expectedUserInfo

        val userInfo = service.getUserInfo(username)

        verify { mockUserDao.findOneByUsername(username) }
        verify { mockUserInfoDao.findOneByUserId(userId) }
        assertEquals(expectedUserInfo, userInfo)
    }

    @Test
    fun `when getting the user info the info is not found, a null is returned`() {
        every { mockUserDao.findOneByUsername(username) } returns null

        val userInfo = service.getUserInfo(username)

        verify { mockUserDao.findOneByUsername(username) }
        assertNull(userInfo)
    }

    @Test
    fun `when getting the user list successfully, the list is returned`() {
        val expectedUsers = listOf<UserInfo>(mockk(), mockk())
        every { mockUserInfoDao.findAll() } returns expectedUsers

        val users = service.getAllUsers()

        verify { mockUserInfoDao.findAll() }
        assertEquals(expectedUsers, users)
    }
}
