
package ayds.songinfo.moredetails.data.external

import ayds.songinfo.moredetails.domain.Entity.ArtistBiography
import com.google.gson.Gson
import com.google.gson.JsonObject
import java.io.IOException


interface externalBiographyRepository{
    fun getArticleFromService(artistName: String): ArtistBiography
}
internal class externalBiographyRepository_Imp(
    private val lastFMAPI: LastFMAPI,
    private val ExternalServiceToArtistBiographyMap:externalServiceToArtistBiographyMap
): externalBiographyRepository{

    override fun getArticleFromService(artistName: String): ArtistBiography {
        var artistBiography = ArtistBiography(artistName, "", "")
        try {
            val callResponse = getSongFromService(artistName)
            artistBiography = ExternalServiceToArtistBiographyMap.mapServiceToBiography(callResponse.body(), artistName)
        } catch (e1: IOException) {
            e1.printStackTrace()
        }
        return artistBiography
    }

    private fun getSongFromService(artistName: String) =
        lastFMAPI.getArtistInfo(artistName).execute()

}

