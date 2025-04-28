package org.psquare.gnews.data.repository.category

class EntertainmentCategory : Category {
    override fun name(): String = "Entertainment"

    override fun urlParamName(): String = "entertainment"
}