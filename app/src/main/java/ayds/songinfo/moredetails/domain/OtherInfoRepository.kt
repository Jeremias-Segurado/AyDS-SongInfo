package ayds.songinfo.moredetails.domain


interface OtherInfoRepository {
    fun getArtistInfoFromRepository(artistName: String): ArtistBiography
}
