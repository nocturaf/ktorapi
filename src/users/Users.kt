package com.nocturaf.ktorapi.users

import org.jetbrains.exposed.sql.Table

object Users : Table() {
    val id = integer("id")
    val fullname = text("fullname")
    val email = text("email")
    override val primaryKey = PrimaryKey(id, name = "primary_key_users")
}