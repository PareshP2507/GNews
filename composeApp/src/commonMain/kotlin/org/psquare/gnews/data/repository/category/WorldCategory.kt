package org.psquare.gnews.data.repository.category

class WorldCategory: Category {
    override fun name(): String = "World"

    override fun urlParamName(): String = "world"
}