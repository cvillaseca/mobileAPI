package com.cvillaseca.mobileapi.model

import com.fasterxml.jackson.annotation.JsonBackReference
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.ArrayList
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "user")
class User : UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, updatable = false)
    val id: Long = 0

    @Column(name = "username", nullable = false, unique = true)
    private var username: String = ""

    @Column(name = "password", nullable = false)
    private var password: String = ""

    @Column(name = "enabled", nullable = false)
    private var enabled: Boolean = true

    @ManyToMany(fetch = FetchType.EAGER, cascade = [(CascadeType.MERGE)])
    @JsonBackReference
    @JoinTable(name = "role_user",
        joinColumns = [(JoinColumn(name = "user_id", referencedColumnName = "user_id"))],
        inverseJoinColumns = [(JoinColumn(name = "role_id", referencedColumnName = "role_id"))])
    private var roles: Collection<Role> = emptyList()

    @Transient
    private val rolePrefix = "ROLE_"

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val list = ArrayList<GrantedAuthority>()

        for (role in roles) {
            list.add(SimpleGrantedAuthority(rolePrefix + role.name))
        }

        return list
    }

    override fun isEnabled(): Boolean {
        return enabled
    }

    override fun getUsername(): String {
        return username
    }

    override fun getPassword(): String {
        return password
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    fun setRoles(roles: Collection<Role>) {
        this.roles = roles
    }

    fun setUsername(username: String) {
        this.username = username
    }

    fun setPassword(password: String) {
        this.password = password
    }
}
