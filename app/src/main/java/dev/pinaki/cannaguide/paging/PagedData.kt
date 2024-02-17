package dev.pinaki.cannaguide.paging

data class PagedData<T>(
    val data: List<T>,
    val nextPage: Page,
)