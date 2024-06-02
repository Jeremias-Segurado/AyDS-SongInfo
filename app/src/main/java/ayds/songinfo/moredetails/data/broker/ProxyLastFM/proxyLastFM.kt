package ayds.songinfo.moredetails.data.broker.ProxyLastFM


import ayds.artist.external.LastFM.data.LastFMAPIService
import ayds.artist.external.LastFM.data.LastFMArticle
import ayds.artist.external.wikipedia.data.WikipediaArticle
import ayds.songinfo.moredetails.data.broker.OtherInfoProxy
import ayds.songinfo.moredetails.domain.Card
import ayds.songinfo.moredetails.domain.ExternalServices


internal class ProxyLastFM_Impl(private val lastFMAPIService: LastFMAPIService ): OtherInfoProxy{

        override fun getArtistCardFromExternalService(artistName: String): Card? {
                val lastFMArticle: LastFMArticle? = lastFMAPIService.getArticle(artistName)
                return lastFMArticle?.let{Card(
                        artistName,
                        lastFMArticle.description,
                        lastFMArticle.lastFMURL,
                        lastFMArticle.lastFMLogoURL,
                        ExternalServices.LastFM
                )}
        }
}