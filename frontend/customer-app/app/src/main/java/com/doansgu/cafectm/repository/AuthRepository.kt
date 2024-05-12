package com.doansgu.cafectm.repository

import com.doansgu.cafectm.model.RetrofitInstance

object AuthRepository {

    suspend fun testAuthorization(): Boolean =
        RetrofitInstance.apiService.testAuthorization().isSuccessful
}