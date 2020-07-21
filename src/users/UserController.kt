package com.nocturaf.ktorapi.users

import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class UserController {

    companion object {
        const val ROUTE = "/users"
    }

    fun getAllUsers(): ArrayList<User> {
        val users = arrayListOf<User>()
        transaction {
            Users.selectAll().map {
                users.add(User(
                    id = it[Users.id],
                    fullname = it[Users.fullname],
                    email = it[Users.email]
                ))
            }
        }
        return users
    }

    fun insert(user: User) {
        transaction {
            Users.insert {
                it[fullname] = user.fullname
                it[email] = user.email
            }
        }
    }

}