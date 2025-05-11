package org.psquare.gnews.init

import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.psquare.gnews.di.categoryModule
import org.psquare.gnews.di.datasourceModule
import org.psquare.gnews.di.dispatchersModule
import org.psquare.gnews.di.networkModule
import org.psquare.gnews.di.platformDbModule
import org.psquare.gnews.di.repositoryModule
import org.psquare.gnews.di.viewModelModule

fun initKoin(onKoinStart: KoinApplication.() -> Unit) {
    startKoin {
        onKoinStart()
        modules(
            networkModule,
            datasourceModule,
            repositoryModule,
            categoryModule,
            viewModelModule,
            dispatchersModule,
            platformDbModule
        )
    }
}