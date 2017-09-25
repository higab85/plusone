package plusone.ui.list

import dagger.Subcomponent
import plusone.ui.ActivityScope

@ActivityScope
@Subcomponent(modules = arrayOf(
        ListModule::class
))
interface ListComponent {

    fun injectTo(activity: ListActivity)
}