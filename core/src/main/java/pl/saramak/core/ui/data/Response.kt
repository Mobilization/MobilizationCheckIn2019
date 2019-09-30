package pl.saramak.core.ui.data

sealed class Response<T>
class Success<T>(val response: T) : Response<T>()
class ResError<T>(val exception:Exception) : Response<T>()