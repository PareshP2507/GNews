package org.psquare.gnews.data.repository.category

interface Category {

    fun name(): String

    fun urlParamName(): String
}