package com.cvillaseca.mobileapi.controller.user

import com.cvillaseca.mobileapi.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping("/user")
class ChangePasswordController {

    @Autowired
    lateinit var userService: UserService

    @RequestMapping(value = ["/changePassword"], method = [(RequestMethod.POST)])
    fun changePassword(
        @RequestParam("newPassword") newPassword: String,
        @RequestParam("oldPassword") oldPassword: String,
        request: HttpServletRequest
    ): ResponseEntity<Unit> =
        if (userService.updatePassword(request.userPrincipal.name, oldPassword, newPassword)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.badRequest().build()
        }
}
