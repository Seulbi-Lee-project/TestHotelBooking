package com.example.testhotelbooking

import java.lang.reflect.Constructor
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter


var customerList = mutableListOf<Customer>(
    Customer("홍길동"
        , 200
        , LocalDate.parse("2023-08-31", DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        , LocalDate.parse("2023-09-31", DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        , 10000, 1000)
    , Customer("김철수"
        , 300
        , LocalDate.parse("2023-06-31", DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        , LocalDate.parse("2023-07-03", DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        , 10000, 1000)
    ,Customer("홍길동"
        , 400
        , LocalDate.parse("2023-11-31", DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        , LocalDate.parse("2023-12-31", DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        , 10000, 1000)
)

fun main() {
    while (true){
        println("호텔예약 프로그램 입니다.")
        println("[메뉴]")
        println("1. 방예약, 2. 예약 목록, 3. 예약 목록(정렬) 출력, 4. 시스템 종료, 5. 금액 입금-출금 내역 목록 출력, 6. 예약 변경/ 취소")
        var menuSelect = readLine()
        if (menuSelect == "1") {
            println("예약자분의 성함을 알려주세요.")
            var name = readLine()!!
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
            //체크인 날짜 확인하고 받아 오기
            var checkInDate = CheckService().checkInDate(roomNum)
            //체크아웃 날짜 확인하고 받아 오기
            var checkOutDate = CheckService().checkOutDate(checkInDate)
            //random 금액
            var randomIncome = (10000..1000000).random()
            var randomOutgoings = (10000..randomIncome).random()
            var customer = Customer(name, roomNum.toInt(), checkInDate, checkOutDate, randomIncome, randomOutgoings)
            customerList.add(customer)
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
            var checkCustomer = true
            for(index in customerList){
                if(checkName == customerList[i].name){
                    println("1. 초기금액으로 ${customerList[i].income}원 입금되었습니다.")
                    println("2. 예약금으로 ${customerList[i].deposit}원 출금되었습니다.")
                    checkCustomer = false
                    break
                }
                i++
            }
            if(checkCustomer){
                println("예약된 사용자를 찾을 수 없습니다.")
            }
            continue
        }else if(menuSelect=="6"){
            println("예약을 변경할 사용자 이름을 입력하세요.")
            var motifyName = readLine()
            var i = 0
            var checkName = true
            var customerBookingList = mutableListOf<Int>()
            for(index in customerList){
                if(motifyName == customerList[i].name){
                    checkName = false
                    customerBookingList.add(i)
                }
                i++
            }
            if(checkName){
                println("사용자 이름으로 예약된 목록을 찾을 수 없습니다.")
            }else{
                var i = 0
                for(index in customerBookingList){
                    println("${i+1}. 방번호 : ${customerList[customerBookingList[i]].roomNum}호" +
                            ", 체크인 : ${customerList[customerBookingList[i]].checkInDate} " +
                            ", 체크아웃: ${customerList[customerBookingList[i]].checkOutDate}")
                    i++
                }
                println("해당 예약을 어떻게 하시겠어요 1. 변경 2. 취소 / 이외 번호. 메뉴로 돌아가기")
                var checkBookingNum = readLine()!!
                if(checkBookingNum=="1"){
                    println("변경할 목록을 선택해 주세요.")
                    var checkListNum = readLine()!!
                    var index = customerBookingList[((checkListNum.toInt()-1))]
                    var roomNum = customerList[index].roomNum.toString()
                    var checkInDate = CheckService().checkInDate(roomNum)
                    var checkOutDate = CheckService().checkOutDate(checkInDate)
                    var customer = Customer(
                        customerList[index].name
                        , customerList[index].roomNum
                        , checkInDate
                        , checkOutDate
                        , customerList[index].income
                        , customerList[index].deposit)
                    customerList.set(index, customer)
                    println("예약이 변경 되었습니다.")
                    continue
                }else if(checkBookingNum=="2"){
                    println("취소를 선택하셨습니다.\n" +
                            "[취소 유의사항]\n" +
                            "체크인 3일 이전 취소 예약금 환불 불가\n" +
                            "체크인 5일 이전 취소 예약금 30% 환불\n" +
                            "체크인 7일 이전 취소 예약금 50% 환불\n" +
                            "체크인 14일 이전 취소 예약금 80% 환불\n" +
                            "체크인 30일 이전 취소 예약금의 100% 환불")
                    println("취소할 목록을 선택해 주세요.")
                    var checkListNum = readLine()!!
                    var index = customerBookingList[((checkListNum.toInt()-1))]
                    var toDay:LocalDate = LocalDate.now()
                    var checkInDate:LocalDate = customerList[index].checkInDate
                    var period = Period.between(toDay, checkInDate)
                    println("예약일로 부터 ${period.days}일 전입니다.")
                    //환불 알고리즘
                    var betweenDate = period.days.toInt()
                    var deposit:Int = customerList[index].deposit
                    val number : Int by lazy {
                        if(betweenDate >= 30){
                            deposit
                        }else if (betweenDate >=14){
                            (deposit*0.8).toInt()
                        }else if (betweenDate >=7){
                            (deposit*0.5).toInt()
                        }else if (betweenDate >=5){
                            (deposit*0.3).toInt()
                        }else{
                            0
                        }
                    }
                    println("환불금은 ${number}원 입니다.")
                }else{
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