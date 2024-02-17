package dev.pinaki.cannaguide.paging

data class Page(
    val limit: Int = 10,
    val offset: Int = 0,
    val hasNothing: Boolean = false
) {
    fun incremented() = copy(
        offset = offset + limit
    )

    fun ended() = copy(
        hasNothing = true
    )
}