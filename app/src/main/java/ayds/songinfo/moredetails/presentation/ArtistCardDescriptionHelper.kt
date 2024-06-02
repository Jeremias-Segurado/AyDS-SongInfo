package ayds.songinfo.moredetails.presentation

import ayds.songinfo.moredetails.domain.Card
import java.util.Locale

interface ArtistCardDescriptionHelper {
    fun getDescription(artistCard: Card): String
}

private const val HEADER = "<html><div width=400><font face=\"arial\">"
private const val FOOTER = "</font></div></html>"

internal class ArtistCardDescriptionHelperImpl : ArtistCardDescriptionHelper {

    override fun getDescription(artistCard: Card): String {
        val text = getTextBiography(artistCard)
        return textToHtml(text, artistCard.artistName)
    }

    private fun getTextBiography(artistBiography: Card): String {
        return artistBiography.biography.replace("\\n", "\n")
    }

    private fun textToHtml(text: String, term: String): String {
        val builder = StringBuilder()
        builder.append(HEADER)
        val textWithBold = text
            .replace("'", " ")
            .replace("\n", "<br>")
            .replace(
                "(?i)$term".toRegex(),
                "<b>" + term.uppercase(Locale.getDefault()) + "</b>"
            )
        builder.append(textWithBold)
        builder.append(FOOTER)
        return builder.toString()
    }

    //ToDo:: Hacer una funcion que transforme el Enum.ExternalServices en un stringHTML
}