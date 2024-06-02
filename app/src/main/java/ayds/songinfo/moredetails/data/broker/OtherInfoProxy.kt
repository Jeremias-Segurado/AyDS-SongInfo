package ayds.songinfo.moredetails.data.broker

import ayds.songinfo.moredetails.domain.Card

interface OtherInfoProxy {
    fun getArtistCardFromExternalService(artistName: String): Card?
}