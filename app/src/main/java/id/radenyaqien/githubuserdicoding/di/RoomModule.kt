package id.radenyaqien.githubuserdicoding.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import id.radenyaqien.githubuserdicoding.persistence.UserDao
import id.radenyaqien.githubuserdicoding.persistence.UsersDatabase
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RoomModule {
    private val mDatabaseName = "user_favorite_db"

    @Singleton
    @Provides
    fun provideDatabase(app: Application): UsersDatabase {
        return Room.databaseBuilder(
            app, UsersDatabase::class.java,
            mDatabaseName
        ).build()
    }

    @Singleton
    @Provides
    fun provideUserFavoriteDao(appDatabase: UsersDatabase): UserDao {
        return appDatabase.userDao()
    }

}