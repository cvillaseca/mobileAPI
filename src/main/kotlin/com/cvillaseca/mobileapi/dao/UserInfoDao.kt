package com.cvillaseca.mobileapi.dao

import com.cvillaseca.mobileapi.model.UserInfo
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserInfoDao : CrudRepository<UserInfo, Long> {
    fun findOneByUserId(userId: Long): UserInfo?
}
