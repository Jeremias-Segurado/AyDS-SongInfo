package ayds.songinfo.moredetails.data
import ayds.songinfo.moredetails.data.external.externalBiographyRepository
import ayds.songinfo.moredetails.data.local.localBiographyRepository
import ayds.songinfo.moredetails.domain.BiographyRepository
import ayds.songinfo.moredetails.domain.Entity.Biography


internal class BiographyRepositoryImpl(
    private val BiographyLocalStorage: externalBiographyRepository,
    private val BiographyExternalService: localBiographyRepository
) : BiographyRepository {

    private fun getArtistInfoFromRepository(): Biography {
        val artistName = getArtistName()

        val dbArticle = getArticleFromDB(artistName)

        val artistBiography: ArtistBiography

        if (dbArticle != null) {
            artistBiography = dbArticle.markItAsLocal()
        } else {
            artistBiography = getArticleFromService(artistName)
            if (artistBiography.biography.isNotEmpty()) {
                BiographyLocalStorage.insertArtistIntoDB(artistBiography)
            }
        }
        return artistBiography
    }
}

