package ru.example.recipesapp.utils

data class Event<out T>(val status: Status = Status.EMPTY, val data: T?, val error: Error?) {

    companion object {
        fun <T> loading(): Event<T> {
            return Event(Status.LOADING, null, null)
        }

        fun <T> paging(): Event<T> {
            return Event(Status.PAGING, null, null)
        }
        fun <T> success(data: T?): Event<T> {
            return Event(Status.SUCCESS, data, null)
        }

        fun <T> empty(): Event<T> {
            return Event(Status.EMPTY, null, null)
        }

        fun <T> error(error: Error?): Event<T> {
            return Event(Status.ERROR, null, error)
        }

        fun <T> default(): Event<T> {
            return Event(Status.DEFAULT, null, null)
        }
    }
}

enum class Status {
    LOADING,
    SUCCESS,
    PAGING,
    EMPTY,
    ERROR,
    DEFAULT;
}