package ayds.artist.external.LastFM.data

import java.io.IOException

interface LastFMAPIService {
    fun getArticle(artistName: String): LastFMArticle?
}
class LastFMAPIServiceImpl(
    private val lastFMAPI: LastFMAPI,
    private val lastFMToArtistBiographyResolver: LastFMToArticleResolver
) : LastFMAPIService {

    override fun getArticle(artistName: String): LastFMArticle? {

        var artistBiography: LastFMArticle? = null
        try {
            val callResponse = getSongFromService(artistName)
            artistBiography = lastFMToArtistBiographyResolver.mapServiceToBiography(callResponse.body(), artistName)
        } catch (e1: IOException) {
            e1.printStackTrace()
        }

        return artistBiography
    }

    private fun getSongFromService(artistName: String) =
        lastFMAPI.getArtistInfo(artistName).execute()

}