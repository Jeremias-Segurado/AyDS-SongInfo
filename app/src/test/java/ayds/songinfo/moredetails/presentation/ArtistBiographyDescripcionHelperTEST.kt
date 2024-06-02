package ayds.songinfo.moredetails.presentation

import ayds.songinfo.moredetails.domain.Card
import org.junit.Assert
import org.junit.Test


class ArtistCardDescriptionHelperTest {

    private val artistCardDescriptionHelper: ArtistCardDescriptionHelper =
        ArtistCardDescriptionHelperImpl()

    @Test
    fun `on local stored artist should return biography`() {
        val artistBiography = Card("artist", "biography", "url", true)

        val result = artistCardDescriptionHelper.getDescription(artistBiography)

        Assert.assertEquals(
            "<html><div width=400><font face=\"arial\">[*]biography</font></div></html>",
            result
        )
    }

    @Test
    fun `on no local stored artist should return biography`() {
        val artistBiography = Card("artist", "biography", "url", false)

        val result = artistCardDescriptionHelper.getDescription(artistBiography)

        Assert.assertEquals(
            "<html><div width=400><font face=\"arial\">biography</font></div></html>",
            result
        )
    }
    @Test
    fun `should remove apostrophes`() {
        val artistBiography = Card("artist", "biography'n", "url", false)

        val result = artistCardDescriptionHelper.getDescription(artistBiography)

        Assert.assertEquals(
            "<html><div width=400><font face=\"arial\">biography n</font></div></html>",
            result
        )
    }

    @Test
    fun `should fix on double slash`() {
        val artistBiography = Card("artist", "biography\\n", "url", false)

        val result = artistCardDescriptionHelper.getDescription(artistBiography)

        Assert.assertEquals(
            "<html><div width=400><font face=\"arial\">biography<br></font></div></html>",
            result
        )
    }

    @Test
    fun `should map break lines`() {
        val artistBiography = Card("artist", "biography\n", "url", false)

        val result = artistCardDescriptionHelper.getDescription(artistBiography)

        Assert.assertEquals(
            "<html><div width=400><font face=\"arial\">biography<br></font></div></html>",
            result
        )
    }
    @Test
    fun `should set artist name bold`() {
        val artistBiography = Card("artist", "biography artist", "url", false)

        val result = artistCardDescriptionHelper.getDescription(artistBiography)

        Assert.assertEquals(
            "<html><div width=400><font face=\"arial\">biography <b>ARTIST</b></font></div></html>",
            result
        )
    }

}