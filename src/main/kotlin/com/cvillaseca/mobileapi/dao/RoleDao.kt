package com.cvillaseca.mobileapi.dao

import com.cvillaseca.mobileapi.model.Role
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RoleDao : CrudRepository<Role, Long> {
    fun findByName(name: String): Role
}
