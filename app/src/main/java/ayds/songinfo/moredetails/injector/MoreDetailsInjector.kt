package ayds.songinfo.moredetails.injector

import android.content.Context
import androidx.room.Room
import ayds.songinfo.moredetails.data.MoreDetailsRepositoryImpl
import ayds.songinfo.moredetails.data.external.LastFMAPI
import ayds.songinfo.moredetails.data.external.LastFMToArtistBiographyResolverImpl
import ayds.songinfo.moredetails.data.external.OtherInfoServiceImpl
import ayds.songinfo.moredetails.data.local.ArticleDatabase
import ayds.songinfo.moredetails.data.local.MoreDetailsLocalRepositoryImpl
import ayds.songinfo.moredetails.presentation.ArtistBiographyDescriptionHelperImpl
import ayds.songinfo.moredetails.presentation.MoreDetailsPresenter
import ayds.songinfo.moredetails.presentation.MoreDetailsPresenterImpl
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory


private const val ARTICLE_BD_NAME = "database-article"
private const val LASTFM_BASE_URL = "https://ws.audioscrobbler.com/2.0/"

object MoreDetailsInjector {

    lateinit var presenter: MoreDetailsPresenter

    fun initGraph(context: Context) {

        val articleDatabase =
            Room.databaseBuilder(context, ArticleDatabase::class.java, ARTICLE_BD_NAME).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(LASTFM_BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        val lastFMAPI = retrofit.create(LastFMAPI::class.java)

        val lastFMToArtistBiographyResolver = LastFMToArtistBiographyResolverImpl()
        val otherInfoService = OtherInfoServiceImpl(lastFMAPI, lastFMToArtistBiographyResolver)
        val articleLocalStorage = MoreDetailsLocalRepositoryImpl(articleDatabase)

        val repository = MoreDetailsRepositoryImpl(articleLocalStorage, otherInfoService)

        val artistBiographyDescriptionHelper = ArtistBiographyDescriptionHelperImpl()

        presenter = MoreDetailsPresenterImpl(repository, artistBiographyDescriptionHelper)
    }
}