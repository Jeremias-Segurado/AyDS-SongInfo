package ayds.songinfo.moredetails.domain

import java.util.LinkedList


interface OtherInfoRepository {
    fun getArtistCardsFromRepository(artistName: String): LinkedList<Card>
}
