package in.boilerplate.sample.ui.detail

import android.support.v7.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import in.boilerplate.sample.data.remote.model.Repo
import in.boilerplate.sample.ui.base.ActivityModule

@Module
class DetailModule(activity: AppCompatActivity, val repo: Repo) : ActivityModule(activity) {

    @Provides
    fun provideRepo(): Repo = repo
}