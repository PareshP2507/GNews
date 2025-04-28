package org.psquare.gnews.data.repository.category

class ScienceCategory : Category {
    override fun name(): String = "Science"

    override fun urlParamName(): String = "science"
}