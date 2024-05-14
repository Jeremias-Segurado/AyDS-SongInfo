package ayds.songinfo.moredetails.presentation

import ayds.observer.Observable
import ayds.observer.Subject
import ayds.songinfo.moredetails.domain.Entity.ArtistBiography
import ayds.songinfo.moredetails.domain.BiographyRepository

interface moreDetailsPresenter{
    val artistBiographyObservable: Observable<ArtistBiographyUiState>
    fun getArtistInfo(artistName: String)
}

internal class moreDetailsPresenter_imp(
    private val repository: BiographyRepository,
    private val artistBiographyDescriptionHelper: BiographyDescriptionHELPER
):moreDetailsPresenter {
    override val artistBiographyObservable= Subject<ArtistBiographyUiState>()

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

