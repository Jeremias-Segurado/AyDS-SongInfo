package ayds.songinfo.moredetails.data.broker.ProxyWiki

import ayds.artist.external.wikipedia.data.WikipediaArticle
import ayds.artist.external.wikipedia.data.WikipediaTrackService
import ayds.songinfo.moredetails.domain.Card
import ayds.songinfo.moredetails.domain.ExternalServices
import ayds.songinfo.moredetails.data.broker.OtherInfoProxy


internal class ProxyWikipedia_Impl(private val wikipediaService : WikipediaTrackService):
    OtherInfoProxy {

    override fun getArtistCardFromExternalService(artistName: String): Card? {
        val wikipediaArticle: WikipediaArticle? = wikipediaService.getInfo(artistName)
        return wikipediaArticle?.let {
            Card(
                artistName,
                wikipediaArticle.description,
                wikipediaArticle.wikipediaURL,
                wikipediaArticle.wikipediaLogoURL,
                ExternalServices.Wikipedia
            )
        }
    }
}