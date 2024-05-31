package core.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration

fun initKoin(
    additionalModules: List<Module> = emptyList(),
    koinAppDeclaration: KoinAppDeclaration = {}
) = startKoin {
    koinAppDeclaration()
    modules(additionalModules + commonModule()+ platformModule())
}