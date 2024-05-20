package com.doansgu.cafectm.model

sealed class PaymentState() {
    object Loading : PaymentState()
    object Success : PaymentState()
    object Error : PaymentState()
    object Cancel : PaymentState()
}

