package com.cvillaseca.mobileapi.controller.user

import com.cvillaseca.mobileapi.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SignUpController {

    @Autowired
    lateinit var userService: UserService

    @RequestMapping(value = ["/signUp"], method = [(RequestMethod.POST)])
    fun addUser(
        @RequestParam email: String,
        @RequestParam password: String
    ): ResponseEntity<Long> =
        userService.createUser(email, password)?.let {
            ResponseEntity(it.id, HttpStatus.CREATED)
        } ?: ResponseEntity.status(HttpStatus.CONFLICT).build()
}
