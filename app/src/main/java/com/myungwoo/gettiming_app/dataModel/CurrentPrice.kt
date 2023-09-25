package com.myungwoo.gettiming_app.dataModel

data class CurrentPrice(
    val opening_price : String,
    val closing_price : String,
    val min_price : String,
    val max_price : String,
    val units_traded : String,
    val acc_trade_value : String,
    val prev_closing_price : String,
    val units_traded_24H : String,
    val acc_trade_value_24H : String,
    val fluctate_24H : String,
    val fluctate_rate_24H : String

//    opening_price: "22774000",
//    closing_price: "22558000",
//    min_price: "22380000",
//    max_price: "22897000",
//    units_traded: "2344.25102733",
//    acc_trade_value: "53116336853.7015",
//    prev_closing_price: "22773000",
//    units_traded_24H: "2789.33394039",
//    acc_trade_value_24H: "63274683837.6202",
//    fluctate_24H: "-280000",
//    fluctate_rate_24H: "-1.23"

)
