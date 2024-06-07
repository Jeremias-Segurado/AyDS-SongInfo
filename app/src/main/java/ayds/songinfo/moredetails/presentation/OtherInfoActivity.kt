package ayds.songinfo.moredetails.presentation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.text.style.URLSpan
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.view.children
import ayds.songinfo.R
import ayds.songinfo.moredetails.injector.MoreDetailsInjector
import com.squareup.picasso.Picasso
import java.util.LinkedList

private const val umlTextView_Filter_NAME = "artistInfo"
private const val urlButton_Filter_NAME = "openUrl"
private const val sourceTextView_Filter_NAME = "source"
private const val logoImageView_Filter_NAME = "serviceLogo"

class OtherInfoActivity : Activity(), View.OnClickListener {
    private lateinit var urlButtonList: LinkedList<Button>
    private lateinit var cardTextViewList: LinkedList<TextView>
    private lateinit var logoImageViewList: LinkedList<ImageView>
    private lateinit var sourceTextViewList: LinkedList<TextView>
    private lateinit var urlsArray: Array<String?>

    private lateinit var presenter: MoreDetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_other_info)

        initViewProperties()
        initPresenter()

        observePresenter()
        getArtistInfoAsync()
    }

    private fun initPresenter() {
        MoreDetailsInjector.initGraph(this)
        presenter = MoreDetailsInjector.presenter
    }

    private fun observePresenter() {
        presenter.artistCardsObservable.subscribe { artistBiography ->
            updateUi(artistBiography)
        }
    }

    private fun initViewProperties() {
        logoImageViewList = LinkedList<ImageView>()
        urlButtonList = LinkedList<Button>()
        cardTextViewList = LinkedList<TextView>()
        sourceTextViewList = LinkedList<TextView>()


        val scrollView = findViewById<ScrollView>(R.id.scrollView)
        val linearLayout: LinearLayout = (scrollView.getChildAt(0) as LinearLayout)
        var linearLayoutChild: View

        for (i in 0..<linearLayout.childCount){
            linearLayoutChild = linearLayout.getChildAt(i)
            when(linearLayoutChild){
                is ImageView -> {
                    if(resources.getResourceName(linearLayoutChild.id).contains(
                            logoImageView_Filter_NAME))
                        logoImageViewList.addLast(linearLayoutChild)}
                is Button -> {
                    if(resources.getResourceName(linearLayoutChild.id).contains(
                            urlButton_Filter_NAME))
                    urlButtonList.addLast(linearLayoutChild)}
                is TextView ->{
                    if (resources.getResourceName(linearLayoutChild.id).contains(
                            umlTextView_Filter_NAME))
                        cardTextViewList.addLast(linearLayoutChild)
                    if(resources.getResourceName(linearLayoutChild.id).contains(
                            sourceTextView_Filter_NAME))
                        sourceTextViewList.addLast(linearLayoutChild)
                }
            }
        }
        urlsArray = arrayOfNulls<String>(urlButtonList.count())
    }

    private fun getArtistInfoAsync() {
        Thread {
            getArtistInfo()
        }.start()
    }

    private fun getArtistInfo() {
        val artistName = getArtistName()
        presenter.getArtistInfo(artistName)
    }


    private fun updateUi(uiState: ArtistCardsUIState) {
        runOnUiThread {
            for(i in 0..<uiState.cards.count()){
                updateOpenUrlButton(uiState.cards[i].articleUrl, i)
                updateServiceLogo(uiState.cards[i].articleURLLogo, i)
                updateArticleText(uiState.cards[i].biographyHTML, i)
                updateSourceTextView(uiState.cards[i].source, i)
            }

        }
    }
    //ToDo:: Modificar desde aca el texto dentro del boton utilizando el source de la Card
    private fun updateOpenUrlButton(url: String, indexButton: Int) {
        urlButtonList[indexButton].setOnClickListener(this)
        urlButtonList[indexButton].visibility = View.VISIBLE
        urlsArray[indexButton] = url
    }

    private fun navigateToUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setData(Uri.parse(url))
        startActivity(intent)
    }

    private fun updateServiceLogo(url: String, indexLogo: Int) {
        Picasso.get().load(url).into(logoImageViewList[indexLogo])
    }

    private fun updateSourceTextView(source: String, indexSource: Int){
        sourceTextViewList[indexSource].text = source
    }
    private fun getArtistName() =
        intent.getStringExtra(ARTIST_NAME_EXTRA) ?: throw Exception("Missing artist name")

    //ToDo::CAmbiar func deprecated
    private fun updateArticleText(infoHtml: String, indexCardText: Int) {
        cardTextViewList[indexCardText].text = Html.fromHtml(infoHtml)
    }

    companion object {
        const val ARTIST_NAME_EXTRA = "artistName"
    }

    override fun onClick(v: View?) {
        if (v is Button)
            urlsArray[urlButtonList.indexOf(v)]?.let { navigateToUrl(it) }
    }

}


