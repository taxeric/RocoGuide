package com.lanier.rocoguide.base

/**
 * Create by Eric
 * on 2022/12/29
 */
sealed class RocoEventModel<out T: Any> {
    object Loading: RocoEventModel<Nothing>()
    data class Complete<out T: Any>(val entity: T? = null): RocoEventModel<T>()
}
