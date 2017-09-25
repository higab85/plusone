package in.boilerplate.sample.ui.list

import dagger.Subcomponent
import in.boilerplate.sample.ui.ActivityScope

@ActivityScope
@Subcomponent(modules = arrayOf(
        ListModule::class
))
interface ListComponent {

    fun injectTo(activity: ListActivity)
}