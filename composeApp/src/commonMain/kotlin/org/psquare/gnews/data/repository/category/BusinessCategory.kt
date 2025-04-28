package org.psquare.gnews.data.repository.category

class BusinessCategory : Category {
    override fun name(): String = "Business"

    override fun urlParamName(): String = "business"
}