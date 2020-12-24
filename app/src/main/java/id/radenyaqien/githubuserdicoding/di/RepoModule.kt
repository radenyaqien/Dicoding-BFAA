package id.radenyaqien.githubuserdicoding.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import id.radenyaqien.githubuserdicoding.data.repository.DetailRepository
import id.radenyaqien.githubuserdicoding.data.repository.SearchRepository
import id.radenyaqien.githubuserdicoding.data.retrofit.GithubInterface
import id.radenyaqien.githubuserdicoding.persistence.FavUserRepository
import id.radenyaqien.githubuserdicoding.persistence.UserDao
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepoModule {

    @Provides
    @Singleton
    fun provideDetailRepo(
        githubInterface: GithubInterface,
        userDao: UserDao
    ): DetailRepository {
        return DetailRepository(githubInterface, userDao)
    }

    @Provides
    @Singleton
    fun provideSearchRepo(
        githubInterface: GithubInterface,
    ): SearchRepository {
        return SearchRepository(githubInterface)
    }

    @Provides
    @Singleton
    fun provideFavRepo(
        userDao: UserDao
    ): FavUserRepository {
        return FavUserRepository(userDao)
    }
}