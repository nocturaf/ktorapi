package com.nocturaf.ktorapi

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*
import io.ktor.routing.*
import io.ktor.http.*
import com.fasterxml.jackson.databind.*
import com.nocturaf.ktorapi.users.User
import com.nocturaf.ktorapi.users.UserController
import io.ktor.jackson.*
import io.ktor.features.*
import org.jetbrains.exposed.sql.Database

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // Referenced in application.conf
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {

    initDB()

    install(CallLogging)

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }

    routing {
        val userController = UserController()

        // default route
        route("/") {
            get {
                call.respond(mapOf(
                    "message" to "Hello World!"
                ))
            }
        }

        // users route
        route(UserController.ROUTE) {

            get {
               val users = userController.getAllUsers()
               call.respond(mapOf(
                   "status" to HttpStatusCode.OK,
                   "data" to users
               ))
           }

           post {
               val newUser = call.receive<User>()
               userController.insert(newUser)
               call.respond(mapOf(
                   "status" to HttpStatusCode.OK,
                   "message" to "Create user success"
               ))
           }
         }
    }
}

fun initDB() {
    Database.connect("jdbc:postgresql://localhost:5432/db_ktor", driver = "org.postgresql.Driver",
        user = "nocturaf", password = "123")
}

data class Product(
    val name: String,
    val price: Int
)

