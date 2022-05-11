package com.example.Project.projectDependencyInjection

import com.example.Project.projectBackendApi.TodoApi
import com.example.Project.projectBackendApi.TodoRepository
import com.example.Project.projectBackendApi.TodoRepositoryImpl
import com.example.Project.projectPlaceholders.ApplicationConstantValues
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val networkModule = module {    // dependency injection using koin
    single(named("base_url")) { BaseUrlProvider() }    // base url placeholder
    single { HttpLoggingInterceptorProvider() }    // logging interceptor to see logs
    single { StethoInterceptorProvider() }    //
    single { AuthInterceptorProvider(apiKey = "") }    // we should provide auth here
    single {
        OkHttpProvider(
            loggingInterceptor = get(),
            stethoInterceptor = get(),
            authInterceptor = get()
        )
    }
    single { CallAdapterFactoryProvider() }
    single { ConverterFactoryProvider() }
    single {
        RetrofitProvider(
            baseUrl = get(named("base_url")),
            okHttpClient = get(),
            gsonConverterFactory = get(),
            callAdapterFactory = get()
        )
    }
    single { TodoApiProvider(retrofit = get()) }
}

val repositoryModule = module {
    single { TodoRepositoryProvider(todoApi = get()) }
}
fun AuthInterceptorProvider(apiKey: String): Interceptor {
    return Interceptor { chain ->
        val newUrl = chain.request().url()
            .newBuilder()
            .addQueryParameter("api_key", apiKey)
            .build()
        val newRequest = chain.request()
            .newBuilder()
            .url(newUrl)
            .build()
        chain.proceed(newRequest)
    }
}
fun TodoApiProvider(retrofit: Retrofit): TodoApi = retrofit.create(TodoApi::class.java)

fun BaseUrlProvider(): String = ApplicationConstantValues.BASE_URL

fun HttpLoggingInterceptorProvider(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor(
    ).apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
}

fun StethoInterceptorProvider(): StethoInterceptor = StethoInterceptor()

fun OkHttpProvider(
    loggingInterceptor: HttpLoggingInterceptor,
    stethoInterceptor: StethoInterceptor,
    authInterceptor: Interceptor
): OkHttpClient {
    return OkHttpClient.Builder()
        .addNetworkInterceptor(stethoInterceptor)
        .addInterceptor(loggingInterceptor)
        .addInterceptor(authInterceptor)
        .build()
}

fun CallAdapterFactoryProvider(): CallAdapter.Factory = CoroutineCallAdapterFactory()

fun ConverterFactoryProvider(): Converter.Factory = GsonConverterFactory.create()

fun RetrofitProvider(
    baseUrl: String,
    okHttpClient: OkHttpClient,
    gsonConverterFactory: Converter.Factory,
    callAdapterFactory: CallAdapter.Factory
): Retrofit {
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .addConverterFactory(gsonConverterFactory)
        .addCallAdapterFactory(callAdapterFactory)
        .build()
}

fun TodoRepositoryProvider(todoApi: TodoApi): TodoRepository = TodoRepositoryImpl(todoApi)    // here we get TodoRepository
