package com.example.testhotelbooking

import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CheckService {
    //체크인 날짜 확인
    fun checkInDate(roomNum:String):LocalDate{
        var checkRoom = true
        lateinit var checkInDate: LocalDate
        while (checkRoom){
            println("체크인 날짜를 입력해주세요 표기방식. 20230631")
            var checkIn = readLine()!!
            checkInDate = LocalDate.parse(checkIn, DateTimeFormatter.ofPattern("yyyyMMdd"))
            var toDay = LocalDate.now()
            while (checkInDate < toDay) {
                println("체크인은 지난날은 선택할 수 없습니다.")
                checkIn = readLine()!!
                checkInDate = LocalDate.parse(checkIn, DateTimeFormatter.ofPattern("yyyyMMdd"))
            }
            //방번호와 체크인 날짜 확인하기
            var i = 0
            for(index in customerList){
                if(roomNum.toInt() == customerList[i].roomNum){
                    if(checkInDate >= customerList[i].checkInDate && checkInDate <= customerList[i].checkOutDate){
                        println("같은 날에 체크인 할 수 없습니다. 다른 날짜를 선택해 주세요.")
                        break
                    }else{
                        checkRoom = false
                        break
                    }
                }else{
                    checkRoom = false
                    break
                }
            }
        }
        return checkInDate
    }

    //체크아웃
    fun checkOutDate(checkInDate:LocalDate):LocalDate{
        println("체크아웃 날짜를 입력해주세요 표기방식 : 20230631")
        var checkOut = readLine()!!
        var checkOutDate = LocalDate.parse(checkOut, DateTimeFormatter.ofPattern("yyyyMMdd"))
        while (checkOutDate <= checkInDate) {
            println("체크아웃 날짜는 체크인 날짜보다 같거나 지난 날을 선택할 수 없습니다.")
            checkOut = readLine()!!
            checkOutDate = LocalDate.parse(checkOut, DateTimeFormatter.ofPattern("yyyyMMdd"))
        }
        return checkOutDate
    }
}