package ayds.songinfo.moredetails.data

import ayds.songinfo.moredetails.data.external.OtherInfoService
import ayds.songinfo.moredetails.data.local.MoreDetailsLocalRespository
import ayds.songinfo.moredetails.data.local.MoreDetailsLocalRepositoryImpl
import ayds.songinfo.moredetails.domain.ArtistBiography
import ayds.songinfo.moredetails.domain.OtherInfoRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Assert
import org.junit.Test


class OtherInfoRepositoryTest {

    private val otherInfoLocalStorage: MoreDetailsLocalRespository = mockk()
    private val otherInfoService: OtherInfoService = mockk()
    private val otherInfoRepository: OtherInfoRepository = MoreDetailsRepositoryImpl(otherInfoLocalStorage, otherInfoService)

    @Test
    fun `on getArtistInfo call getArticle from local storage`() {
        val artistBiography = ArtistBiography("artist", "biography", "url", false)
        every { otherInfoLocalStorage.getArticleFromDB("artist") } returns artistBiography

        val result = otherInfoRepository.getArtistInfoFromRepository("artist")

        Assert.assertEquals(artistBiography, result)
        Assert.assertTrue(result.isLocallyStored)
    }

    @Test
    fun `on getArtistInfo call getArticle from service`() {
        val artistBiography = ArtistBiography("artist", "biography", "url", false)
        every { otherInfoLocalStorage.getArticleFromDB("artist") } returns null
        every { otherInfoService.getArticle("artist") } returns artistBiography
        every { otherInfoLocalStorage.insertArtistIntoDB(artistBiography) } returns Unit

        val result = otherInfoRepository.getArtistInfoFromRepository("artist")

        Assert.assertEquals(artistBiography, result)
        Assert.assertFalse(result.isLocallyStored)
        verify { otherInfoLocalStorage.insertArtistIntoDB(artistBiography) }
    }

    @Test
    fun `on empty bio, getArtistInfo call getArticle from service`() {
        val artistBiography = ArtistBiography("artist", "", "url", false)
        every { otherInfoLocalStorage.getArticleFromDB("artist") } returns null
        every { otherInfoService.getArticle("artist") } returns artistBiography

        val result = otherInfoRepository.getArtistInfoFromRepository("artist")

        Assert.assertEquals(artistBiography, result)
        Assert.assertFalse(result.isLocallyStored)
        verify(inverse = true) { otherInfoLocalStorage.insertArtistIntoDB(artistBiography) }
    }
}