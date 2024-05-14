package ayds.songinfo.moredetails.Dependency_Inyector

import android.content.Context
import androidx.room.Room
import ayds.songinfo.moredetails.data.BiographyRepositoryImpl
import ayds.songinfo.moredetails.data.external.LastFMAPI
import ayds.songinfo.moredetails.data.external.externalBiographyRepository_Imp
import ayds.songinfo.moredetails.data.external.externalServiceToArtistBiographyMap_implementacion
import ayds.songinfo.moredetails.data.local.ArticleDatabase
import ayds.songinfo.moredetails.data.local.localRepository_Imp
import ayds.songinfo.moredetails.presentation.ArtistBiographyDescriptionHelperImpl
import ayds.songinfo.moredetails.presentation.moreDetailsPresenter
import ayds.songinfo.moredetails.presentation.moreDetailsPresenter_imp
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val ARTICLE_BD_NAME = "database-article"
private const val LASTFM_BASE_URL = "https://ws.audioscrobbler.com/2.0/"

object moreDetailsInyector {

    lateinit var presenter: moreDetailsPresenter

    fun initGraph(context: Context) {

        val articleDatabase =
            Room.databaseBuilder(context, ArticleDatabase::class.java, ARTICLE_BD_NAME).build()

        val retrofit = Retrofit.Builder()
            .baseUrl(LASTFM_BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
        val lastFMAPI = retrofit.create(LastFMAPI::class.java)

        val lastFMToArtistBiographyResolver = externalServiceToArtistBiographyMap_implementacion()
        val otherInfoService =
            externalBiographyRepository_Imp(lastFMAPI, lastFMToArtistBiographyResolver)
        val articleLocalStorage = localRepository_Imp(articleDatabase)

        val repository = BiographyRepositoryImpl(articleLocalStorage, otherInfoService)

        val artistBiographyDescriptionHelper = ArtistBiographyDescriptionHelperImpl()

        presenter = moreDetailsPresenter_imp(repository, artistBiographyDescriptionHelper)
    }
}