package org.psquare.gnews.di

import io.ktor.client.HttpClient
import org.koin.core.qualifier.named
import org.koin.dsl.module
import org.psquare.gnews.data.NewsApiService
import org.psquare.gnews.data.NewsApiServiceImpl
import org.psquare.gnews.data.repository.NewsDataSource
import org.psquare.gnews.data.repository.NewsRepositoryImpl
import org.psquare.gnews.data.repository.RemoteNewsDataSource
import org.psquare.gnews.domain.repository.NewsRepository

private const val HOST_KEY = "endpoint"
private const val HOST_VALUE = "https://gnews.io/api/v4/"

private const val API_KEY = "apikey"
private const val API_KEY_VALUE = "ae16d57e7724fd11d24ec18de55aa809"

private const val HEADLINES_PATH_KEY = "top-headlines"
private const val HEADLINES_PATH_VALUE = "top-headlines"

internal val networkModule = module {
    single(named(HOST_KEY)) { HOST_VALUE }
    single(named(API_KEY)) { API_KEY_VALUE }
    single(named(HEADLINES_PATH_KEY)) { HEADLINES_PATH_VALUE }
    single { HttpClient() }
    single<NewsApiService> {
        NewsApiServiceImpl(
            get(named(HOST_KEY)),
            get(named(API_KEY)),
            get(named(HEADLINES_PATH_KEY)),
            get()
        )
    }
}

internal val datasourceModule = module {
    factory<NewsDataSource.Remote> { RemoteNewsDataSource(get()) }
}

internal val repositoryModule = module {
    factory<NewsRepository> { NewsRepositoryImpl(get()) }
}
