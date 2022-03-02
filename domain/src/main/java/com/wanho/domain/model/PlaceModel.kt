package com.wanho.domain.model

interface PlaceModel {
    val id : String
    val name : String
    val category : String
    val phone : String
    val distance : Int
    val placeUrl : String
    val roadAddressName : String
    val date : String?
    val latitude : Double
    val longitude : Double
}