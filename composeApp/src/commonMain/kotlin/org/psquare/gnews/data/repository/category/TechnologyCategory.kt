package org.psquare.gnews.data.repository.category

class TechnologyCategory : Category {
    override fun name(): String = "Technology"

    override fun urlParamName(): String = "technology"
}