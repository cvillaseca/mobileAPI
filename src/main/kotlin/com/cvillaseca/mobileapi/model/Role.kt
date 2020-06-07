package com.cvillaseca.mobileapi.model

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "role")
class Role(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", nullable = false, updatable = false)
    val id: Long = 0,

    @Column(name = "role_name", nullable = false, unique = true)
    var name: String = "",

    @ManyToMany
    @JoinTable(name = "role_user",
        joinColumns = [JoinColumn(name = "role_id", referencedColumnName = "role_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id", referencedColumnName = "user_id")])
    val users: Set<User> = HashSet()
) : Serializable
