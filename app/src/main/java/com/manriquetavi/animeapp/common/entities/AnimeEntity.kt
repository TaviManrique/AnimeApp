package com.manriquetavi.animeapp.common.entities

import java.io.Serializable

data class AnimeEntity(
    var id: Int = 0,
    var title: String = "",
    var coverImage: String = "",
    var youtubeVideoId: String = "",
    var creationAt: String = "",
    var description: String = ""
): Serializable
