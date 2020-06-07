package com.cvillaseca.mobileapi.dao

import com.cvillaseca.mobileapi.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserDao : CrudRepository<User, Long> {
    fun findOneByUsername(email: String): User?
}
