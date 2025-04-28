package org.psquare.gnews.data.repository.category

class HealthCategory : Category {
    override fun name(): String = "Health"

    override fun urlParamName(): String = "health"
}