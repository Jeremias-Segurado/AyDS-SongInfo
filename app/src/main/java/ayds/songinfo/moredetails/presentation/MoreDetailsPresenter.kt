package ayds.songinfo.moredetails.presentation

import ayds.observer.Observable
import ayds.observer.Subject
import ayds.songinfo.moredetails.domain.Card
import ayds.songinfo.moredetails.domain.OtherInfoRepository
import java.util.LinkedList

interface MoreDetailsPresenter {
    val artistCardsObservable: Observable<ArtistCardsUIState>
    fun getArtistInfo(artistName: String)
}

internal class MoreDetailsPresenterImpl(
    private val repository: OtherInfoRepository,
    private val artistCardDescriptionHelper: ArtistCardDescriptionHelper
) : MoreDetailsPresenter {

    override val artistCardsObservable = Subject<ArtistCardsUIState>()

    override fun getArtistInfo(artistName: String) {
        val artistBiography = repository.getArtistCardsFromRepository(artistName)
        val uiState = artistBiography.toUiState()

        artistCardsObservable.notify(uiState)
    }

    private fun LinkedList<Card>.toUiState(): ArtistCardsUIState {
        val artistCardsUIStateReturn = ArtistCardsUIState(LinkedList<CardInfo>())
        for (card in this){
            artistCardsUIStateReturn.cards.addLast(
                CardInfo(
                    card.artistName,
                    artistCardDescriptionHelper.getDescription(card),
                    card.articleUrl,
                    card.articleURLLogo,
                    card.source.toString() ))
        }
        return artistCardsUIStateReturn
    }

}