package plusone.ui.detail

import dagger.Subcomponent
import plusone.ui.ActivityScope

@ActivityScope
@Subcomponent(modules = arrayOf(
        DetailModule::class
))
interface DetailComponent {
    fun injectTo(activity: DetailActivity)
}