package id.radenyaqien.githubuserdicoding.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import id.radenyaqien.githubuserdicoding.data.retrofit.ApiService
import id.radenyaqien.githubuserdicoding.data.retrofit.GithubInterface

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Provides
    fun provideNetworkService(): GithubInterface {
        return ApiService.retrofitClient().create(GithubInterface::class.java)
    }

}