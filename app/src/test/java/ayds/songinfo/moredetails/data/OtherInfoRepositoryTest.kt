package ayds.songinfo.moredetails.data

import ayds.artist.external.LastFM.OtherInfoService
import ayds.songinfo.moredetails.data.local.MoreDetailsLocalRespository
import ayds.songinfo.moredetails.domain.Card
import ayds.songinfo.moredetails.domain.OtherInfoRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert
import org.junit.Test


class OtherInfoRepositoryTest {

    private val otherInfoLocalStorage: MoreDetailsLocalRespository = mockk()
    private val otherInfoService: ayds.artist.external.LastFM.OtherInfoService = mockk()
    private val otherInfoRepository: OtherInfoRepository = MoreDetailsRepositoryImpl(otherInfoLocalStorage, otherInfoService)

    @Test
    fun `on getArtistInfo call getArticle from local storage`() {
        val artistBiography = Card("artist", "biography", "url", false)
        every { otherInfoLocalStorage.getArticleFromDB("artist") } returns artistBiography

        val result = otherInfoRepository.getArtistCardsFromRepository("artist")

        Assert.assertEquals(artistBiography, result)
        Assert.assertTrue(result.isLocallyStored)
    }

    @Test
    fun `on getArtistInfo call getArticle from service`() {
        val artistBiography = Card("artist", "biography", "url", false)
        every { otherInfoLocalStorage.getArticleFromDB("artist") } returns null
        every { otherInfoService.getArticle("artist") } returns artistBiography
        every { otherInfoLocalStorage.insertArtistIntoDB(artistBiography) } returns Unit

        val result = otherInfoRepository.getArtistCardsFromRepository("artist")

        Assert.assertEquals(artistBiography, result)
        Assert.assertFalse(result.isLocallyStored)
        verify { otherInfoLocalStorage.insertArtistIntoDB(artistBiography) }
    }

    @Test
    fun `on empty bio, getArtistInfo call getArticle from service`() {
        val artistBiography = Card("artist", "", "url", false)
        every { otherInfoLocalStorage.getArticleFromDB("artist") } returns null
        every { otherInfoService.getArticle("artist") } returns artistBiography

        val result = otherInfoRepository.getArtistCardsFromRepository("artist")

        Assert.assertEquals(artistBiography, result)
        Assert.assertFalse(result.isLocallyStored)
        verify(inverse = true) { otherInfoLocalStorage.insertArtistIntoDB(artistBiography) }
    }
}