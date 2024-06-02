package ayds.songinfo.moredetails.domain

data class Card(
    val artistName: String,
    val biography: String,
    val articleUrl: String,
    val articleURLLogo: String,
    var source: ExternalServices = ExternalServices.NoService
)

enum class ExternalServices{
    LastFM,
    Wikipedia,
    NewYorkTimes,
    NoService
}





