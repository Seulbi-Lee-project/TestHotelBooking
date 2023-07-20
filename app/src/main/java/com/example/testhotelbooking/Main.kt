package com.example.testhotelbooking

import java.lang.reflect.Constructor
import java.time.LocalDate
import java.time.format.DateTimeFormatter


var customerList = mutableListOf<Customer>(
    Customer("홍길동"
        , 200
        , LocalDate.parse("2023-08-31", DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        , LocalDate.parse("2023-09-31", DateTimeFormatter.ofPattern("yyyy-MM-dd")))
    , Customer("김철수"
        , 300
        , LocalDate.parse("2023-06-31", DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        , LocalDate.parse("2023-07-03", DateTimeFormatter.ofPattern("yyyy-MM-dd")) )
)

fun main() {
    while (true){
        println("호텔예약 프로그램 입니다.")
        println("[메뉴]")
        println("1. 방예약, 2. 예약 목록, 3. 예약 목록(정렬) 출력, 4. 시스템 종료, 5. 금액 입금-출금 내역 목록 출력, 6. 예약 변경/ 취소")
        var menuSelect = readLine()
        if (menuSelect == "1") {
            println("예약자분의 성함을 알려주세요.")
            var name = readLine()
            println("예약할 방번호를 입력해주세요")
            var roomNum = readLine()!!
            while (roomNum.toInt() < 100 || roomNum.toInt() > 999) {
                // Everything after this is in red
                val red = "\u001b[31m"
                // Resets previous color codes
                val reset = "\u001b[0m"
                println(red + "올바르지 않은 방번호입니다. 방번호는 100~999 영역 이내입니다." + reset)
                roomNum = readLine()!!
            }

            //체크인 날짜 확인
            var checkRoom = true
            lateinit var checkInDate:LocalDate
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
            //체크아웃 날짜
            println("체크아웃 날짜를 입력해주세요 표기방식 : 20230631")
            var checkOut = readLine()!!
            var checkOutDate = LocalDate.parse(checkOut, DateTimeFormatter.ofPattern("yyyyMMdd"))
            while (checkOutDate <= checkInDate) {
                println("체크아웃 날짜는 체크인 날짜보다 같거나 지난 날을 선택할 수 없습니다.")
                checkOut = readLine()!!
                checkOutDate = LocalDate.parse(checkOut, DateTimeFormatter.ofPattern("yyyyMMdd"))
            }
            println("호텔 예약이 완료되었습니다.")
            break
        } else if (menuSelect == "2") {
            println("예약자 목록입니다.")
            if (customerList.isEmpty()) {
                println("예약자가 없습니다.")
            } else {
                println("호텔예약자 목록입니다.")
                var i: Int = 0
                for (index in customerList) {
                    println("${i + 1}. 사용자 : ${customerList[i].name}" +
                            ", 방번호 : ${customerList[i].roomNum}호" +
                            ", 체크인 날짜 : ${customerList[i].checkInDate}" +
                            ", 체크아웃 날짜 : ${customerList[i].checkOutDate}")
                    i++
                }
            }
            continue
        } else if(menuSelect=="3"){
            println("호텔 예약자 목록입니다. (정렬완료)")
            var customerListSort = customerList.sortedWith(compareBy<Customer>{it.checkInDate})
            var i: Int = 0
            for (index in customerListSort) {
                println("${i + 1}. 사용자 : ${customerListSort[i].name}" +
                        ", 방번호 : ${customerListSort[i].roomNum}호" +
                        ", 체크인 날짜 : ${customerListSort[i].checkInDate}" +
                        ", 체크아웃 날짜 : ${customerListSort[i].checkOutDate}")
                i++
            }
            continue
        }else if (menuSelect == "4") {
            println("프로그램을 종료합니다.")
            break
        }else if(menuSelect == "5"){
            println("조회하실 사용자 이름을 입력하세요.")
            var checkName = readLine()
            var i :Int = 0
            for(index in customerList){
                if(checkName == customerList[i].name){
                    var randomIncome = (10000..1000000).random()
                    var randomOutgoings = (10000..randomIncome).random()
                    println("1. 초기금액으로 ${randomIncome}원 입금되었습니다.")
                    println("2. 예약금으로 ${randomOutgoings}원 출금되었습니다.")
                    break
                }else{
                    println("사용자를 찾을 수 없습니다.")
                    break
                }
            }
            continue
        } else {
            println("잘못된 번호입니다.")
            continue
        }
    }
}