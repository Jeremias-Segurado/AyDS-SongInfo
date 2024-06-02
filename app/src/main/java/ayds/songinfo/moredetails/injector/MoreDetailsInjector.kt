package ayds.songinfo.moredetails.injector

import android.content.Context
import androidx.room.Room
import ayds.songinfo.moredetails.data.MoreDetailsRepositoryImpl
import ayds.artist.external.LastFM.inyector.LastFMInjector
import ayds.artist.external.newyorktimes.injector.NYTimesInjector
import ayds.artist.external.wikipedia.injector.WikipediaInjector
import ayds.songinfo.moredetails.data.broker.ArtistBiographyBrokerImpl
import ayds.songinfo.moredetails.data.broker.OtherInfoProxy
import ayds.songinfo.moredetails.data.broker.ProxyLastFM.ProxyLastFM_Impl
import ayds.songinfo.moredetails.data.broker.ProxyNYTimes.ProxyNYTimes_Impl
import ayds.songinfo.moredetails.data.broker.ProxyWiki.ProxyWikipedia_Impl
import ayds.songinfo.moredetails.data.local.ArticleDatabase
import ayds.songinfo.moredetails.data.local.MoreDetailsLocalRepositoryImpl
import ayds.songinfo.moredetails.presentation.ArtistCardDescriptionHelperImpl
import ayds.songinfo.moredetails.presentation.MoreDetailsPresenter
import ayds.songinfo.moredetails.presentation.MoreDetailsPresenterImpl
import java.util.LinkedList


private const val ARTICLE_BD_NAME = "database-article"

object MoreDetailsInjector {

    lateinit var presenter: MoreDetailsPresenter

    fun initGraph(context: Context) {
        val articleDatabase =
            Room.databaseBuilder(context, ArticleDatabase::class.java, ARTICLE_BD_NAME).build()
        val articleLocalStorage = MoreDetailsLocalRepositoryImpl(articleDatabase)
        val proxyList = LinkedList<OtherInfoProxy>()
        proxyList.addLast(ProxyNYTimes_Impl(NYTimesInjector.nyTimesService))
        proxyList.addLast(ProxyWikipedia_Impl(WikipediaInjector.wikipediaTrackService))
        proxyList.addLast(ProxyLastFM_Impl(LastFMInjector.lastFMAPIService))
        val artistCardBroker = ArtistBiographyBrokerImpl(proxyList)
        val repository = MoreDetailsRepositoryImpl(articleLocalStorage, artistCardBroker)
        val artistBiographyDescriptionHelper = ArtistCardDescriptionHelperImpl()

        presenter = MoreDetailsPresenterImpl(repository, artistBiographyDescriptionHelper)
    }
}