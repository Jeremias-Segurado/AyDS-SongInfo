package ayds.songinfo.moredetails.domain
import ayds.songinfo.moredetails.domain.Entity.Biography

interface BiographyRepository{
    fun getArtistInfoFromRepository(artistName: String): Biography
}