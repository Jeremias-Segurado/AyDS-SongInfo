package ayds.songinfo.home.view

fun PrecisionDateTOREaleseDate(date: String, precisionDate: String): String =

    when(precisionDate){
        "day" -> PrecisionDAY(date)
        "month" -> PrecisionMONTH(date)
        else ->
    }


private fun PrecisionDAY(date: String): String {
    var toReturn : String = ""
    toReturn += date.slice(listOf(5,6))
    toReturn += "/"+date.slice(listOf(8,9))
    toReturn += "/"+date.slice(listOf(0,3))
    return toReturn
}


private fun PrecisionMONTH(date: String): String {
    var toReturn : String = ""
    val monthAux = date.slice(listOf(5,6))
    when (monthAux)
    {
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
    toReturn += ", "+date.slice(listOf(0,3))
    return toReturn
}

private fun PrecisionYEAR(date: String): String{
    var toReturn : String = ""
    val yearAux = date.slice(listOf(0,3))
    if ((yearAux  4))
}