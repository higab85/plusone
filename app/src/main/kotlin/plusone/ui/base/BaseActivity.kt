package plusone.ui.base

import android.os.Bundle
import android.support.annotation.CallSuper
import android.support.v7.app.AppCompatActivity
import plusone.ApplicationComponent
import plusone.KotlinBoilerplateApp

abstract class BaseActivity: AppCompatActivity() {

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        injectDependencies(KotlinBoilerplateApp.graph)
    }

    abstract fun injectDependencies(graph: ApplicationComponent)
}