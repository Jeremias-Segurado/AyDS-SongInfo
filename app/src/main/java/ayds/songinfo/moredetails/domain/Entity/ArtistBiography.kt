package ayds.songinfo.moredetails.domain.Entity

import ayds.songinfo.home.model.entities.Song

sealed class Biography{
    data class ArtistBiography(
        val artistName: String,
        val biography: String,
        val articleUrl: String
    ) : Biography() {

    }
}