/*
 *                          Copyright 2023
 *            Designed and developed by Enes Kamil Yılmaz
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package dev.enesky.core.data.base

import com.google.gson.annotations.SerializedName

/**
 * Created by Enes Kamil YILMAZ on 28/10/2023
 */
data class BasePagingResponse<T>(
    val pagination: Pagination,
    val data: List<T>,
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
