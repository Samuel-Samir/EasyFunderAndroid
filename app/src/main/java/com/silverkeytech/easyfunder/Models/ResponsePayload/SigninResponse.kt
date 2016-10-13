package com.silverkeytech.easyfunder.Models.ResponsePayload

/**
 * Created by samuel on 9/11/2016.
 */
class SigninResponse {
    var ifExist:Boolean = false
    var errorStatus:String ?= null
    var user: UserInfo = UserInfo()

}
