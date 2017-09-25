package in.boilerplate.sample

import dagger.Component
import in.boilerplate.sample.data.network.NetworkModule
import in.boilerplate.sample.data.remote.ApiModule
import in.boilerplate.sample.ui.detail.DetailComponent
import in.boilerplate.sample.ui.detail.DetailModule
import in.boilerplate.sample.ui.list.ListComponent
import in.boilerplate.sample.ui.list.ListModule
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        ApplicationModule::class,
        NetworkModule::class,
        ApiModule::class
))
interface ApplicationComponent {

    // Injectors
    fun injectTo(app: KotlinBoilerplateApp)

    // Submodule methods
    // Every screen is its own submodule of the graph and must be added here.
    fun plus(module: ListModule): ListComponent
    fun plus(module: DetailModule): DetailComponent
}