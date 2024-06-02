package ayds.songinfo.moredetails.presentation

import ayds.songinfo.moredetails.domain.Card
import ayds.songinfo.moredetails.domain.OtherInfoRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test

class OtherInfoPresenterTest {

    private val otherInfoRepository: OtherInfoRepository = mockk()
    private val artistCardDescriptionHelper: ArtistCardDescriptionHelper = mockk()
    private val otherInfoPresenter: MoreDetailsPresenter =
        MoreDetailsPresenterImpl(otherInfoRepository, artistCardDescriptionHelper)

    @Test
    fun `getArtistInfo should return artist biography ui state`() {
        val artistBiography = Card("artistName", "biography", "articleUrl")
        every { otherInfoRepository.getArtistCardsFromRepository("artistName") } returns artistBiography
        every { artistCardDescriptionHelper.getDescription(artistBiography) } returns "description"
        val artistBiographyTester: (ArtistCardsUIState) -> Unit = mockk(relaxed = true)

        otherInfoPresenter.artistCardsObservable.subscribe(artistBiographyTester)
        otherInfoPresenter.getArtistInfo("artistName")

        val result = ArtistCardsUIState("artistName", "description", "articleUrl")
        verify { artistBiographyTester(result) }
    }
}