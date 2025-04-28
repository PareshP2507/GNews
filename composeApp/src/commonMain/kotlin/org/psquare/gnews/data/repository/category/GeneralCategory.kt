package org.psquare.gnews.data.repository.category

class GeneralCategory: Category {
    override fun name(): String = "General"

    override fun urlParamName(): String = "general"
}