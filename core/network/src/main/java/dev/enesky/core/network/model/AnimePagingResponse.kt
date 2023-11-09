package dev.enesky.core.network.model

import com.google.gson.annotations.SerializedName

/**
 * Created by Enes Kamil YILMAZ on 28/10/2023
 */
data class AnimePagingResponse(
    val pagination: Pagination,
    val data: List<Anime>,
)

data class Pagination(
    @SerializedName("last_visible_page") val lastVisiblePage: Int,
    @SerializedName("has_next_page") val hasNextPage: Boolean,
    @SerializedName("current_page") val currentPage: Int,
    val items: PaginationItems,
)

data class PaginationItems(
    val count: Int,
    val total: Int,
    @SerializedName("per_page") val perPage: Int,
)
