package ayds.songinfo.moredetails.presentation

import java.util.LinkedList

data class ArtistCardsUIState(
    val cards: LinkedList<CardInfo>
)
data class CardInfo(
    val artistName: String,
    val biographyHTML: String,
    val articleUrl: String,
    val articleURLLogo: String,
    var source: String = "Source Not Found"
)