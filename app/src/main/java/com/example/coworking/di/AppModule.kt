package com.example.coworking.di

import com.example.coworking.data.repository.Repository
import com.example.coworking.data.repository.RepositoryImpl
import com.example.coworking.network.APIClient
import com.example.coworking.network.APIInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun providesAPIClient(): APIClient = APIClient

    @Provides
    @Singleton
    fun providesAPIInterface(apiClient: APIClient): APIInterface =
        apiClient.getClient()!!.create(APIInterface::class.java)

    @Provides
    @Singleton
    fun providesRepository(apiInterface: APIInterface): Repository = RepositoryImpl(apiInterface)

}