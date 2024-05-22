package ayds.songinfo.moredetails.presentation

import ayds.observer.Observable
import ayds.observer.Subject
import ayds.songinfo.moredetails.domain.ArtistBiography
import ayds.songinfo.moredetails.domain.OtherInfoRepository

interface MoreDetailsPresenter {
    val artistBiographyObservable: Observable<ArtistBiographyUiState>
    fun getArtistInfo(artistName: String)

}

internal class MoreDetailsPresenterImpl(
    private val repository: OtherInfoRepository,
    private val artistBiographyDescriptionHelper: ArtistBiographyDescriptionHelper
) : MoreDetailsPresenter {

    override val artistBiographyObservable = Subject<ArtistBiographyUiState>()

    override fun getArtistInfo(artistName: String) {
        val artistBiography = repository.getArtistInfoFromRepository(artistName)

        val uiState = artistBiography.toUiState()

        artistBiographyObservable.notify(uiState)
    }

    private fun ArtistBiography.toUiState() = ArtistBiographyUiState(
        artistName,
        artistBiographyDescriptionHelper.getDescription(this),
        articleUrl
    )
}