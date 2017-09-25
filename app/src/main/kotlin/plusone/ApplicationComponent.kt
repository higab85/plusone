package plusone

import dagger.Component
import plusone.data.network.NetworkModule
import plusone.data.remote.ApiModule
import plusone.ui.detail.DetailComponent
import plusone.ui.detail.DetailModule
import plusone.ui.list.ListComponent
import plusone.ui.list.ListModule
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