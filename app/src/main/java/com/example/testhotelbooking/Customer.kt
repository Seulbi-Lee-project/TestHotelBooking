package com.example.testhotelbooking

import java.time.LocalDate

class Customer {
    var name:String
    var roomNum: Int
    var checkInDate: LocalDate
    var checkOutDate: LocalDate
    var income: Int
    var deposit: Int

    constructor(name: String, roomNum : Int, checkInDate:LocalDate, checkOutDate:LocalDate, income:Int, deposit:Int){
        this.name = name
        this.roomNum = roomNum
        this.checkInDate = checkInDate
        this.checkOutDate = checkOutDate
        this.income = income
        this.deposit = deposit
    }
}