package org.psquare.gnews.data.repository.category

class NationCategory : Category {
    override fun name(): String = "Nation"

    override fun urlParamName(): String = "nation"
}