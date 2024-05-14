package ayds.songinfo.moredetails.presentation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.text.HtmlCompat
import ayds.songinfo.R
import ayds.songinfo.moredetails.Dependency_Inyector.moreDetailsInyector
import ayds.songinfo.moredetails.domain.Entity.ArtistBiography
import com.squareup.picasso.Picasso
import java.util.Locale

class moreDetailsView : Activity(){
    private lateinit var articleTextView: TextView
    private lateinit var openUrlButton: Button
    private lateinit var lastFMImageView: ImageView

    private lateinit var presenter: moreDetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)

        initViewProperties()
        initPresenter()

        observePresenter()
        getArtistInfoAsync()
    }

    private fun getArtistInfoAsync() {
        Thread {
            presenter.getArtistInfo(getArtistName())
        }.start()
    }

    private fun observePresenter() {
        presenter.artistBiographyObservable.subscribe { artistBiography ->
            updateUi(artistBiography)
        }
    }

    private fun initViewProperties() {
        articleTextView = findViewById(R.id.textPane1)
        openUrlButton = findViewById(R.id.openUrlButton)
        lastFMImageView = findViewById(R.id.lastFMImageView)
    }

    private fun initPresenter() {
        moreDetailsInyector.initGraph(this)
        presenter = moreDetailsInyector.presenter
    }


    private fun getArtistName() =
        intent.getStringExtra(ARTIST_NAME_EXTRA) ?: throw Exception("Missing artist name")

    private fun updateUi(uiState: ArtistBiographyUiState) {
        runOnUiThread {
            updateOpenUrlButton(uiState.articleUrl)
            updateLastFMLogo(uiState.imageUrl)
            updateArticleText(uiState.infoHtml)
        }
    }

    private fun updateOpenUrlButton(url: String) {
        openUrlButton.setOnClickListener {
            navigateToUrl(url)
        }
    }

    private fun navigateToUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setData(Uri.parse(url))
        startActivity(intent)
    }

    private fun updateLastFMLogo(url: String) {
        Picasso.get().load(url).into(lastFMImageView)
    }

    private fun updateArticleText(infoHtml: String) {
        articleTextView.text = HtmlCompat.fromHtml(infoHtml, HtmlCompat.FROM_HTML_MODE_LEGACY);
    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }

}


