package com.doansgu.cafectm.model

data class PaymentState(
    var onSusses: Boolean = false,
    var onCancel: Boolean = false,
    var onFailed: Boolean = false,
)

