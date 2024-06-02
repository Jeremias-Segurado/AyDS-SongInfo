package ayds.artist.external.LastFM.data

import com.google.gson.Gson
import com.google.gson.JsonObject

interface LastFMToArticleResolver {
    fun mapServiceToBiography(serviceData: String?, artistName: String): LastFMArticle?
}

private const val ARTIST = "artist"
private const val BIO = "bio"
private const val CONTENT = "content"
private const val URL = "url"
private const val LASTFM_LOGO_URL =
    "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d4/Lastfm_logo.svg/320px-Lastfm_logo.svg.png"

internal class LastFMToArticleResolverImpl : LastFMToArticleResolver {
    override fun mapServiceToBiography(
        serviceData: String?,
        artistName: String
    ): LastFMArticle? {
        val gson = Gson()
        val jobj = gson.fromJson(serviceData, JsonObject::class.java)
        val artist = jobj[ARTIST].getAsJsonObject()
        val bio = artist[BIO].getAsJsonObject()
        val extract = bio[CONTENT]
        val url = artist[URL]
        val text = extract?.asString

        return text?.let{LastFMArticle(text, url.asString, LASTFM_LOGO_URL)}
    }
}