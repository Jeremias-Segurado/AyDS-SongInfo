package ayds.songinfo.moredetails.data

import ayds.songinfo.moredetails.data.broker.ArtistBiographyBroker
import ayds.songinfo.moredetails.data.local.MoreDetailsLocalRespository
import ayds.songinfo.moredetails.domain.Card
import ayds.songinfo.moredetails.domain.ExternalServices
import ayds.songinfo.moredetails.domain.OtherInfoRepository
import java.util.LinkedList

internal class MoreDetailsRepositoryImpl(
    private val otherInfoLocalStorage: MoreDetailsLocalRespository,
    private val otherInfoServiceBroker: ArtistBiographyBroker,
) : OtherInfoRepository {

    override fun getArtistCardsFromRepository(artistName: String): LinkedList<Card> {
        val dbArticleList = otherInfoLocalStorage.getArticleFromDB(artistName)
        val artistCardList: LinkedList<Card>

        if(dbArticleList.count() < ExternalServices.entries.count()){
            artistCardList = otherInfoServiceBroker.getInfoArticlesFromServices(artistName)
            if (artistCardList.isNotEmpty()) {
                for (card in artistCardList)
                    otherInfoLocalStorage.insertArtistIntoDB(card)
           }
        }else
            artistCardList = dbArticleList
        return artistCardList
    }


}