package com.cjwilliams24680.seatgeeksearch.ui

data class LiveDataEvent<T>(private var value: T?) {
    fun pop(): T? {
        val pendingValue = value
        value = null
        return pendingValue
    }
}