package com.test.kotlintest

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by Scott on 2021/1/4 0004
 */
interface RetrofitApi {
    @GET("https://www.baidu.com")
    fun baidu():Call<ResponseBody>
}