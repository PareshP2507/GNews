package org.psquare.gnews.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module
import org.psquare.gnews.data.dateconverter.DateConverter
import org.psquare.gnews.data.dateconverter.DateConverterImpl
import org.psquare.gnews.data.repository.category.BusinessCategory
import org.psquare.gnews.data.repository.category.Category
import org.psquare.gnews.data.repository.category.EntertainmentCategory
import org.psquare.gnews.data.repository.category.GeneralCategory
import org.psquare.gnews.data.repository.category.HealthCategory
import org.psquare.gnews.data.repository.category.NationCategory
import org.psquare.gnews.data.repository.category.ScienceCategory
import org.psquare.gnews.data.repository.category.SportsCategory
import org.psquare.gnews.data.repository.category.TechnologyCategory
import org.psquare.gnews.data.repository.category.WorldCategory
import org.psquare.gnews.data.repository.news.NewsApiService
import org.psquare.gnews.data.repository.news.NewsApiServiceImpl
import org.psquare.gnews.data.repository.news.NewsDataSource
import org.psquare.gnews.data.repository.news.NewsRepositoryImpl
import org.psquare.gnews.data.repository.news.RemoteNewsDataSource
import org.psquare.gnews.domain.repository.NewsRepository
import org.psquare.gnews.ui.screen.FeedViewModel

private const val HOST_KEY = "endpoint"
private const val HOST_VALUE = "gnews.io"

private const val API_KEY = "apikey"
private const val API_KEY_VALUE = "ae16d57e7724fd11d24ec18de55aa809"

private const val HEADLINES_PATH_KEY = "top-headlines"
private const val HEADLINES_PATH_VALUE = "/api/v4/top-headlines"

private const val DISPATCHER_IO = "io"
private const val DISPATCHER_MAIN = "main"
private const val DISPATCHER_DEFAULT = "default"

internal val networkModule = module {
    single(named(HOST_KEY)) { HOST_VALUE }
    single(named(API_KEY)) { API_KEY_VALUE }
    single(named(HEADLINES_PATH_KEY)) { HEADLINES_PATH_VALUE }
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                })
            }
        }
    }
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
    factory<NewsDataSource.Remote> { RemoteNewsDataSource(get(), get(named(DISPATCHER_IO))) }
}

internal val repositoryModule = module {
    singleOf<DateConverter>(::DateConverterImpl)
    factory<NewsRepository> { NewsRepositoryImpl(get(), get()) }
}

internal val categoryModule = module {
    single { GeneralCategory() } bind Category::class
    single { BusinessCategory() } bind Category::class
    single { EntertainmentCategory() } bind Category::class
    single { HealthCategory() } bind Category::class
    single { NationCategory() } bind Category::class
    single { ScienceCategory() } bind Category::class
    single { SportsCategory() } bind Category::class
    single { TechnologyCategory() } bind Category::class
    single { WorldCategory() } bind Category::class
}

internal val viewModelModule = module {
    viewModel { FeedViewModel(get()) }
}

internal val dispatchersModule = module {
    single(named(DISPATCHER_IO)) { Dispatchers.IO }
    single(named(DISPATCHER_MAIN)) { Dispatchers.Main }
    single(named(DISPATCHER_DEFAULT)) { Dispatchers.Default }
}
