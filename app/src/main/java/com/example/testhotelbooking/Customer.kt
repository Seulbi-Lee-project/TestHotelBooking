package com.example.testhotelbooking

import java.time.LocalDate

class Customer {
    var name:String
    var roomNum: Int
    var checkInDate: LocalDate
    var checkOutDate: LocalDate

    constructor(name: String, roomNum : Int, checkInDate:LocalDate, checkOutDate:LocalDate){
        this.name = name
        this.roomNum = roomNum
        this.checkInDate = checkInDate
        this.checkOutDate = checkOutDate
    }
}