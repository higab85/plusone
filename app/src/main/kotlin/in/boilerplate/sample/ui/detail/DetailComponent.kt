package in.boilerplate.sample.ui.detail

import dagger.Subcomponent
import in.boilerplate.sample.ui.ActivityScope

@ActivityScope
@Subcomponent(modules = arrayOf(
        DetailModule::class
))
interface DetailComponent {
    fun injectTo(activity: DetailActivity)
}