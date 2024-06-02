package ayds.songinfo.moredetails.data.broker

import ayds.songinfo.moredetails.domain.Card
import java.util.LinkedList

interface ArtistBiographyBroker{
    fun getInfoArticlesFromServices(artisName: String) : LinkedList<Card>
}

internal class ArtistBiographyBrokerImpl(
    private val proxyList: LinkedList<OtherInfoProxy>
): ArtistBiographyBroker{

    override fun getInfoArticlesFromServices(artisName: String) : LinkedList<Card>{
        var cardAux:Card?
        val articles = LinkedList<Card>()

        for (proxy in proxyList){
            cardAux = proxy.getArtistCardFromExternalService(artisName)
            cardAux?.let{
                articles.addLast(cardAux)
            }
        }
        return articles
    }

}