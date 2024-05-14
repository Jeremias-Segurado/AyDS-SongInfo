package ayds.songinfo.moredetails.domain.Entity

import ayds.songinfo.home.model.entities.Song


data class ArtistBiography(
    val artistName: String,
    val biography: String,
    val articleUrl: String,
    var isLocallyStored: Boolean = false
)
