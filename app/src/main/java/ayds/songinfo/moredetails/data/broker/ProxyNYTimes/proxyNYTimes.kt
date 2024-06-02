package ayds.songinfo.moredetails.data.broker.ProxyNYTimes

import ayds.artist.external.newyorktimes.data.NYT_LOGO_URL
import ayds.artist.external.newyorktimes.data.NYTimesArticle
import ayds.artist.external.newyorktimes.data.NYTimesArticle.NYTimesArticleWithData
import ayds.artist.external.newyorktimes.data.NYTimesService
import ayds.songinfo.moredetails.domain.Card
import ayds.songinfo.moredetails.domain.ExternalServices
import ayds.songinfo.moredetails.data.broker.OtherInfoProxy


internal class ProxyNYTimes_Impl(private val nytimesService : NYTimesService): OtherInfoProxy {

    override fun getArtistCardFromExternalService(artistName: String): Card?{
        val nytArticle : NYTimesArticle = nytimesService.getArtistInfo(artistName)
        return when(nytArticle){
            is NYTimesArticleWithData -> nytArticle.info?.let {
                Card(artistName,
                    it,
                    nytArticle.url,
                    NYT_LOGO_URL,
                    ExternalServices.NewYorkTimes
                ) }
            is NYTimesArticle.EmptyArtistDataExternal -> null
        }
    }
}