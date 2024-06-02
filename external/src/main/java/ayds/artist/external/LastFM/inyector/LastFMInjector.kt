package ayds.artist.external.LastFM.inyector



import ayds.artist.external.LastFM.data.LastFMAPI
import ayds.artist.external.LastFM.data.LastFMAPIServiceImpl
import ayds.artist.external.LastFM.data.LastFMToArticleResolver
import ayds.artist.external.LastFM.data.LastFMToArticleResolverImpl
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val LASTFM_BASE_URL = "https://ws.audioscrobbler.com/2.0/"

object LastFMInjector {

    private val retrofit = Retrofit.Builder()
        .baseUrl(LASTFM_BASE_URL)
        .addConverterFactory(ScalarsConverterFactory.create())
        .build()

    private val lastFMAPI = retrofit.create(LastFMAPI::class.java)
    private val lastFMToArticleResolver: LastFMToArticleResolver = LastFMToArticleResolverImpl()

    val lastFMAPIService = LastFMAPIServiceImpl(
        lastFMAPI,
        lastFMToArticleResolver
    )


}