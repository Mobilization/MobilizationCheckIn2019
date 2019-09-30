package pl.saramak.mobilizationcheckin2019

import pl.saramak.data.User

interface LoginService {
    suspend fun login(email:String, password:String) : User
}