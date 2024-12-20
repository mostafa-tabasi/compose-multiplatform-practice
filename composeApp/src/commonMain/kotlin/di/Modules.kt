package di

import data.ProfileRepository
import data.ProfileRepositoryImpl
import org.koin.compose.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import screen.username.UsernameViewModel

expect val platformModule: Module

val sharedModule = module {
    singleOf(::ProfileRepositoryImpl).bind<ProfileRepository>()
    viewModelOf(::UsernameViewModel)
}