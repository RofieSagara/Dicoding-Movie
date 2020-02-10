package com.sagara.dicodingmovie

import android.app.Application
import android.content.Context
import com.sagara.dicodingmovie.room.AppDatabase
import com.sagara.dicodingmovie.room.IAppDatabase
import com.sagara.dicodingmovie.schedulers.WorkManagerLauncher
import com.sagara.dicodingmovie.service.MovieService
import com.sagara.dicodingmovie.service.api.MovieApi
import com.sagara.dicodingmovie.ui.details.DetailsViewModel
import com.sagara.dicodingmovie.ui.favorite.FavoriteViewModel
import com.sagara.dicodingmovie.ui.favorite.favoritelist.FavoriteListViewModel
import com.sagara.dicodingmovie.ui.favorite.favoritetvshows.FavoriteTvShowsViewModel
import com.sagara.dicodingmovie.ui.list.movie.MovieViewModel
import com.sagara.dicodingmovie.ui.list.tvseries.TvSeriesViewModel
import com.sagara.dicodingmovie.ui.search.SearchViewModel
import com.sagara.dicodingmovie.ui.settings.SettingsViewModel
import com.sagara.dicodingmovie.utils.SharedPreferences
import org.joda.time.DateTime
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber

class Application: Application() {

    private val roomModule = module {
        single { instanceAppDatabase(get()) }
    }

    private val networkModule = module {
        single {
            Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        single { get<Retrofit>().create(MovieApi::class.java) }
        single { MovieService(get(), get()) }
    }

    private val sharedPreferencesModule = module {
        single { instanceSharedReferences(get()) }
        single { instanceWorkManagerLauncher(get()) }
    }

    private val viewModelModule = module {
        viewModel { MovieViewModel(get()) }
        viewModel { TvSeriesViewModel(get()) }
        viewModel { FavoriteListViewModel(get()) }
        viewModel { FavoriteTvShowsViewModel(get()) }
        viewModel { DetailsViewModel(get()) }
        viewModel { FavoriteViewModel(get()) }
        viewModel { SearchViewModel(get()) }
        viewModel { SettingsViewModel(get(), get()) }
    }

    private fun instanceAppDatabase(context: Context): IAppDatabase {
        return AppDatabase.getInstance(context)
    }

    private fun instanceSharedReferences(context: Context): SharedPreferences {
        return SharedPreferences(context)
    }

    private fun instanceWorkManagerLauncher(context: Context): WorkManagerLauncher {
        return WorkManagerLauncher(context)
    }

    override fun onCreate() {
        super.onCreate()

        //Timber Debug
        Timber.plant(Timber.DebugTree())

        //Create Koin
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@Application)
            modules(listOf(sharedPreferencesModule, roomModule, networkModule, viewModelModule))
        }

        //Fire Work Manager
        val shared = instanceSharedReferences(this)
        if (shared.isDailyReminder) {
            WorkManagerLauncher(this)
                .startDailyWork()
        }else{
            WorkManagerLauncher(this)
                .stopDailyWork()
        }

        if (shared.isRealeaseReminder) {
            WorkManagerLauncher(this)
                .startReleaseWork()
        }else{
            WorkManagerLauncher(this)
                .stopReleaseWork()
        }
    }
}