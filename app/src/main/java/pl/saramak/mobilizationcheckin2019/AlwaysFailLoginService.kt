package pl.saramak.mobilizationcheckin2019

import kotlinx.coroutines.delay
import mu.KotlinLogging
import pl.saramak.data.User
import java.lang.NullPointerException


class AlwaysFailLoginService : LoginService {
    private val logger = KotlinLogging.logger {}
    override suspend fun login(email: String, password: String) : User {
        logger.info { "email: $email password: $password" }
        delay(2000)
        if (email!=password){
            throw NullPointerException("Wrong email or password")
        }
        return User(email, password)
    }
}