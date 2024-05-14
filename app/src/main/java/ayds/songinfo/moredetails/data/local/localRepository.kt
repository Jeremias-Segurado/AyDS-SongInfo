package ayds.songinfo.moredetails.data.local

import ayds.songinfo.moredetails.domain.Entity.ArtistBiography

interface localBiographyRepository{
    fun getArticleFromDB(artistName: String): ArtistBiography?
    fun insertArtistIntoDB(artistBiography: ArtistBiography)
}

class localRepository_Imp(val articleDatabase: ArticleDatabase):localBiographyRepository{

    private val articleDao = articleDatabase.ArticleDao()
    override fun getArticleFromDB(artistName: String): ArtistBiography? {
        val artistEntity = articleDao.getArticleByArtistName(artistName)
        return artistEntity?.let {
            ArtistBiography(artistName, artistEntity.biography, artistEntity.articleUrl)
        }
    }

    override fun insertArtistIntoDB(artistBiography: ArtistBiography) {
        articleDao.insertArticle(
            ArticleEntity(
                artistBiography.artistName, artistBiography.biography, artistBiography.articleUrl
            )
        )
    }
}
