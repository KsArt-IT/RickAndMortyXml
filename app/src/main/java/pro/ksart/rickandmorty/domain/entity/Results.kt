package pro.ksart.rickandmorty.domain.entity

sealed class Results<out T : Any> {

    data class Success<out T : Any>(val data: T) : Results<T>()

    data class Error(val message: String) : Results<Nothing>()

    object Loading : Results<Nothing>()

}
