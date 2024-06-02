package ayds.songinfo.moredetails.data.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import ayds.songinfo.moredetails.domain.ExternalServices

@Database(entities = [CardEntity::class], version = 1)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun CardDao(): CardDao
}

@Entity
data class CardEntity(
    @PrimaryKey
    val artistName: String,
    val biography: String,
    val articleUrl: String,
    val articleURLLogo: String,
    var source: ExternalServices
)

@Dao
interface CardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(article: CardEntity)

    @Query("SELECT * FROM CardEntity WHERE artistName LIKE :artistName")
    fun getArticleByArtistName(artistName: String): List<CardEntity>

}