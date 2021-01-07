package com.example.kotlintutorial

import com.google.gson.annotations.SerializedName

data class LoginResponseModel(
   // @SerializedName("EMPID")
    val EMPID: Int, // 134
   // @SerializedName("EMPLOYEENAME")
    val EMPLOYEENAME: String, // Testing Byker
  //  @SerializedName("MESSAGE")
    val MESSAGE: String, // Active User.
  //  @SerializedName("PLANTCD")
    val PLANTCD: String, // 1001
  //  @SerializedName("PLANTCODE")
    val PLANTCODE: String, // 1001
  //  @SerializedName("SEGMENT")
    val SEGMENT: String,
  //  @SerializedName("STATUS")
    val STATUS: Int, // 1
  //  @SerializedName("USERID")
    val USERID: Int, // 297
  //  @SerializedName("USERNAME")
    val USERNAME: String // Testing Byker


)