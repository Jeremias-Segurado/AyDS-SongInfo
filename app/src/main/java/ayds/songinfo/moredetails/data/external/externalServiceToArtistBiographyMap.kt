package ayds.songinfo.moredetails.data.external

import ayds.songinfo.moredetails.domain.Entity.Biography
import com.google.gson.Gson
import com.google.gson.JsonObject


interface externalServiceToArtistBiographyMap{
    fun mapServiceToBiography(serviceData: String?, artistName: String): Biography.ArtistBiography

}


private const val ARTIST = "artist"
private const val BIO = "bio"
private const val CONTENT = "content"
private const val URL = "url"
private const val NO_RESULTS = "No Results"


internal class externalServiceToArtistBiographyMap_implementacion: externalServiceToArtistBiographyMap {
    override fun mapServiceToBiography(
        serviceData: String?,
        artistName: String
    ): Biography.ArtistBiography {
        val gson = Gson()
        val jobj = gson.fromJson(serviceData, JsonObject::class.java)
        val artist = jobj[ARTIST].getAsJsonObject()
        val bio = artist[BIO].getAsJsonObject()
        val extract = bio[CONTENT]
        val url = artist[URL]
        val text = extract?.asString ?: NO_RESULTS

        return Biography.ArtistBiography(artistName, text, url.asString)
    }
}