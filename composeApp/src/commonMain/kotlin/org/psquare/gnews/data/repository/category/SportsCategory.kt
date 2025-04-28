package org.psquare.gnews.data.repository.category

class SportsCategory : Category {
    override fun name(): String = "Sports"

    override fun urlParamName(): String = "sports"
}