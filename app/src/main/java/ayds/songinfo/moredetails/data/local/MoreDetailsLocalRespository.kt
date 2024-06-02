package ayds.songinfo.moredetails.data.local

import ayds.songinfo.moredetails.domain.Card
import java.util.LinkedList

interface MoreDetailsLocalRespository {
    fun getArticleFromDB(artistName: String): LinkedList<Card>
    fun insertArtistIntoDB(artistBiography: Card)
}

internal class MoreDetailsLocalRepositoryImpl(private val articleDatabase: ArticleDatabase) : MoreDetailsLocalRespository {

    private val cardDao = articleDatabase.CardDao()
    override fun getArticleFromDB(artistName: String): LinkedList<Card> {
        val artistInfoList = cardDao.getArticleByArtistName(artistName)
        val artistCardList = LinkedList<Card>()
        for (cardEntity in artistInfoList) {
            artistCardList.addLast(Card(
                cardEntity.artistName,
                cardEntity.biography,
                cardEntity.articleUrl,
                cardEntity.articleURLLogo,
                cardEntity.source
            ))
        }
        return artistCardList
    }

    override fun insertArtistIntoDB(artistBiography: Card) {
        cardDao.insertArticle(
            CardEntity(
                artistBiography.artistName,
                artistBiography.biography,
                artistBiography.articleUrl,
                artistBiography.articleURLLogo,
                artistBiography.source
            )
        )
    }
}