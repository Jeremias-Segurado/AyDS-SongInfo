package ayds.songinfo.moredetails.domain
import ayds.songinfo.moredetails.domain.Entity.ArtistBiography

interface BiographyRepository{
    fun getArtistInfoFromRepository(artistName: String): ArtistBiography
}