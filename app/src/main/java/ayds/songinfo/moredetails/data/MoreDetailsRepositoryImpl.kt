package ayds.songinfo.moredetails.data

import ayds.songinfo.moredetails.data.external.OtherInfoService
import ayds.songinfo.moredetails.data.local.MoreDetailsLocalRespository
import ayds.songinfo.moredetails.domain.ArtistBiography
import ayds.songinfo.moredetails.domain.OtherInfoRepository

internal class MoreDetailsRepositoryImpl(
    private val otherInfoLocalStorage: MoreDetailsLocalRespository,
    private val otherInfoService: OtherInfoService,
) : OtherInfoRepository {

    override fun getArtistInfoFromRepository(artistName: String): ArtistBiography {
        val dbArticle = otherInfoLocalStorage.getArticleFromDB(artistName)

        val artistBiography: ArtistBiography

        if (dbArticle != null) {
            artistBiography = dbArticle.apply { markItAsLocal() }
        } else {
            artistBiography = otherInfoService.getArticle(artistName)
            if (artistBiography.biography.isNotEmpty()) {
                otherInfoLocalStorage.insertArtistIntoDB(artistBiography)
            }
        }
        return artistBiography
    }

    private fun ArtistBiography.markItAsLocal() {
       isLocallyStored = true
    }

}