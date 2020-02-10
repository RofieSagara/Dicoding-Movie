package com.sagara.dicodingmovie.utils.livedata

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 * Created by Jose Alcérreca
 * Developer Relations Engineer @ Google, working on Android
 */
open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}