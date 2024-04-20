package ayds.songinfo.home.view
import ayds.songinfo.home.model.entities.Song

//----------Factory-------
//Se usaria en el INYECTOR para mantener dependencias y no se que factor SOLID
interface SongDataHelperFactory{
    fun getSongDataHelper(song: Song.SpotifySong): SongDateHelper
}
class SongDataHelperFactoryImpl: SongDataHelperFactory{
    override fun getSongDataHelper(song: Song.SpotifySong): SongDateHelper =
        when (song.releaseDatePrecision) {
            "day" -> SongDataDayHelper(song)
            "month" -> SongDataMonthHelper(song)
            else -> SongDataYearHelper(song)
        }

}
//------------------------
interface SongDateHelper{
    val song: Song.SpotifySong
    fun getPrecisionDate(): String
    
}

internal class SongDataMonthHelper(override val song: Song.SpotifySong):SongDateHelper{
    override fun getPrecisionDate(): String {
        var toReturn: String = ""
        val date = song.releaseDate
        val monthAux = date.slice(listOf(5, 6))
        when (monthAux) {
            "01" -> toReturn += "January "
            "02" -> toReturn += "February "
            "03" -> toReturn += "March"
            "04" -> toReturn += "April"
            "05" -> toReturn += "May"
            "06" -> toReturn += "June"
            "07" -> toReturn += "July"
            "08" -> toReturn += "August"
            "09" -> toReturn += "September"
            "10" -> toReturn += "October"
            "11" -> toReturn += "November"
            "12" -> toReturn += "December"
        }
        toReturn += ", " + date.slice(listOf(0, 1, 2, 3))
        return toReturn
    }
}

internal class SongDataDayHelper(override val song: Song.SpotifySong):SongDateHelper{
    override fun getPrecisionDate(): String {
        var toReturn: String = ""
        val date = song.releaseDate
        toReturn +=  date.slice(listOf(8, 9))
        toReturn += "/" + date.slice(listOf(5, 6))
        toReturn += "/" + date.slice(listOf(0, 1, 2, 3))
        return toReturn
    }
}
internal class SongDataYearHelper(override val song: Song.SpotifySong):SongDateHelper{
    override fun getPrecisionDate(): String {
        var toReturn: String = ""
        val date = song.releaseDate
        val yearAux = date.slice(listOf(0, 1, 2, 3))
        toReturn += yearAux
        if (yearAux.toInt() % 4 == 0)
            toReturn += " (is a leap year)"
        else
            toReturn += " (not a leap year)"
        return toReturn
    }
}
